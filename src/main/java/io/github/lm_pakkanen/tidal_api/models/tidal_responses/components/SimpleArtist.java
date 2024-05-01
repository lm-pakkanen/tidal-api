package io.github.lm_pakkanen.tidal_api.models.tidal_responses.components;

/**
 * Represents a simple artist in a tidal response.
 */
public class SimpleArtist {

  /**
   * ID of the simple artist.
   */
  public String id;

  /**
   * Name of the simple artist.
   */
  public String name;

  /**
   * Picture of the simple artist.
   */
  public Image[] picture;

  /**
   * Whether if simple artist is the main artist of a given track.
   */
  public boolean main;

  /**
   * Default constructor.
   */
  public SimpleArtist() {
  }
}
