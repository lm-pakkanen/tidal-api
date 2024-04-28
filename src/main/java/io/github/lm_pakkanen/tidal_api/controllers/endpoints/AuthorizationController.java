package io.github.lm_pakkanen.tidal_api.controllers.endpoints;

import java.util.Base64;

import io.github.lm_pakkanen.tidal_api.models.tidal_responses.AuthorizationResponse;
import io.github.lm_pakkanen.tidal_api.models.CredentialsStore;
import io.github.lm_pakkanen.tidal_api.models.entities.TidalCredentials;
import io.github.lm_pakkanen.tidal_api.models.exceptions.InvalidCredentialsException;
import io.github.lm_pakkanen.tidal_api.models.exceptions.QueryException;
import io.github.lm_pakkanen.tidal_api.models.exceptions.UnauthorizedException;
import io.github.lm_pakkanen.tidal_api.models.queries.BaseQuery;
import io.github.lm_pakkanen.tidal_api.models.queries.Query;

/**
 * Controller for the Tidal authorization endpoint.
 */
public final class AuthorizationController extends BaseEndpointController {

  /**
   * Tries to get the credentials from the store. If the credentials don't exist
   * or are invalid/expired, throws an exception.
   * 
   * @return credentials.
   * 
   * @throws InvalidCredentialsException
   * @throws UnauthorizedException
   */
  public static TidalCredentials tryGetCredentials()
      throws InvalidCredentialsException, UnauthorizedException {

    final CredentialsStore credentialsStore = CredentialsStore.getInstance();
    final TidalCredentials credentials = credentialsStore.getCredentials();

    if (credentials == null) {
      throw new InvalidCredentialsException();
    }

    final String accessToken = credentials.getAccessToken();
    final long credentialsExpireInSeconds = credentials.getExpiresInSeconds();

    final boolean isAccessTokenValid = accessToken != null && !accessToken.isEmpty();
    final boolean isCredentialsExpired = credentialsExpireInSeconds == 0;

    if (!isAccessTokenValid || isCredentialsExpired) {
      throw new UnauthorizedException();
    }

    return credentials;
  }

  /**
   * Tries to get the credentials from the store. If the credentials don't exist,
   * returns null.
   * 
   * @return credentials or null.
   */
  public static TidalCredentials getCredentialsOrNull() {
    final CredentialsStore credentialsStore = CredentialsStore.getInstance();
    return credentialsStore.getCredentials();
  }

  /**
   * Authorizes the client with the Tidal API.
   * 
   * @param clientId     client id.
   * @param clientSecret client secret.
   * 
   * @return authorized credentials.
   * 
   * @throws InvalidCredentialsException
   * @throws UnauthorizedException
   */
  public TidalCredentials authorize(String clientId, String clientSecret)
      throws InvalidCredentialsException, UnauthorizedException {

    AuthorizationController.validateCredentials(clientId, clientSecret);

    try {
      final Query query = new Query(BaseEndpointController.AUTHORIZATION_URL);
      final String credentialsBase64 = AuthorizationController.getCredentialsBase64(clientId, clientSecret);

      query.auth(credentialsBase64);

      query.method(BaseQuery.HttpMethod.POST);
      query.body("grant_type=client_credentials");

      final AuthorizationResponse tidalAuthorizationResponse = query.execute(AuthorizationResponse.class);

      return new TidalCredentials(tidalAuthorizationResponse);
    } catch (QueryException exception) {
      throw new UnauthorizedException();
    }
  }

  /**
   * Validates the provided credentials.
   * 
   * @param clientId     client id.
   * @param clientSecret client secret.
   * 
   * @throws InvalidCredentialsException
   */
  private static void validateCredentials(String clientId, String clientSecret)
      throws InvalidCredentialsException {
    if (clientId == null || clientSecret == null || clientId.isEmpty() || clientSecret.isEmpty()) {
      throw new InvalidCredentialsException();
    }
  }

  /**
   * Gets the credentials converted to <clientId>:<clientSecret>
   * format as base64.
   * 
   * @param clientId     client id.
   * @param clientSecret client secret.
   * 
   * @return the credentials as base64.
   */
  private static String getCredentialsBase64(String clientId, String clientSecret) {
    final String credentialsString = clientId + ":" + clientSecret;
    final String credentialsBase64 = Base64.getEncoder().encodeToString(credentialsString.getBytes());
    return credentialsBase64;
  }
}
