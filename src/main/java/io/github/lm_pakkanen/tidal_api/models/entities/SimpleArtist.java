package io.github.lm_pakkanen.tidal_api.models.entities;

import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.TidalImage;
import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.TidalSimpleArtist;

public class SimpleArtist {
  public String id;
  public String name;
  public TidalImage[] pictures;
  public boolean isMainArtist;

  public SimpleArtist(TidalSimpleArtist tidalSimpleArtist) {
    this.id = tidalSimpleArtist.id;
    this.name = tidalSimpleArtist.name;
    this.pictures = tidalSimpleArtist.picture;
    this.isMainArtist = tidalSimpleArtist.main;
  }
}
