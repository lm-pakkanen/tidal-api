package io.github.lm_pakkanen.tidal_api.controllers.endpoints;

import java.util.List;

import io.github.lm_pakkanen.tidal_api.models.ListQueryResult;
import io.github.lm_pakkanen.tidal_api.models.entities.Credentials;
import io.github.lm_pakkanen.tidal_api.models.entities.Track;
import io.github.lm_pakkanen.tidal_api.models.exceptions.QueryException;
import io.github.lm_pakkanen.tidal_api.models.queries.BaseQuery;
import io.github.lm_pakkanen.tidal_api.models.queries.ListQuery;
import io.github.lm_pakkanen.tidal_api.models.queries.Query;
import io.github.lm_pakkanen.tidal_api.models.tidal_responses.TidalResourceResponse;
import io.github.lm_pakkanen.tidal_api.models.tidal_responses.TidalTrackResponse;

public final class TracksController extends BaseEndpointController {

  /**
   * @see TracksController#list(String[], String, Integer,
   *      Integer)
   */
  public Track[] list(String[] trackIds, String countryCode) throws QueryException {
    return this.list(trackIds, countryCode, null, null);
  }

  /**
   * @see TracksController#list(String[], String, Integer,
   *      Integer)
   */
  public Track[] list(String[] trackIds, String countryCode, Integer limit) throws QueryException {
    return this.list(trackIds, countryCode, null, limit);
  }

  /**
   * Gets a list of tracks by IDs from the Tidal API.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&ref=get-tracks-by-ids
   *
   * @param limit  The maximum number of tracks to retrieve. (required)
   * @param offset The offset for pagination. (required)
   *
   * @return the list of retrieved tracks.
   *
   * @throws QueryException if there is an error executing the query.
   */
  public Track[] list(String[] trackIds, String countryCode, Integer offset, Integer limit) throws QueryException {
    if (trackIds.length == 0) {
      throw new QueryException("trackIds is empty.");
    }

    if (countryCode == null || countryCode.isEmpty()) {
      throw new QueryException("countryCode is required.");
    }

    final Credentials credentials = BaseQuery.tryGetCredentialsOrQueryException();

    final String trackIdsAsString = String.join(",", trackIds);

    ListQuery query = new ListQuery(BaseEndpointController.TRACKS_URL)
        .contentType(BaseQuery.ContentType.TIDAL_JSON)
        .auth(credentials)
        .parameter("countryCode", countryCode)
        .parameter("ids", trackIdsAsString);

    if (offset != null) {
      query = query.offset(offset);
    }

    if (limit != null) {
      query = query.limit(limit);
    }

    final ListQueryResult<TidalTrackResponse> listQueryResult = query.execute(TidalTrackResponse.class,
        TidalTrackResponse.ListResponse.class, "data");

    final List<TidalTrackResponse> items = listQueryResult.getItems();
    final Track[] tracks = new Track[items.size()];

    for (int i = 0; i < items.size(); i++) {
      tracks[i] = new Track(items.get(i));
    }

    return tracks;
  }

  /**
   * @see TracksController#listByArtist(String, String, Integer,
   *      Integer)
   */
  public Track[] listByArtist(String artistId, String countryCode) throws QueryException {
    return this.listByArtist(artistId, countryCode, null, null);
  }

  /**
   * @see TracksController#listByArtist(String, String, Integer,
   *      Integer)
   */
  public Track[] listByArtist(String artistId, String countryCode, Integer limit) throws QueryException {
    return this.listByArtist(artistId, countryCode, null, limit);
  }

