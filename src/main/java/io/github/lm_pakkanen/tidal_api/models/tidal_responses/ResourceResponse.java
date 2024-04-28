package io.github.lm_pakkanen.tidal_api.models.tidal_responses;

import java.util.List;

import io.github.lm_pakkanen.tidal_api.interfaces.TidalListResponse;

public class ResourceResponse {
  public Resource resource;

  public static class Resource {
    public String id;
  }

  public static class ListResponse implements TidalListResponse<ResourceResponse> {
    public List<ResourceResponse> data;

    public List<ResourceResponse> getData() {
      return data;
    }
  }
}
