package io.github.lm_pakkanen.tidal_api.models.tidal_responses;

import java.util.List;

import io.github.lm_pakkanen.tidal_api.interfaces.TidalListResponse;

public class TidalResourceResponse {
  public Resource resource;

  public static class Resource {
    public String id;
  }

  public static class ListResponse implements TidalListResponse<TidalResourceResponse> {
    public List<TidalResourceResponse> data;

    public List<TidalResourceResponse> getData() {
      return data;
    }
  }
}