  /**
   * Gets a list of tracks by artist ID from the Tidal API.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&ref=get-tracks-by-artist
   *
   * @param limit  The maximum number of tracks to retrieve. (required)
   * @param offset The offset for pagination. (required)
   *
   * @return the list of retrieved tracks.
   *
   * @throws QueryException if there is an error executing the query.
   */
  public Track[] listByArtist(String artistId, String countryCode, Integer offset, Integer limit)
      throws QueryException {
    if (artistId == null || artistId.isEmpty()) {
      throw new QueryException("artistId is required.");
    }

    if (countryCode == null || countryCode.isEmpty()) {
      throw new QueryException("countryCode is required.");
    }

    final Credentials credentials = BaseQuery.tryGetCredentialsOrQueryException();

    final StringBuilder tracksByArtistUrlBuilder = new StringBuilder();
    tracksByArtistUrlBuilder.append(BaseEndpointController.ARTISTS_URL);
    tracksByArtistUrlBuilder.append("/");
    tracksByArtistUrlBuilder.append(artistId);
    tracksByArtistUrlBuilder.append("/tracks");

    final String tracksByArtistUrl = tracksByArtistUrlBuilder.toString();

    ListQuery query = new ListQuery(tracksByArtistUrl)
        .contentType(BaseQuery.ContentType.TIDAL_JSON)
        .auth(credentials)
        .parameter("countryCode", countryCode);

    if (offset != null) {
      query = query.offset(offset);
    }

    if (limit != null) {
      query = query.limit(limit);
    }

    final ListQueryResult<TidalTrackResponse> listQueryResult = query.execute(TidalTrackResponse.class,
        TidalTrackResponse.ListResponse.class, "data");

    final List<TidalTrackResponse> items = listQueryResult.getItems();
    final Track[] tracks = new Track[items.size()];

    for (int i = 0; i < items.size(); i++) {
      tracks[i] = new Track(items.get(i));
    }

    return tracks;
  }

  /**
   * @see TracksController#listByIsrc(String, String, Integer,
   *      Integer)
   */
  public Track[] listByIsrc(String isrc, String countryCode) throws QueryException {
    return this.listByIsrc(isrc, countryCode, null, null);
  }

  /**
   * @see TracksController#listByIsrc(String, String, Integer,
   *      Integer)
   */
  public Track[] listByIsrc(String isrc, String countryCode, Integer limit) throws QueryException {
    return this.listByIsrc(isrc, countryCode, null, limit);
  }

  /**
   * Gets a list of tracks by ISRC ID from the Tidal API.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&ref=get-tracks-by-isrc
   *
   * @param limit  The maximum number of tracks to retrieve. (required)
   * @param offset The offset for pagination. (required)
   *
   * @return the list of retrieved tracks.
   *
   * @throws QueryException if there is an error executing the query.
   */
  public Track[] listByIsrc(String isrc, String countryCode, Integer offset, Integer limit)
      throws QueryException {
    if (isrc == null || isrc.isEmpty()) {
      throw new QueryException("ISRC is required.");
    }

    if (countryCode == null || countryCode.isEmpty()) {
      throw new QueryException("countryCode is required.");
    }

    final Credentials credentials = BaseQuery.tryGetCredentialsOrQueryException();

    final StringBuilder tracksByIsrcUrlBuilder = new StringBuilder();
    tracksByIsrcUrlBuilder.append(BaseEndpointController.TRACKS_URL);
    tracksByIsrcUrlBuilder.append("/byIsrc");

    final String tracksByIsrcUrl = tracksByIsrcUrlBuilder.toString();

    ListQuery query = new ListQuery(tracksByIsrcUrl)
        .contentType(BaseQuery.ContentType.TIDAL_JSON)
        .auth(credentials)
        .parameter("isrc", isrc)
        .parameter("countryCode", countryCode);

    if (offset != null) {
      query = query.offset(offset);
    }

    if (limit != null) {
      query = query.limit(limit);
    }

    final ListQueryResult<TidalTrackResponse> listQueryResult = query.execute(TidalTrackResponse.class,
        TidalTrackResponse.ListResponse.class, "data");

    final List<TidalTrackResponse> items = listQueryResult.getItems();
    final Track[] tracks = new Track[items.size()];

    for (int i = 0; i < items.size(); i++) {
      tracks[i] = new Track(items.get(i));
    }

    return tracks;
  }

