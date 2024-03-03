package io.github.lm_pakkanen.tidal_api.models.tidal_responses;

import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.TidalSimpleAlbum;
import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.TidalSimpleArtist;

public class TidalTrackResponse {
  public Resource resource;

  public static class Resource {
    public String artifactType;
    public String id;
    public String title;
    public TidalSimpleArtist[] artists;
    public TidalSimpleAlbum album;
    public int duration;
    public int trackNumber;
    public int volumeNumber;
    public String isrc;
    public String copyright;
    public TidalTrackResponse.MediaMetadata mediaMetadata;
    public TidalTrackResponse.Properties properties;
    public String tidalUrl;

  }

  public static class MediaMetadata {
    public String[] tags;
  }

  public static class Properties {
    public String[] content;
  }
}