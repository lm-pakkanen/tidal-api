package io.github.lm_pakkanen.tidal_api.models.entities;

import io.github.lm_pakkanen.tidal_api.models.tidal_responses.TidalTrackResponse;

public final class Track {
  private String id;
  private String title;
  private int durationSeconds;
  private SimpleAlbum album;
  private SimpleArtist[] artists;

  /**
   * Constructs a new Track entity from the given Tidal API response.
   * 
   * @param tidalTrackResponse the Tidal API response to be used.
   */
  public Track(TidalTrackResponse tidalTrackResponse) {
    final TidalTrackResponse.Resource resource = tidalTrackResponse.resource;

    this.id = resource.id;
    this.title = resource.title;
    this.album = new SimpleAlbum(resource.album);
    this.durationSeconds = resource.duration;

    this.artists = new SimpleArtist[resource.artists.length];

    for (int i = 0; i < resource.artists.length; i++) {
      this.artists[i] = new SimpleArtist(resource.artists[i]);
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
  public SimpleAlbum getAlbum() {
    return this.album;
  }

  /**
   * Gets the artists of this track.
   * 
   * @return the artists of this track.
   */
  public SimpleArtist[] getArtists() {
    return this.artists;
  }
}
