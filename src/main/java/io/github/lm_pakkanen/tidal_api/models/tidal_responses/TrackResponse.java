package io.github.lm_pakkanen.tidal_api.models.tidal_responses;

import java.util.List;

import io.github.lm_pakkanen.tidal_api.interfaces.TidalListResponse;
import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.SimpleAlbum;
import io.github.lm_pakkanen.tidal_api.models.tidal_responses.components.SimpleArtist;

/**
 * Represents a response from the Tidal API for a track.
 */
public class TrackResponse {

  /**
   * Resource of the track response.
   */
  public Resource resource;

  /**
   * Empty default constructor.
   */
  private TrackResponse() {
  }

  /**
   * Represents the resource of a track response.
   */
  public static class Resource {

    /**
     * Artifact type of the track response.
     */
    public String artifactType;

    /**
     * ID of the track response.
     */
    public String id;

    /**
     * Title of the track response.
     */
    public String title;

    /**
     * URL of the track response.
     */
    public SimpleArtist[] artists;

    /**
     * Album of the track response.
     */
    public SimpleAlbum album;

    /**
     * Duration of the track response.
     */
    public int duration;

    /**
     * Track number of the track response.
     */
    public int trackNumber;

    /**
     * Volume number of the track response.
     */
    public int volumeNumber;

    /**
     * ISRC code of the track response.
     */
    public String isrc;

    /**
     * Copyright information of the track response.
     */
    public String copyright;

    /**
     * Media metadata of the track response.
     */
    public TrackResponse.MediaMetadata mediaMetadata;

    /**
     * Properties of the track response.
     */
    public TrackResponse.Properties properties;

    /**
     * Tidal URL of the track response.
     */
    public String tidalUrl;

    /**
     * Empty default constructor.
     */
    private Resource() {
    }
  }

  /**
   * Represents the media metadata of a track response.
   */
  public static class MediaMetadata {
    /**
     * Media tags of the track response.
     */
    public String[] tags;

    /**
     * Empty default constructor.
     */
    private MediaMetadata() {
    }
  }

  /**
   * Represents the properties of a track response.
   */
  public static class Properties {

    /**
     * Additional properties of the track response.
     */
    public String[] content;

    /**
     * Empty default constructor.
     */
    private Properties() {
    }
  }

  /**
   * Represents a list response from the Tidal API for a track.
   */
  public static class ListResponse implements TidalListResponse<TrackResponse> {

    /**
     * Data of the list response.
     */
    public List<TrackResponse> data;

    /**
     * Empty default constructor.
     */
    private ListResponse() {
    }

    /**
     * Returns the data of the list response.
     * 
     * @return the data of the list response.
     */
    public List<TrackResponse> getData() {
      return data;
    }
  }
}