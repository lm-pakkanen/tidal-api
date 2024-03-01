package io.github.lm_pakkanen.tidal_api.models.exceptions;

/**
 * Thrown when provided credentials are invalid (null or empty).
 */
public final class InvalidCredentialsException extends Exception {
  public InvalidCredentialsException() {
    super("Invalid credentials");
  }
}
