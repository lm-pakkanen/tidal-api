package io.github.lm_pakkanen.tidal_api;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import io.github.lm_pakkanen.tidal_api.models.CredentialsStore;
import io.github.lm_pakkanen.tidal_api.models.entities.Credentials;

public final class TidalApiAuthorizationTest {
  final Configuration config = Configuration.getInstance();
  final String testClientId = config.__getTestClientId();
  final String testClientSecret = config.__getTestClientSecret();

  @Test
  void testAuthorize() throws Exception {
    CredentialsStore.destroyInstance();
    final TidalApi api = new TidalApi();
    assertDoesNotThrow(() -> api.authorize(testClientId, testClientSecret));

    final Credentials credentials = TidalApiAuthorizationTest.getCredentials(api);
    assertCredentialsValid(credentials);
  }

  @Test
  void testDoesNotReAuthorizeWithoutForce() throws Exception {
    CredentialsStore.destroyInstance();
    final TidalApi api = new TidalApi();

    api.authorize(testClientId, testClientSecret);
    final Credentials credentials = TidalApiAuthorizationTest.getCredentials(api);
    assertCredentialsValid(credentials);

    api.authorize(testClientId, testClientSecret);
    final Credentials credentials2 = TidalApiAuthorizationTest.getCredentials(api);
    assertCredentialsValid(credentials2);

    assertEquals(credentials, credentials2);
  }

  @Test
  void testReAuthorizesWithForce() throws Exception {
    CredentialsStore.destroyInstance();
    final TidalApi api = new TidalApi();

    api.authorize(testClientId, testClientSecret);
    final Credentials credentials = TidalApiAuthorizationTest.getCredentials(api);
    assertCredentialsValid(credentials);

    api.authorize(testClientId, testClientSecret, true);
    final Credentials credentials2 = TidalApiAuthorizationTest.getCredentials(api);
    assertCredentialsValid(credentials2);

    assertNotEquals(credentials, credentials2);
  }

  private static void assertCredentialsValid(Credentials credentials) {
    assertNotNull(credentials);

    final String accessToken = credentials.getAccessToken();
    final long expiresInSeconds = credentials.getExpiresInSeconds();

    assertNotNull(accessToken);
    assertFalse(accessToken.isEmpty());
    assertTrue(expiresInSeconds > 0);
  }

  private static Credentials getCredentials(TidalApi api) throws Exception {
    final Field credentialsStoreField = api.getClass().getDeclaredField("credentialsStore");
    credentialsStoreField.setAccessible(true);

    final CredentialsStore credentialsStore = (CredentialsStore) credentialsStoreField.get(api);

    final Field credentialsField = credentialsStore.getClass().getDeclaredField("credentials");
    credentialsField.setAccessible(true);

    final Credentials credentials = (Credentials) credentialsField.get(credentialsStore);

    return credentials;
  }
}
