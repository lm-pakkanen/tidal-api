package io.github.lm_pakkanen.tidal_api.models;

import io.github.lm_pakkanen.tidal_api.models.entities.Credentials;

public final class CredentialsStore {
  private static CredentialsStore INSTANCE;

  private Credentials credentials;

  private CredentialsStore() {
  }

  public static CredentialsStore getInstance() {
    if (CredentialsStore.INSTANCE == null) {
      CredentialsStore.INSTANCE = new CredentialsStore();
    }

    return CredentialsStore.INSTANCE;
  }

  public void setCredentials(Credentials credentials) {
    this.credentials = credentials;
  }

  public Credentials getCredentials() {
    return this.credentials;
  }
}
