package io.github.lm_pakkanen.tidal_api.models.exceptions;

/**
 * Thrown when the client is unauthorized (incorrect ID and / or secret).
 */
public final class UnauthorizedException extends Exception {
  /**
   * Constructs a new UnauthorizedException with the default message
   * "Unauthorized".
   */
  public UnauthorizedException() {
    super("Unauthorized");
  }
}
