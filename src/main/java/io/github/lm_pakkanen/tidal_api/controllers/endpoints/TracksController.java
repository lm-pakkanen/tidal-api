package io.github.lm_pakkanen.tidal_api.controllers.endpoints;

import java.util.List;

import io.github.lm_pakkanen.tidal_api.models.ListQueryResult;
import io.github.lm_pakkanen.tidal_api.models.entities.TidalCredentials;
import io.github.lm_pakkanen.tidal_api.models.entities.TidalTrack;
import io.github.lm_pakkanen.tidal_api.models.exceptions.QueryException;
import io.github.lm_pakkanen.tidal_api.models.queries.BaseQuery;
import io.github.lm_pakkanen.tidal_api.models.queries.ListQuery;
import io.github.lm_pakkanen.tidal_api.models.queries.Query;
import io.github.lm_pakkanen.tidal_api.models.tidal_responses.ResourceResponse;
import io.github.lm_pakkanen.tidal_api.models.tidal_responses.TrackResponse;

/**
 * Controller for the Tidal API tracks endpoint.
 */
public final class TracksController extends BaseEndpointController {

  /**
   * Default constructor.
   */
  public TracksController() {
  }

  /**
   * Gets a list of tracks by IDs from the Tidal API.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&amp;ref=get-tracks-by-ids
   * 
   * @see TracksController#list(String[], String, Integer,
   *      Integer)
   *
   * @param trackIds    the IDs of the tracks to retrieve.
   * @param countryCode the country code for the tracks.
   *
   * @return the list of retrieved tracks.
   *
   * @throws QueryException if there is an error executing the query.
   */
  public TidalTrack[] list(String[] trackIds, String countryCode) throws QueryException {
    return this.list(trackIds, countryCode, null, null);
  }

  /**
   * Gets a list of tracks by IDs from the Tidal API. Supports limiting the
   * response.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&amp;ref=get-tracks-by-ids
   * 
   * @see TracksController#list(String[], String, Integer,
   *      Integer)
   *
   * @param trackIds    the IDs of the tracks to retrieve.
   * @param countryCode the country code for the tracks.
   * @param limit       The maximum number of tracks to retrieve. (required)
   *
   * @return the list of retrieved tracks.
   *
   * @throws QueryException if there is an error executing the query.
   */
  public TidalTrack[] list(String[] trackIds, String countryCode, Integer limit) throws QueryException {
    return this.list(trackIds, countryCode, null, limit);
  }

  /**
   * Gets a list of tracks by IDs from the Tidal API. Supports limiting and
   * offsetting the response.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&amp;ref=get-tracks-by-ids
   *
   * @param trackIds    the IDs of the tracks to retrieve.
   * @param countryCode the country code for the tracks.
   * @param offset      The offset for pagination. (required)
   * @param limit       The maximum number of tracks to retrieve. (required)
   *
   * @return the list of retrieved tracks.
   *
   * @throws QueryException if there is an error executing the query.
   */
  public TidalTrack[] list(String[] trackIds, String countryCode, Integer offset, Integer limit) throws QueryException {
    if (trackIds.length == 0) {
      throw new QueryException("trackIds is empty.");
    }

    if (countryCode == null || countryCode.isEmpty()) {
      throw new QueryException("countryCode is required.");
    }

    final TidalCredentials credentials = BaseQuery.tryGetCredentialsOrQueryException();

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

    final ListQueryResult<TrackResponse> listQueryResult = query.execute(TrackResponse.class,
        TrackResponse.ListResponse.class, "data");

    final List<TrackResponse> items = listQueryResult.getItems();
    final TidalTrack[] tracks = new TidalTrack[items.size()];

    for (int i = 0; i < items.size(); i++) {
      tracks[i] = new TidalTrack(items.get(i));
    }

    return tracks;
  }

  /**
   * Gets a list of tracks by artist ID from the Tidal API.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&amp;ref=get-tracks-by-artist
   * 
   * @see TracksController#listByArtist(String, String, Integer,
   *      Integer)
   *
   * @param artistId    The ID of the artist to retrieve tracks for. (required)
   * @param countryCode The country code for the tracks. (required)
   *
   * @return the list of retrieved tracks.
   *
   * @throws QueryException if there is an error executing the query.
   */
  public TidalTrack[] listByArtist(String artistId, String countryCode) throws QueryException {
    return this.listByArtist(artistId, countryCode, null, null);
  }

