package io.github.lm_pakkanen.tidal_api.models;

import java.util.List;

/**
 * Represents the result of a list query operation.
 *
 * @param <T> the type of items in the list
 */
public final class ListQueryResult<T> {
  private final Status status;
  private final String message; // Nullable
  private final List<T> items;
  private final Integer offset; // Nullable
  private final Integer limit; // Nullable

  /**
   * Constructs a new ListQueryResult object.
   *
   * @param status  the status of the query result.
   * @param message the message associated with the query result (nullable).
   * @param items   the list of items returned by the query.
   * @param offset  the offset of the query result (nullable).
   * @param limit   the limit of the query result (nullable).
   */
  public ListQueryResult(Status status,
      String message,
      List<T> items,
      Integer offset,
      Integer limit) {
    this.status = status;
    this.message = message;
    this.items = items;
    this.offset = offset;
    this.limit = limit;
  }

  /**
   * Returns the status of the query result.
   *
   * @return the status of the query result.
   */
  public Status getStatus() {
    return status;
  }

  /**
   * Returns the message associated with the query result.
   *
   * @return the message associated with the query result, or null if no message
   *         is available.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Returns the list of items returned by the query.
   *
   * @return the list of items returned by the query.
   */
  public List<T> getItems() {
    return items;
  }

  /**
   * Returns the offset of the query result.
   *
   * @return the offset of the query result, or null if no offset is available.
   */
  public Integer getOffset() {
    return offset;
  }

  /**
   * Returns the limit of the query result.
   *
   * @return the limit of the query result, or null if no limit is available.
   */
  public Integer getLimit() {
    return limit;
  }

  /**
   * Represents the status of a list query result.
   */
  public enum Status {

    /**
     * Represents a successful query result.
     */
    SUCCESS,

    /**
     * Represents a failed query result.
     */
    FAILURE
  }
}
