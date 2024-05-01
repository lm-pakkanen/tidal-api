package io.github.lm_pakkanen.tidal_api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration for this package.
 * 
 */
public final class Configuration {

  private static Configuration INSTANCE;
  private final Properties properties;

  /**
   * Constructor for the Configuration singleton.
   * 
   * Loads package configurations.
   * 
   * @throws IOException
   * @throws IllegalArgumentException
   * @throws NullPointerException
   */
  private Configuration()
      throws IOException, IllegalArgumentException, NullPointerException {
    final ClassLoader classLoader = this.getClass().getClassLoader();
    final Properties properties = Configuration.getProperties(classLoader);
    this.properties = properties;
  }

  /**
   * Gets the instance of the Configuration.
   * 
   * @return the instance of the Configuration.
   */
  public static Configuration getInstance() {
    try {
      if (Configuration.INSTANCE == null) {
        Configuration.INSTANCE = new Configuration();
      }

      return Configuration.INSTANCE;
    } catch (IOException | IllegalArgumentException | NullPointerException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Gets the version of this package.
   * 
   * @return the version of this package
   */
  public String getVersion() {
    return this.properties.getProperty("VERSION");
  }

  /**
   * Gets the test client id for the Tidal API. Used only in development and won't
   * return anything in production.
   * 
   * @return the test client id for the Tidal API.
   */
  public String __getTestClientId() {
    return this.properties.getProperty("CLIENT_ID_TEST");
  }

  /**
   * Gets the test client secret for the Tidal API. Used only in development and
   * won't
   * return anything in production.
   * 
   * @return the test client secret for the Tidal API.
   */
  public String __getTestClientSecret() {
    return this.properties.getProperty("CLIENT_SECRET_TEST");
  }

  /**
   * Gets the properties file name used in this package.
   * 
   * @param isTestEnv whether to get the test filename or normal filename.
   * @return the properties file name used in this package.
   */
  private static String getPropertiesFileName(boolean isTestEnv) {
    if (isTestEnv) {
      return "tidal.test.properties";
    }

    return "tidal.properties";
  }

  /**
   * Gets the properties/settings for this package.
   * 
   * @param classLoader the class loader to use for loading the properties file.
   * 
   * @return the properties/settings for this package.
   * 
   * @throws IOException
   */
  private static Properties getProperties(ClassLoader classLoader) throws IOException {
    final Properties properties = new Properties();

    final String commonPropertiesFileName = Configuration.getPropertiesFileName(false);

    final InputStream commonPropertiesStream = classLoader
        .getResourceAsStream(commonPropertiesFileName);

    properties.load(commonPropertiesStream);

    try {
      // Try to load test properties, ignore exceptions if not found
      final String testPropertiesFileName = Configuration.getPropertiesFileName(true);
      final InputStream testPropertiesStream = classLoader.getResourceAsStream(testPropertiesFileName);
      properties.load(testPropertiesStream);
    } catch (Exception ex) {
      // no-op
    }

    return properties;
  }
}
