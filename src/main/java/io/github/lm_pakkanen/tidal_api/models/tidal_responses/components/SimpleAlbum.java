package io.github.lm_pakkanen.tidal_api.models.tidal_responses.components;

/**
 * Represents a simple album in a tidal response.
 */
public class SimpleAlbum {

  /**
   * ID of the simple album.
   */
  public String id;

  /**
   * Title of the simple album.
   */
  public String title;

  /**
   * Image cover of the simple album.
   */
  public Image[] imageCover;

  /**
   * Video cover of the simple album.
   */
  public Image[] videoCover;

  /**
   * Default constructor.
   */
  public SimpleAlbum() {
  }
}
