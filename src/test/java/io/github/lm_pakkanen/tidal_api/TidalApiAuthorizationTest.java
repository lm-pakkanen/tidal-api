package io.github.lm_pakkanen.tidal_api;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import io.github.lm_pakkanen.tidal_api.models.CredentialsStore;
import io.github.lm_pakkanen.tidal_api.models.entities.TidalCredentials;

public final class TidalApiAuthorizationTest {
  private final static Configuration CONFIG = Configuration.getInstance();
  private final static String TEST_CLIENT_ID = TidalApiAuthorizationTest.CONFIG.__getTestClientId();
  private final static String TEST_CLIENT_SECRET = TidalApiAuthorizationTest.CONFIG.__getTestClientSecret();

  @Test
  void testAuthorize() throws Exception {
    CredentialsStore.destroyInstance();
    final TidalApi api = new TidalApi();

    assertDoesNotThrow(
        () -> {
          api.authorize(TidalApiAuthorizationTest.TEST_CLIENT_ID, TidalApiAuthorizationTest.TEST_CLIENT_SECRET);
        });

    final TidalCredentials credentials = TidalApiAuthorizationTest.getCredentials(api);
    assertCredentialsValid(credentials);
  }

  @Test
  void testDoesNotReAuthorizeWithoutForce() throws Exception {
    CredentialsStore.destroyInstance();
    final TidalApi api = new TidalApi();

    api.authorize(TidalApiAuthorizationTest.TEST_CLIENT_ID, TidalApiAuthorizationTest.TEST_CLIENT_SECRET);
    final TidalCredentials credentials = TidalApiAuthorizationTest.getCredentials(api);
    assertCredentialsValid(credentials);

    api.authorize(TidalApiAuthorizationTest.TEST_CLIENT_ID, TidalApiAuthorizationTest.TEST_CLIENT_SECRET);
    final TidalCredentials credentials2 = TidalApiAuthorizationTest.getCredentials(api);
    assertCredentialsValid(credentials2);

    assertEquals(credentials, credentials2);
  }

  @Test
  void testReAuthorizesWithForce() throws Exception {
    CredentialsStore.destroyInstance();
    final TidalApi api = new TidalApi();

    api.authorize(TidalApiAuthorizationTest.TEST_CLIENT_ID, TidalApiAuthorizationTest.TEST_CLIENT_SECRET);
    final TidalCredentials credentials = TidalApiAuthorizationTest.getCredentials(api);
    assertCredentialsValid(credentials);

    api.authorize(TidalApiAuthorizationTest.TEST_CLIENT_ID, TidalApiAuthorizationTest.TEST_CLIENT_SECRET, true);
    final TidalCredentials credentials2 = TidalApiAuthorizationTest.getCredentials(api);
    assertCredentialsValid(credentials2);

    assertNotEquals(credentials, credentials2);
  }

  private static void assertCredentialsValid(TidalCredentials credentials) {
    assertInstanceOf(TidalCredentials.class, credentials);

    final String accessToken = credentials.getAccessToken();
    final long expiresInSeconds = credentials.getExpiresInSeconds();

    assertNotNull(accessToken);
    assertFalse(accessToken.isEmpty());
    assertTrue(expiresInSeconds > 0);
  }

  private static TidalCredentials getCredentials(TidalApi api) throws Exception {
    final Field credentialsStoreField = api.getClass().getDeclaredField("credentialsStore");
    credentialsStoreField.setAccessible(true);

    final CredentialsStore credentialsStore = (CredentialsStore) credentialsStoreField.get(api);

    final Field credentialsField = credentialsStore.getClass().getDeclaredField("credentials");
    credentialsField.setAccessible(true);

    final TidalCredentials credentials = (TidalCredentials) credentialsField.get(credentialsStore);

    return credentials;
  }
}
