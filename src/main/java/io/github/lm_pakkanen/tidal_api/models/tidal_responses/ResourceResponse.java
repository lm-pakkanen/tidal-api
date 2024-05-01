package io.github.lm_pakkanen.tidal_api.models.tidal_responses;

import java.util.List;

import io.github.lm_pakkanen.tidal_api.interfaces.TidalListResponse;

/**
 * Represents a response from the Tidal API for a resource.
 */
public class ResourceResponse {

  /**
   * Resource of the resource response.
   */
  public Resource resource;

  /**
   * Default constructor.
   */
  public ResourceResponse() {
  }

  /**
   * Represents the resource of a resource response.
   */
  public static class Resource {
    /**
     * Resource ID of the resource response.
     */
    public String id;

    /**
     * Default constructor.
     */
    public Resource() {
    }
  }

  /**
   * Represents a list response from the Tidal API for a resource.
   */
  public static class ListResponse implements TidalListResponse<ResourceResponse> {
    /**
     * Data of the list response.
     */
    public List<ResourceResponse> data;

    /**
     * Default constructor.
     */
    public ListResponse() {
    }

    /**
     * Returns the data of the list response.
     * 
     * @return the data of the list response.
     */
    public List<ResourceResponse> getData() {
      return data;
    }
  }
}
