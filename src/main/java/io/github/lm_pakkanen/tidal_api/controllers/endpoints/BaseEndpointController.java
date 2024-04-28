package io.github.lm_pakkanen.tidal_api.controllers.endpoints;

import io.github.lm_pakkanen.tidal_api.models.entities.TidalCredentials;

/**
 * Base class for all endpoint controllers.
 * Provides useful ields & methods for the endpoint controllers.
 */
public class BaseEndpointController {
  private static final String BASE_URL = "https://openapi.tidal.com";

  protected static final String AUTHORIZATION_URL = "https://auth.tidal.com/v1/oauth2/token";
  protected static final String ARTISTS_URL = BaseEndpointController.BASE_URL + "/artists";
  protected static final String TRACKS_URL = BaseEndpointController.BASE_URL + "/tracks";

  public static void tryValidateCredentials(TidalCredentials credentials) throws IllegalStateException {
    if (credentials == null) {
      throw new IllegalStateException("No credentials found. Please authorize the client first.");
    }
  }
}
