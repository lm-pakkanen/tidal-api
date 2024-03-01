package io.github.lm_pakkanen.tidal_api.endpoint_controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

import com.fasterxml.jackson.jr.ob.JSON;

import io.github.lm_pakkanen.tidal_api.models.tidal_responses.TidalAuthorizationResponse;
import io.github.lm_pakkanen.tidal_api.models.Credentials;
import io.github.lm_pakkanen.tidal_api.models.exceptions.InvalidCredentialsException;
import io.github.lm_pakkanen.tidal_api.models.exceptions.UnauthorizedException;

/**
 * Controller for the Tidal authorization endpoint.
 */
public final class Authorization extends BaseEndpointController {

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
  public static Credentials authorize(String clientId, String clientSecret)
      throws InvalidCredentialsException, UnauthorizedException {

    Authorization.validateCredentials(clientId, clientSecret);

    HttpURLConnection connection = null;

    OutputStream outputStream = null;
    OutputStreamWriter outputStreamWriter = null;

    InputStreamReader inputStreamReader = null;
    BufferedReader bufferedReader = null;

    try {
      String credentialsBase64 = Authorization.getCredentialsBase64(clientId, clientSecret);

      connection = (HttpURLConnection) new URI(BaseEndpointController.AUTHORIZATION_URL).toURL().openConnection();

      connection.setRequestProperty("Authorization", "Basic " + credentialsBase64);
      connection.setRequestProperty("Accept", "application/json");

      connection.setDoOutput(true);

      outputStream = connection.getOutputStream();
      outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");

      outputStreamWriter.write("grant_type=client_credentials");

      outputStreamWriter.close();
      outputStream.close();

      if (connection.getResponseCode() != 200) {
        throw new UnauthorizedException();
      }

      final String responseString = BaseEndpointController.responseToString(inputStreamReader, bufferedReader,
          connection);

      return new Credentials(JSON.std.beanFrom(TidalAuthorizationResponse.class, responseString));
    } catch (URISyntaxException | IOException exception) {
      throw new UnauthorizedException();
    } finally {
      try {
        if (outputStreamWriter != null) {
          outputStreamWriter.close();
        }

        if (outputStream != null) {
          outputStream.close();
        }

        if (inputStreamReader != null) {
          inputStreamReader.close();
        }

        if (bufferedReader != null) {
          bufferedReader.close();
        }

        if (connection != null) {
          connection.disconnect();
        }
      } catch (IOException e) {
        // no-op
      }
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
