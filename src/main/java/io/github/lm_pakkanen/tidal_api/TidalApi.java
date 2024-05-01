package io.github.lm_pakkanen.tidal_api;

import io.github.lm_pakkanen.tidal_api.controllers.endpoints.AuthorizationController;
import io.github.lm_pakkanen.tidal_api.controllers.endpoints.TracksController;
import io.github.lm_pakkanen.tidal_api.models.CredentialsStore;
import io.github.lm_pakkanen.tidal_api.models.entities.TidalCredentials;
import io.github.lm_pakkanen.tidal_api.models.exceptions.InvalidCredentialsException;
import io.github.lm_pakkanen.tidal_api.models.exceptions.UnauthorizedException;

/**
 * Main class for the Tidal API.
 */
public final class TidalApi {

  private final CredentialsStore credentialsStore;
  private final AuthorizationController authorizationController;

  /**
   * Controller for the tracks endpoint.
   */
  public final TracksController tracks;

  /**
   * Constructor for the Tidal API.
   */
  public TidalApi() {
    this.credentialsStore = CredentialsStore.getInstance();
    this.authorizationController = new AuthorizationController();
    this.tracks = new TracksController();
  }

  /**
   * Authorizes the client with the Tidal API. If the credentials are already
   * valid and expire in more than 1 hour, they are not refreshed.
   * 
   * @see #authorize(String, String, boolean)
   * 
   * @param clientId     client id.
   * @param clientSecret client secret.
   * 
   * 
   * @throws InvalidCredentialsException if provided credentials are null or
   *                                     empty.
   * @throws UnauthorizedException       if provided credentials are null or
   *                                     empty.
   */
  public void authorize(String clientId, String clientSecret)
      throws InvalidCredentialsException, UnauthorizedException {
    this.authorize(clientId, clientSecret, false);
  }

  /**
   * Authorizes the client with the Tidal API. If the credentials are already
   * valid and expire in more than 1 hour, they are not refreshed unless 'force'
   * is set to true.
   * 
   * @param clientId     client id.
   * @param clientSecret client secret.
   * @param force        force re-authorization.
   * 
   * @throws InvalidCredentialsException if provided credentials are null or
   *                                     empty.
   * @throws UnauthorizedException       if provided credentials are null or
   *                                     empty.
   */
  public void authorize(String clientId, String clientSecret, boolean force)
      throws InvalidCredentialsException, UnauthorizedException {
    final TidalCredentials currentCredentials = AuthorizationController.getCredentialsOrNull();

    if (currentCredentials == null || force) {
      credentialsStore.setCredentials(this.authorizationController.authorize(clientId, clientSecret));
      return;
    }

    final long refreshThresholdSeconds = 60 * 60L; // 1hr
    final long credentialsExpireInSeconds = currentCredentials.getExpiresInSeconds();

    if (credentialsExpireInSeconds <= refreshThresholdSeconds) {
      // Credentials expire within 1 hour, refresh
      credentialsStore.setCredentials(this.authorizationController.authorize(clientId, clientSecret));
    }
  }

  /**
   * Forgets the current credentials. This does not need to be called to refresh
   * credentials.
   */
  public void forgetCredentials() {
    this.credentialsStore.setCredentials(null);
  }

  /**
   * Gets the version of the Tidal API package.
   * 
   * @return version of the Tidal API package.
   */
  public String getVersion() {
    return Configuration.getInstance().getVersion();
  }
}
