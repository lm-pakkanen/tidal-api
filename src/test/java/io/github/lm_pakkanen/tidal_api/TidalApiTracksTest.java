package io.github.lm_pakkanen.tidal_api;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.github.lm_pakkanen.tidal_api.models.entities.TidalSimpleAlbum;
import io.github.lm_pakkanen.tidal_api.models.entities.TidalSimpleArtist;
import io.github.lm_pakkanen.tidal_api.models.entities.TidalTrack;

public final class TidalApiTracksTest {
  private final static Configuration CONFIG = Configuration.getInstance();
  private final static String TEST_CLIENT_ID = TidalApiTracksTest.CONFIG.__getTestClientId();
  private final static String TEST_CLIENT_SECRET = TidalApiTracksTest.CONFIG.__getTestClientSecret();
  private final static String TEST_TRACK_ID = "345485959";
  private final static String TEST_COUNTRY_CODE = "US";
  private final static String TEST_ISRC = "USSM12209515";
  private final static String TEST_ARTIST_ID = "1566";

  @Test
  public void testList() throws Exception {
    rateLimit();
    final TidalApi api = new TidalApi();
    api.authorize(TidalApiTracksTest.TEST_CLIENT_ID, TidalApiTracksTest.TEST_CLIENT_SECRET);

    final TidalTrack[] tracks = api.tracks.list(new String[] { TEST_TRACK_ID }, TEST_COUNTRY_CODE);
    assertTracksValid(tracks);
  }

  @Test
  public void testListByArtist() throws Exception {
    rateLimit();
    final TidalApi api = new TidalApi();
    api.authorize(TidalApiTracksTest.TEST_CLIENT_ID, TidalApiTracksTest.TEST_CLIENT_SECRET);

    final TidalTrack[] tracks = api.tracks.listByArtist(TEST_ARTIST_ID, TEST_COUNTRY_CODE);
    assertTracksValid(tracks);
  }

  @Test
  public void testListByIsrc() throws Exception {
    rateLimit();
    final TidalApi api = new TidalApi();
    api.authorize(TidalApiTracksTest.TEST_CLIENT_ID, TidalApiTracksTest.TEST_CLIENT_SECRET);

    final TidalTrack[] tracks = api.tracks.listByIsrc(TEST_ISRC, TEST_COUNTRY_CODE);
    assertTracksValid(tracks);
  }

  @Test
  public void testListSimilar() throws Exception {
    rateLimit();
    final TidalApi api = new TidalApi();
    api.authorize(TidalApiTracksTest.TEST_CLIENT_ID, TidalApiTracksTest.TEST_CLIENT_SECRET);

    final TidalTrack[] tracks = api.tracks.listSimilar(TEST_TRACK_ID, TEST_COUNTRY_CODE);
    assertTracksValid(tracks);
  }

  @Test
  public void testGet() throws Exception {
    rateLimit();
    final TidalApi api = new TidalApi();
    api.authorize(TidalApiTracksTest.TEST_CLIENT_ID, TidalApiTracksTest.TEST_CLIENT_SECRET);

    final TidalTrack track = api.tracks.get(TEST_TRACK_ID, TEST_COUNTRY_CODE);
    assertTrackValid(track);
  }

  private void assertTracksValid(TidalTrack[] tracks) {
    assertInstanceOf(TidalTrack[].class, tracks);

    for (TidalTrack track : tracks) {
      assertTrackValid(track);
    }
  }

  private void assertTrackValid(TidalTrack track) {
    assertInstanceOf(TidalTrack.class, track);
    assertInstanceOf(String.class, track.getId());
    assertInstanceOf(String.class, track.getTitle());
    assertTrue(track.getDurationSeconds() > 0);
    assertInstanceOf(TidalSimpleAlbum.class, track.getAlbum());
    assertInstanceOf(TidalSimpleArtist[].class, track.getArtists());
  }

  /**
   * Tests are rate limited due to Tidal API restrictions.
   * Tests are run concurrently so delay all by the same 2 seconds.
   * If they're later run in parallel, increase the delay per test.
   */
  private void rateLimit() {
    final int delayMs = 2 * 1000;
    try {
      Thread.sleep(delayMs);
    } catch (InterruptedException e) {
      // no-op
    }
  }
}
