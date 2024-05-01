package io.github.lm_pakkanen.tidal_api.models.entities;

import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.Image;
import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.SimpleAlbum;

/**
 * Represents a simple tidal album.
 */
public class TidalSimpleAlbum {
  /**
   * ID of the simple album.
   */
  public String id;

  /**
   * Title of the simple album.
   */
  public String title;

  /**
   * Image covers of the simple album.
   */
  public Image[] imageCovers;

  /**
   * Video covers of the simple album.
   */
  public Image[] videoCovers;

  /**
   * Creates a new TidalSimpleAlbum from a SimpleAlbum tidal response.
   * 
   * @param simpleAlbum the simple album to create the TidalSimpleAlbum from.
   */
  public TidalSimpleAlbum(SimpleAlbum simpleAlbum) {
    this.id = simpleAlbum.id;
    this.title = simpleAlbum.title;
    this.imageCovers = simpleAlbum.imageCover;
    this.videoCovers = simpleAlbum.videoCover;
  }
}
