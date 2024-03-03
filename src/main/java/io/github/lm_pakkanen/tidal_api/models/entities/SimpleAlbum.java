package io.github.lm_pakkanen.tidal_api.models.entities;

import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.TidalImage;
import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.TidalSimpleAlbum;

public class SimpleAlbum {
  public String id;
  public String title;
  public TidalImage[] imageCovers;
  public TidalImage[] videoCovers;

  public SimpleAlbum(TidalSimpleAlbum tidalSimpleAlbum) {
    this.id = tidalSimpleAlbum.id;
    this.title = tidalSimpleAlbum.title;
    this.imageCovers = tidalSimpleAlbum.imageCover;
    this.videoCovers = tidalSimpleAlbum.videoCover;
  }
}
