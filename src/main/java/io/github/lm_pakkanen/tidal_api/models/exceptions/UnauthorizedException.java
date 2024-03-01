package io.github.lm_pakkanen.tidal_api.models.exceptions;

/**
 * Thrown when the client is unauthorized (incorrect ID and / or secret).
 */
public final class UnauthorizedException extends Exception {
  public UnauthorizedException() {
    super("Unauthorized");
  }
}
