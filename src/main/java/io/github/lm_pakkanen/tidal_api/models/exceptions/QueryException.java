package io.github.lm_pakkanen.tidal_api.models.exceptions;

/**
 * This exception is thrown when a query to the Tidal API fails.
 */
public final class QueryException extends Exception {

  /**
   * Constructs a new QueryException with a default error message.
   */
  public QueryException() {
    super("Query failure: unknown exception");
  }

  /**
   * Constructs a new QueryException with the specified error message.
   *
   * @param message the error message.
   */
  public QueryException(String message) {
    super("Query failure: " + message);
  }

  /**
   * Constructs a new QueryException from another QueryException.
   *
   * @param exception the exception to get the error message from.
   */
  public QueryException(QueryException exception) {
    super(exception.getMessage());
  }

  /**
   * Constructs a new QueryException with the error message from the specified
   * exception.
   *
   * @param exception the exception to get the error message from.
   */
  public QueryException(Throwable exception) {
    super("Query failure: " + exception.getMessage());
  }
}
