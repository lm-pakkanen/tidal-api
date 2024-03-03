package io.github.lm_pakkanen.tidal_api.controllers.endpoints;

import io.github.lm_pakkanen.tidal_api.models.entities.Credentials;
import io.github.lm_pakkanen.tidal_api.models.entities.Track;
import io.github.lm_pakkanen.tidal_api.models.exceptions.QueryException;
import io.github.lm_pakkanen.tidal_api.models.queries.BaseQuery;
import io.github.lm_pakkanen.tidal_api.models.queries.Query;
import io.github.lm_pakkanen.tidal_api.models.tidal_responses.TidalTrackResponse;

public final class TracksController extends BaseEndpointController {

  /**
   * Gets a specific track from the Tidal API.
   * 
   * @param trackId     The ID of the track to retrieve.
   * @param credentials The credentials used for authentication.
   * 
   * @return the retrieved track.
   * 
   * @throws QueryException if there is an error executing the query.
   */
  public Track get(String trackId, Credentials credentials) throws QueryException {
    final String trackUrl = BaseEndpointController.TRACKS_URL + "/" + trackId;

    final Query query = new Query(trackUrl)
        .contentType(BaseQuery.ContentType.TIDAL_JSON)
        .auth(credentials)
        .parameter("countryCode", "FI");

    final TidalTrackResponse tidalTrack = query.execute(TidalTrackResponse.class);

    return new Track(tidalTrack);
  }
}
