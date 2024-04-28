package io.github.lm_pakkanen.tidal_api.models.tidal_responses;

import java.util.List;

import io.github.lm_pakkanen.tidal_api.interfaces.TidalListResponse;
import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.SimpleAlbum;
import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.SimpleArtist;

public class TrackResponse {
  public Resource resource;

  public static class Resource {
    public String artifactType;
    public String id;
    public String title;
    public SimpleArtist[] artists;
    public SimpleAlbum album;
    public int duration;
    public int trackNumber;
    public int volumeNumber;
    public String isrc;
    public String copyright;
    public TrackResponse.MediaMetadata mediaMetadata;
    public TrackResponse.Properties properties;
    public String tidalUrl;
  }

  public static class MediaMetadata {
    public String[] tags;
  }

  public static class Properties {
    public String[] content;
  }

  public static class ListResponse implements TidalListResponse<TrackResponse> {
    public List<TrackResponse> data;

    public List<TrackResponse> getData() {
      return data;
    }
  }
}