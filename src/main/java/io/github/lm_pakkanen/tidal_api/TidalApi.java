package io.github.lm_pakkanen.tidal_api;

import io.github.lm_pakkanen.tidal_api.controllers.endpoints.AuthorizationController;
import io.github.lm_pakkanen.tidal_api.controllers.endpoints.TracksController;
import io.github.lm_pakkanen.tidal_api.models.entities.Credentials;
import io.github.lm_pakkanen.tidal_api.models.exceptions.InvalidCredentialsException;
import io.github.lm_pakkanen.tidal_api.models.exceptions.UnauthorizedException;

/**
 * Main class for the Tidal API.
 */
public final class TidalApi {

  private Credentials credentials;

  private final AuthorizationController authorizationController;
  public final TracksController tracks;

  /**
   * Constructor for the Tidal API.
   */
  public TidalApi() {
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
   * @return authorized credentials.
   * 
   * @throws InvalidCredentialsException if provided credentials are null or
   *                                     empty.
   * @throws UnauthorizedException       if provided credentials are null or
   *                                     empty.
   */
  public Credentials authorize(String clientId, String clientSecret)
      throws InvalidCredentialsException, UnauthorizedException {
    return this.authorize(clientId, clientSecret, false);
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
   * @return authorized credentials.
   * 
   * @throws InvalidCredentialsException if provided credentials are null or
   *                                     empty.
   * @throws UnauthorizedException       if provided credentials are null or
   *                                     empty.
   */
  public Credentials authorize(String clientId, String clientSecret, boolean force)
      throws InvalidCredentialsException, UnauthorizedException {
    if (!force && this.credentials != null) {
      long refreshThresholdSeconds = 60 * 60L; // 1hr
      long credentialsExpireInSeconds = this.credentials.getExpiresInSeconds();

      // Credentials still valid, don't refresh
      if (credentialsExpireInSeconds > refreshThresholdSeconds) {
        return this.credentials;
      }
    }

    this.credentials = this.authorizationController.authorize(clientId, clientSecret);
    return this.credentials;
  }

  /**
   * Forgets the current credentials. This does not need to be called to refresh
   * credentials.
   */
  public void forgetCredentials() {
    this.credentials = null;
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
