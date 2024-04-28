package io.github.lm_pakkanen.tidal_api.models;

import io.github.lm_pakkanen.tidal_api.models.entities.TidalCredentials;

public final class CredentialsStore {
  private static CredentialsStore INSTANCE;

  private TidalCredentials credentials;

  private CredentialsStore() {
  }

  public static CredentialsStore getInstance() {
    if (CredentialsStore.INSTANCE == null) {
      CredentialsStore.INSTANCE = new CredentialsStore();
    }

    return CredentialsStore.INSTANCE;
  }

  public static void destroyInstance() {
    CredentialsStore.INSTANCE = null;
  }

  public void setCredentials(TidalCredentials credentials) {
    this.credentials = credentials;
  }

  public TidalCredentials getCredentials() {
    return this.credentials;
  }
}
