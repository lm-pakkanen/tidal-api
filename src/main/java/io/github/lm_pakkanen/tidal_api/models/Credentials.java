package io.github.lm_pakkanen.tidal_api.models;

import io.github.lm_pakkanen.tidal_api.models.tidal_responses.TidalAuthorizationResponse;

/**
 * Credentials for the Tidal API.
 */
public final class Credentials {
  private String accessToken;
  private int expirationTimeSeconds;
  private long issuedAtUnix;

  /**
   * Constructor for the Credentials.
   * 
   * @param response the response from the Tidal API.
   */
  public Credentials(TidalAuthorizationResponse response) {
    this.accessToken = response.access_token;
    this.expirationTimeSeconds = response.expires_in;
    this.issuedAtUnix = System.currentTimeMillis();
  }

  /**
   * Gets the access token.
   * 
   * @return the access token.
   */
  public String getAccessToken() {
    return this.accessToken;
  }

  /**
   * Gets the expiration time in seconds.
   * 
   * @return the expiration time in seconds.
   */
  public long getExpiresInSeconds() {
    final long expiresAtUnix = this.issuedAtUnix + (this.expirationTimeSeconds * 1000L);
    final long expiresInMillis = expiresAtUnix - System.currentTimeMillis();
    return Math.max(expiresInMillis / 1000L, 0L);
  }
}
