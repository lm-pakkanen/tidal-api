package io.github.lm_pakkanen.tidal_api.controllers.endpoints;

import io.github.lm_pakkanen.tidal_api.models.entities.TidalCredentials;

/**
 * Base class for all endpoint controllers.
 * Provides useful ields &amp; methods for the endpoint controllers.
 */
public class BaseEndpointController {
  private static final String BASE_URL = "https://openapi.tidal.com";

  /**
   * Tidal API auhtorization URL.
   */
  protected static final String AUTHORIZATION_URL = "https://auth.tidal.com/v1/oauth2/token";

  /**
   * Tidal API artists URL.
   */
  protected static final String ARTISTS_URL = BaseEndpointController.BASE_URL + "/artists";

  /**
   * Tidal API tracks URL.
   */
  protected static final String TRACKS_URL = BaseEndpointController.BASE_URL + "/tracks";

  /**
   * Default constructor.
   */
  public BaseEndpointController() {
  }

  /**
   * Tries to get the credentials from the store. If the credentials don't exist
   * or are invalid/expired, throws an exception.
   * 
   * @param credentials the credentials to validate.
   * @throws IllegalStateException if the credentials are invalid.
   */
  public static void tryValidateCredentials(TidalCredentials credentials) throws IllegalStateException {
    if (credentials == null) {
      throw new IllegalStateException("No credentials found. Please authorize the client first.");
    }
  }
}
