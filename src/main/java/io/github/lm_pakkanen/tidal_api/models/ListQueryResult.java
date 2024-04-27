package io.github.lm_pakkanen.tidal_api.models;

import java.util.List;

public final class ListQueryResult<T> {
  private final Status status;
  private final String message; // Nullable
  private final List<T> items;
  private final Integer offset; // Nullable
  private final Integer limit; // Nullable

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

  public Status getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public List<T> getItems() {
    return items;
  }

  public Integer getOffset() {
    return offset;
  }

  public Integer getLimit() {
    return limit;
  }

  public enum Status {
    SUCCESS,
    FAILURE
  }
}