  /**
   * @see TracksController#listSimilar(String, String, Integer,
   *      Integer)
   */
  public Track[] listSimilar(String trackId, String countryCode) throws QueryException {
    return this.listSimilar(trackId, countryCode, null, null);
  }

  /**
   * @see TracksController#listSimilar(String, String, Integer,
   *      Integer)
   */
  public Track[] listSimilar(String trackId, String countryCode, Integer limit) throws QueryException {
    return this.listSimilar(trackId, countryCode, null, limit);
  }

  /**
   * Gets a list of similar tracks to ID from the Tidal API.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&ref=get-similar-tracks
   *
   * @param limit  The maximum number of tracks to retrieve. (required)
   * @param offset The offset for pagination. (required)
   *
   * @return the list of retrieved tracks.
   *
   * @throws QueryException if there is an error executing the query.
   */
  public Track[] listSimilar(String trackId, String countryCode, Integer offset, Integer limit)
      throws QueryException {
    if (trackId == null || trackId.isEmpty()) {
      throw new QueryException("trackId is required.");
    }

    if (countryCode == null || countryCode.isEmpty()) {
      throw new QueryException("countryCode is required.");
    }

    final Credentials credentials = BaseQuery.tryGetCredentialsOrQueryException();

    final StringBuilder similarTracksUrlBuilder = new StringBuilder();
    similarTracksUrlBuilder.append(BaseEndpointController.TRACKS_URL);
    similarTracksUrlBuilder.append("/");
    similarTracksUrlBuilder.append(trackId);
    similarTracksUrlBuilder.append("/similar");

    final String similarTracksUrl = similarTracksUrlBuilder.toString();

    ListQuery query = new ListQuery(similarTracksUrl)
        .contentType(BaseQuery.ContentType.TIDAL_JSON)
        .auth(credentials)
        .parameter("countryCode", countryCode);

    if (offset != null) {
      query = query.offset(offset);
    }

    if (limit != null) {
      query = query.limit(limit);
    }

    final ListQueryResult<TidalResourceResponse> listQueryResult = query.execute(TidalResourceResponse.class,
        TidalResourceResponse.ListResponse.class, "data");

    final List<String> similarTrackIds = listQueryResult.getItems().stream().map(n -> n.resource.id).toList();
    final String[] similarTrackIdsArray = similarTrackIds.toArray(new String[similarTrackIds.size()]);

    return this.list(similarTrackIdsArray, countryCode, offset, limit);
  }

  /**
   * Gets a specific track by ID from the Tidal API.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&ref=get-track
   * 
   * @param trackId The ID of the track to retrieve.
   * 
   * @return the retrieved track.
   * 
   * @throws QueryException if there is an error executing the query.
   */
  public Track get(String trackId, String countryCode) throws QueryException {
    if (trackId == null || trackId.isEmpty()) {
      throw new QueryException("trackId is required.");
    }

    if (countryCode == null || countryCode.isEmpty()) {
      throw new QueryException("countryCode is required.");
    }

    final Credentials credentials = BaseQuery.tryGetCredentialsOrQueryException();

    final StringBuilder trackUrlBuilder = new StringBuilder();
    trackUrlBuilder.append(BaseEndpointController.TRACKS_URL);
    trackUrlBuilder.append("/");
    trackUrlBuilder.append(trackId);

    final String trackUrl = trackUrlBuilder.toString();

    final Query query = new Query(trackUrl)
        .contentType(BaseQuery.ContentType.TIDAL_JSON)
        .auth(credentials)
        .parameter("countryCode", countryCode);

    final TidalTrackResponse tidalTrack = query.execute(TidalTrackResponse.class);
    return new Track(tidalTrack);
  }
}
