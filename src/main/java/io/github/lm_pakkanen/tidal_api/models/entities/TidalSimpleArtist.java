package io.github.lm_pakkanen.tidal_api.models.entities;

import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.Image;
import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.SimpleArtist;

public class TidalSimpleArtist {
  public String id;
  public String name;
  public Image[] pictures;
  public boolean isMainArtist;

  public TidalSimpleArtist(SimpleArtist simpleArtist) {
    this.id = simpleArtist.id;
    this.name = simpleArtist.name;
    this.pictures = simpleArtist.picture;
    this.isMainArtist = simpleArtist.main;
  }
}
