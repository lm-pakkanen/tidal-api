package io.github.lm_pakkanen.tidal_api.interfaces;

import java.util.List;

public interface TidalListResponse<T> {
  public List<T> getData();
}
