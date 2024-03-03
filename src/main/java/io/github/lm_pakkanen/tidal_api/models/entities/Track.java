package io.github.lm_pakkanen.tidal_api.models.entities;

import io.github.lm_pakkanen.tidal_api.models.tidal_responses.TidalTrackResponse;

public final class Track {
  private String id;
  private String title;
  private int durationSeconds;
  private SimpleAlbum album;
  private SimpleArtist[] artists;

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

  public String getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public int getDurationSeconds() {
    return this.durationSeconds;
  }

  public SimpleAlbum getAlbum() {
    return this.album;
  }

  public SimpleArtist[] getArtists() {
    return this.artists;
  }
}
