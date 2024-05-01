package io.github.lm_pakkanen.tidal_api.models.tidal_responses;

/**
 * Response from the Tidal authorization API.
 */
public final class AuthorizationResponse {

  /**
   * Access token of the authorization response.
   */
  public String access_token;

  /**
   * Expiration time of the authorization response.
   */
  public int expires_in;

  /**
   * Default constructor.
   */
  public AuthorizationResponse() {
  }
}
