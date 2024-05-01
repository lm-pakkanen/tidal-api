package io.github.lm_pakkanen.tidal_api.interfaces;

import java.util.List;

/**
 * Represents a response from the Tidal API for a list of resources.
 * 
 * @param <T> the type of the resources in the list.
 */
public interface TidalListResponse<T> {
  /**
   * Returns the data of the list response.
   * 
   * @return the data of the list response.
   */
  public List<T> getData();
}
