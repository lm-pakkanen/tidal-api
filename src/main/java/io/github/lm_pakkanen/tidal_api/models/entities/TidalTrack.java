package io.github.lm_pakkanen.tidal_api.models.entities;

import io.github.lm_pakkanen.tidal_api.models.tidal_responses.TrackResponse;

public final class TidalTrack {
  private String id;
  private String title;
  private int durationSeconds;
  private TidalSimpleAlbum album;
  private TidalSimpleArtist[] artists;

  /**
   * Constructs a new Track entity from the given Tidal API response.
   * 
   * @param trackResponse the Tidal API response to be used.
   */
  public TidalTrack(TrackResponse trackResponse) {
    final TrackResponse.Resource resource = trackResponse.resource;

    this.id = resource.id;
    this.title = resource.title;
    this.album = new TidalSimpleAlbum(resource.album);
    this.durationSeconds = resource.duration;

    this.artists = new TidalSimpleArtist[resource.artists.length];

    for (int i = 0; i < resource.artists.length; i++) {
      this.artists[i] = new TidalSimpleArtist(resource.artists[i]);
    }
  }

  /**
   * Gets the ID of this track.
   * 
   * @return the ID of this track.
   */
  public String getId() {
    return this.id;
  }

  /**
   * Gets the title of this track.
   * 
   * @return the title of this track.
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Gets the duration of this track in seconds.
   * 
   * @return the duration of this track in seconds.
   */
  public int getDurationSeconds() {
    return this.durationSeconds;
  }

  /**
   * Gets the album of this track.
   * 
   * @return the album of this track.
   */
  public TidalSimpleAlbum getAlbum() {
    return this.album;
  }

  /**
   * Gets the artists of this track.
   * 
   * @return the artists of this track.
   */
  public TidalSimpleArtist[] getArtists() {
    return this.artists;
  }
}
