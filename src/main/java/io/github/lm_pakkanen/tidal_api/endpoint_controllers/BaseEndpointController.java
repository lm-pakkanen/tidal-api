package io.github.lm_pakkanen.tidal_api.endpoint_controllers;

/**
 * Base class for all endpoint controllers.
 * Provides useful ields & methods for the endpoint controllers.
 */
public class BaseEndpointController {
  private static final String BASE_URL = "https://openapi.tidal.com";

  protected static final String AUTHORIZATION_URL = "https://auth.tidal.com/v1/oauth2/token";
  protected static final String TRACKS_URL = BaseEndpointController.BASE_URL + "/tracks";
}
