package io.github.lm_pakkanen.tidal_api.endpoint_controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Base class for all endpoint controllers.
 * Provides useful ields & methods for the endpoint controllers.
 */
public class BaseEndpointController {
  private static final String API_VERSION = "v1";
  protected static final String BASE_URL = "https://openapi.tidal.com/" + BaseEndpointController.API_VERSION;

  protected static final String AUTHORIZATION_URL = "https://auth.tidal.com/v1/oauth2/token";

  /**
   * Converts the response from the Tidal API to a string.
   * 
   * Utilizes the input stream reader and buffer reader from the parent class.
   * 
   * @param inputStreamReader input stream reader.
   * @param bufferedReader    buffer reader.
   * @param connection        HTTP connection.
   * 
   * @return response as a string.
   * 
   * @throws IOException
   */
  public static String responseToString(InputStreamReader inputStreamReader, BufferedReader bufferedReader,
      HttpURLConnection connection) throws IOException {
    inputStreamReader = new InputStreamReader(connection.getInputStream());
    bufferedReader = new BufferedReader(inputStreamReader);

    final StringBuilder responseBuilder = new StringBuilder();

    String line;

    while ((line = bufferedReader.readLine()) != null) {
      responseBuilder.append(line);
    }

    inputStreamReader.close();
    bufferedReader.close();

    final String responseString = responseBuilder.toString();
    return responseString;
  }
}
