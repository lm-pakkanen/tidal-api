package io.github.lm_pakkanen.tidal_api.models.entities;

import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.Image;
import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.SimpleArtist;

/**
 * Represents a simple tidal artist.
 */
public class TidalSimpleArtist {

  /**
   * ID of the simple artist.
   */
  public String id;

  /**
   * Name of the simple artist.
   */
  public String name;

  /**
   * Pictures of the simple artist.
   */
  public Image[] pictures;

  /**
   * Whether if simple artist is the main artist of a given track.
   */
  public boolean isMainArtist;

  /**
   * Default constructor.
   */
  public TidalSimpleArtist() {
  }

  /**
   * Creates a new TidalSimpleArtist from a SimpleArtist tidal response.
   * 
   * @param simpleArtist the simple artist to create the TidalSimpleArtist from.
   */
  public TidalSimpleArtist(SimpleArtist simpleArtist) {
    this.id = simpleArtist.id;
    this.name = simpleArtist.name;
    this.pictures = simpleArtist.picture;
    this.isMainArtist = simpleArtist.main;
  }
}