  /**
   * Gets a list of tracks by artist ID from the Tidal API. Supports limiting the
   * response.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&amp;ref=get-tracks-by-artist
   * 
   * @see TracksController#listByArtist(String, String, Integer,
   *      Integer)
   *
   * @param artistId    The ID of the artist to retrieve tracks for. (required)
   * @param countryCode The country code for the tracks. (required)
   * @param limit       The maximum number of tracks to retrieve. (required)
   *
   * @return the list of retrieved tracks.
   *
   * @throws QueryException if there is an error executing the query.
   */
  public TidalTrack[] listByArtist(String artistId, String countryCode, Integer limit) throws QueryException {
    return this.listByArtist(artistId, countryCode, null, limit);
  }

  /**
   * Gets a list of tracks by artist ID from the Tidal API. Supports limiting and
   * offsetting the response.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&amp;ref=get-tracks-by-artist
   *
   * @param artistId    The ID of the artist to retrieve tracks for. (required)
   * @param countryCode The country code for the tracks. (required)
   * @param offset      The offset for pagination. (required)
   * @param limit       The maximum number of tracks to retrieve. (required)
   *
   * @return the list of retrieved tracks.
   *
   * @throws QueryException if there is an error executing the query.
   */
  public TidalTrack[] listByArtist(String artistId, String countryCode, Integer offset, Integer limit)
      throws QueryException {
    if (artistId == null || artistId.isEmpty()) {
      throw new QueryException("artistId is required.");
    }

    if (countryCode == null || countryCode.isEmpty()) {
      throw new QueryException("countryCode is required.");
    }

    final TidalCredentials credentials = BaseQuery.tryGetCredentialsOrQueryException();

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

    final ListQueryResult<TrackResponse> listQueryResult = query.execute(TrackResponse.class,
        TrackResponse.ListResponse.class, "data");

    final List<TrackResponse> items = listQueryResult.getItems();
    final TidalTrack[] tracks = new TidalTrack[items.size()];

    for (int i = 0; i < items.size(); i++) {
      tracks[i] = new TidalTrack(items.get(i));
    }

    return tracks;
  }

  /**
   * Gets a list of tracks by ISRC ID from the Tidal API.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&amp;ref=get-tracks-by-isrc
   * 
   * @see TracksController#listByIsrc(String, String, Integer,
   *      Integer)
   *
   * @param isrc        The ISRC code of the track to retrieve. (required)
   * @param countryCode The country code for the tracks. (required)
   *
   * @return the list of retrieved tracks.
   *
   * @throws QueryException if there is an error executing the query.
   */
  public TidalTrack[] listByIsrc(String isrc, String countryCode) throws QueryException {
    return this.listByIsrc(isrc, countryCode, null, null);
  }

  /**
   * Gets a list of tracks by ISRC ID from the Tidal API. Supports limiting the
   * response.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&amp;ref=get-tracks-by-isrc
   * 
   * @see TracksController#listByIsrc(String, String, Integer,
   *      Integer)
   *
   * @param isrc        The ISRC code of the track to retrieve. (required)
   * @param countryCode The country code for the tracks. (required)
   * @param limit       The maximum number of tracks to retrieve. (required)
   *
   * @return the list of retrieved tracks.
   *
   * @throws QueryException if there is an error executing the query.
   */
  public TidalTrack[] listByIsrc(String isrc, String countryCode, Integer limit) throws QueryException {
    return this.listByIsrc(isrc, countryCode, null, limit);
  }

  /**
   * Gets a list of tracks by ISRC ID from the Tidal API. Supports limiting and
   * offsetting the response.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&amp;ref=get-tracks-by-isrc
   *
   * @param isrc        The ISRC code of the track to retrieve. (required)
   * @param countryCode The country code for the tracks. (required)
   * @param offset      The offset for pagination. (required)
   * @param limit       The maximum number of tracks to retrieve. (required)
   *
   * @return the list of retrieved tracks.
   *
   * @throws QueryException if there is an error executing the query.
   */
  public TidalTrack[] listByIsrc(String isrc, String countryCode, Integer offset, Integer limit)
      throws QueryException {
    if (isrc == null || isrc.isEmpty()) {
      throw new QueryException("ISRC is required.");
    }

    if (countryCode == null || countryCode.isEmpty()) {
      throw new QueryException("countryCode is required.");
    }

    final TidalCredentials credentials = BaseQuery.tryGetCredentialsOrQueryException();

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

    final ListQueryResult<TrackResponse> listQueryResult = query.execute(TrackResponse.class,
        TrackResponse.ListResponse.class, "data");

    final List<TrackResponse> items = listQueryResult.getItems();
    final TidalTrack[] tracks = new TidalTrack[items.size()];

    for (int i = 0; i < items.size(); i++) {
      tracks[i] = new TidalTrack(items.get(i));
    }

    return tracks;
  }

