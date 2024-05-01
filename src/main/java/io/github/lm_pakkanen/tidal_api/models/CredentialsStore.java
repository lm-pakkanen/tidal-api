package io.github.lm_pakkanen.tidal_api.models;

import io.github.lm_pakkanen.tidal_api.models.entities.TidalCredentials;

/**
 * Singleton class for storing Tidal API credentials.
 */
public final class CredentialsStore {
  private static CredentialsStore INSTANCE;

  private TidalCredentials credentials;

  /**
   * Private constructor to prevent instantiation.
   */
  private CredentialsStore() {
  }

  /**
   * Get the singleton instance of the CredentialsStore.
   * 
   * @return the singleton instance of the CredentialsStore.
   */
  public static CredentialsStore getInstance() {
    if (CredentialsStore.INSTANCE == null) {
      CredentialsStore.INSTANCE = new CredentialsStore();
    }

    return CredentialsStore.INSTANCE;
  }

  /**
   * Destroy the singleton instance of the CredentialsStore.
   */
  public static void destroyInstance() {
    CredentialsStore.INSTANCE = null;
  }

  /**
   * Set the Tidal API credentials.
   * 
   * @param credentials the Tidal API credentials.
   */
  public void setCredentials(TidalCredentials credentials) {
    this.credentials = credentials;
  }

  /**
   * Get the Tidal API credentials.
   * 
   * @return the Tidal API credentials.
   */
  public TidalCredentials getCredentials() {
    return this.credentials;
  }
}
