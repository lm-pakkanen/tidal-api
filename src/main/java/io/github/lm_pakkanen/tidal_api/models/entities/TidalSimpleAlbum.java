package io.github.lm_pakkanen.tidal_api.models.entities;

import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.Image;
import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.SimpleAlbum;

public class TidalSimpleAlbum {
  public String id;
  public String title;
  public Image[] imageCovers;
  public Image[] videoCovers;

  public TidalSimpleAlbum(SimpleAlbum simpleAlbum) {
    this.id = simpleAlbum.id;
    this.title = simpleAlbum.title;
    this.imageCovers = simpleAlbum.imageCover;
    this.videoCovers = simpleAlbum.videoCover;
  }
}