  /**
   * Gets a list of similar tracks to ID from the Tidal API.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&amp;ref=get-similar-tracks
   * 
   * @see TracksController#listSimilar(String, String, Integer,
   *      Integer)
   *
   * @param trackId     The ID of the track to retrieve similar tracks for.
   *                    (required)
   * @param countryCode The country code for the tracks. (required)
   *
   * @return the list of retrieved tracks.
   *
   * @throws QueryException if there is an error executing the query.
   */
  public TidalTrack[] listSimilar(String trackId, String countryCode) throws QueryException {
    return this.listSimilar(trackId, countryCode, null, null);
  }

  /**
   * Gets a list of similar tracks to ID from the Tidal API. Supports limiting the
   * response.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&amp;ref=get-similar-tracks
   * 
   * @see TracksController#listSimilar(String, String, Integer,
   *      Integer)
   *
   * @param trackId     The ID of the track to retrieve similar tracks for.
   *                    (required)
   * @param countryCode The country code for the tracks. (required)
   * @param limit       The maximum number of tracks to retrieve. (required)
   *
   * @return the list of retrieved tracks.
   *
   * @throws QueryException if there is an error executing the query.
   */
  public TidalTrack[] listSimilar(String trackId, String countryCode, Integer limit) throws QueryException {
    return this.listSimilar(trackId, countryCode, null, limit);
  }

  /**
   * Gets a list of similar tracks to ID from the Tidal API. Supports limiting and
   * offsetting the response.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&amp;ref=get-similar-tracks
   *
   * @param trackId     The ID of the track to retrieve similar tracks for.
   *                    (required)
   * @param countryCode The country code for the tracks. (required)
   * @param offset      The offset for pagination. (required)
   * @param limit       The maximum number of tracks to retrieve. (required)
   *
   * @return the list of retrieved tracks.
   *
   * @throws QueryException if there is an error executing the query.
   */
  public TidalTrack[] listSimilar(String trackId, String countryCode, Integer offset, Integer limit)
      throws QueryException {
    if (trackId == null || trackId.isEmpty()) {
      throw new QueryException("trackId is required.");
    }

    if (countryCode == null || countryCode.isEmpty()) {
      throw new QueryException("countryCode is required.");
    }

    final TidalCredentials credentials = BaseQuery.tryGetCredentialsOrQueryException();

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

    final ListQueryResult<ResourceResponse> listQueryResult = query.execute(ResourceResponse.class,
        ResourceResponse.ListResponse.class, "data");

    final List<String> similarTrackIds = listQueryResult.getItems().stream().map(n -> n.resource.id).toList();
    final String[] similarTrackIdsArray = similarTrackIds.toArray(new String[similarTrackIds.size()]);

    return this.list(similarTrackIdsArray, countryCode, offset, limit);
  }

  /**
   * Gets a specific track by ID from the Tidal API.
   * 
   * https://developer.tidal.com/apiref?spec=catalogue&amp;ref=get-track
   * 
   * @param trackId     The ID of the track to retrieve.
   * @param countryCode The country code for the track.
   * 
   * @return the retrieved track.
   * 
   * @throws QueryException if there is an error executing the query.
   */
  public TidalTrack get(String trackId, String countryCode) throws QueryException {
    if (trackId == null || trackId.isEmpty()) {
      throw new QueryException("trackId is required.");
    }

    if (countryCode == null || countryCode.isEmpty()) {
      throw new QueryException("countryCode is required.");
    }

    final TidalCredentials credentials = BaseQuery.tryGetCredentialsOrQueryException();

    final StringBuilder trackUrlBuilder = new StringBuilder();
    trackUrlBuilder.append(BaseEndpointController.TRACKS_URL);
    trackUrlBuilder.append("/");
    trackUrlBuilder.append(trackId);

    final String trackUrl = trackUrlBuilder.toString();

    final Query query = new Query(trackUrl)
        .contentType(BaseQuery.ContentType.TIDAL_JSON)
        .auth(credentials)
        .parameter("countryCode", countryCode);

    final TrackResponse tidalTrack = query.execute(TrackResponse.class);
    return new TidalTrack(tidalTrack);
  }
}
