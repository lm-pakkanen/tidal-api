package io.github.lm_pakkanen.tidal_api.models.queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import com.fasterxml.jackson.jr.ob.JSON;

import io.github.lm_pakkanen.tidal_api.controllers.endpoints.AuthorizationController;
import io.github.lm_pakkanen.tidal_api.models.entities.TidalCredentials;
import io.github.lm_pakkanen.tidal_api.models.exceptions.InvalidCredentialsException;
import io.github.lm_pakkanen.tidal_api.models.exceptions.QueryException;
import io.github.lm_pakkanen.tidal_api.models.exceptions.UnauthorizedException;

/**
 * The 'BaseQuery' class represents a base query for making HTTP requests to the
 * Tidal API.
 * It provides methods for setting various parameters such as HTTP method,
 * content type, authentication, body, limit, skip, and country code.
 * The 'BaseQuery' class also includes a method for building the query and
 * returning the 'HttpURLConnection' object.
 */
public class BaseQuery {
  /**
   * Represents the HTTP connection.
   */
  protected HttpURLConnection connection;

  /**
   * Represents the HTTP method.
   */
  protected BaseQuery.HttpMethod httpMethod;

  /**
   * Represents the content type.
   */
  protected BaseQuery.ContentType contentType;

  /**
   * Represents the base64-encoded basic authentication credentials.
   */
  protected String basicCredentialsBase64;

  /**
   * Represents the authentication credentials.
   */
  protected TidalCredentials credentials;

  /**
   * Represents the body of the query.
   */
  protected Object body;

  /**
   * Represents the query parameters of the query.
   */
  protected final HashMap<String, Object> queryParameters;

  /**
   * Represents an HTTP method.
   */
  public static enum HttpMethod {

    /**
     * Represents the 'GET' HTTP method.
     */
    GET,

    /**
     * Represents the 'POST' HTTP method.
     */
    POST,

    /**
     * Represents the 'PUT' HTTP method.
     */
    PUT,

    /**
     * Represents the 'DELETE' HTTP method.
     */
    DELETE
  }

  /**
   * Represents a content type.
   * Supports JSON and Tidal JSON content types.
   */
  public static enum ContentType {

    /**
     * Represents the JSON content type.
     */
    JSON("application/json"),

    /**
     * Represents the Tidal JSON content type.
     */
    TIDAL_JSON("application/vnd.tidal.v1+json");

    /**
     * Represents the value of the content type.
     */
    public final String value;

    /**
     * Constructs a new 'ContentType' object with the given value.
     * 
     * @param value the value of the content type.
     */
    private ContentType(String value) {
      this.value = value;
    }
  }

  /**
   * Constructs a new 'BaseQuery' object with the default HTTP method set to
   * 'GET'.
   */
  public BaseQuery() {
    this.httpMethod = BaseQuery.HttpMethod.GET;
    this.queryParameters = new HashMap<>();
  }

  /**
   * Tries to get the credentials from the store. If the credentials don't exist
   * or are invalid/expired, throws an exception.
   * 
   * This is a wrapper around AuthorizationController.tryGetCredentials() that
   * throws a QueryException instead of an InvalidCredentialsException or an
   * UnauthorizedException.
   * 
   * @return credentials.
   * @throws QueryException if the credentials don't exist or are invalid/expired.
   */
  public static TidalCredentials tryGetCredentialsOrQueryException() throws QueryException {
    try {
      return AuthorizationController.tryGetCredentials();
    } catch (InvalidCredentialsException | UnauthorizedException exception) {
      throw new QueryException(exception);
    }
  }

  /**
   * Converts the response from the Tidal API to a string.
   * 
   * Utilizes the input stream reader and buffer reader from the parent class.
   * 
   * @param connection HTTP connection.
   * 
   * @return response as a string.
   * 
   * @throws IOException if there is an error reading the response.
   */
  public static String responseToString(
      HttpURLConnection connection) throws IOException {
    final InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
    final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

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

  /**
   * Sets the HTTP method for the query.
   *
   * @param httpMethod the HTTP method to be set.
   * 
   * @return the updated BaseQuery object.
   * 
   * @throws QueryException if the query is already built.
   */
  protected BaseQuery method(BaseQuery.HttpMethod httpMethod) throws QueryException {
    if (this.connection != null) {
      throw new QueryException("Query is already built.");
    }

    this.httpMethod = httpMethod;
    return this;
  }

  /**
   * Sets the content type for the query.
   *
   * @param contentType the content type to be set.
   * 
   * @return the updated BaseQuery object.
   * 
   * @throws QueryException if there is an error setting the content type.
   */
  protected BaseQuery contentType(BaseQuery.ContentType contentType) throws QueryException {
    this.contentType = contentType;
    return this;
  }

  /**
   * Sets the authentication credentials for the query.
   *
   * @param credentials the authentication credentials to be set.
   * 
   * @return the updated BaseQuery object.
   * 
   * @throws QueryException if the query is already built or if the credentials
   *                        are invalid.
   */
  protected BaseQuery auth(TidalCredentials credentials) throws QueryException {
    if (this.connection != null) {
      throw new QueryException("Query is already built.");
    }

    if (credentials == null) {
      throw new QueryException(new UnauthorizedException());
    }

    final String accessToken = credentials.getAccessToken();
    final long expiresInSeconds = credentials.getExpiresInSeconds();

    if (accessToken == null || accessToken.isEmpty() || expiresInSeconds == 0) {
      throw new QueryException(new UnauthorizedException());
    }

    this.credentials = credentials;
    return this;
  }

  /**
   * Sets the basic authentication credentials for the query.
   * Used in authorization controller.
   *
   * @param basicCredentialsBase64 the base64-encoded basic authentication
   *                               credentials.
   * 
   * @return the updated BaseQuery object.
   * 
   * @throws QueryException if the query is already built.
   */
  protected BaseQuery auth(String basicCredentialsBase64) throws QueryException {
    if (this.connection != null) {
      throw new QueryException("Query is already built.");
    }

    this.basicCredentialsBase64 = basicCredentialsBase64;
    return this;
  }

  /**
   * Sets the body of the query.
   *
   * @param data the data to be set as the body of the query.
   * 
   * @return the updated BaseQuery object.
   * 
   * @throws QueryException if the query is already built.
   */
  protected BaseQuery body(Object data) throws QueryException {
    if (this.connection != null) {
      throw new QueryException("Query is already built.");
    }

    this.body = data;
    return this;
  }

  /**
   * Sets a query parameter for the query.
   * 
   * @param key   the key of the query parameter.
   * @param value the value of the query parameter.
   * 
   * @return the updated BaseQuery object.
   * 
   * @throws QueryException if the query is already built.
   */
  protected BaseQuery parameter(String key, Object value) throws QueryException {
    if (this.connection != null) {
      throw new QueryException("Query is already built.");
    }

    this.queryParameters.put(key, value);
    return this;
  }

  /**
   * Builds and returns an HttpURLConnection object based on the provided URL and
   * fields of this instance.
   *
   * @param url the URL to build the connection with.
   * 
   * @return the built HttpURLConnection object.
   * 
   * @throws QueryException if the query is already built or if there is an error
   *                        during the build process.
   */
  protected HttpURLConnection build(String url) throws QueryException {
    if (this.connection != null) {
      throw new QueryException("Query is already built.");
    }

    OutputStream outputStream = null;
    OutputStreamWriter outputStreamWriter = null;

    try {
      final StringBuilder urlBuilder = new StringBuilder(url);
      this.queryParameters.forEach((key, value) -> BaseQuery.addQueryParameter(urlBuilder, key, value));
      final String finalUrl = urlBuilder.toString();

      this.connection = (HttpURLConnection) new URI(finalUrl).toURL().openConnection();

      if (this.contentType != null) {
        this.connection.setRequestProperty("Content-Type", this.contentType.value);
      }

      this.connection.setRequestProperty("Accept", "*");

      this.connection.setRequestMethod(this.httpMethod.toString());

      if (this.credentials != null) {
        this.connection.setRequestProperty("Authorization", "Bearer " + credentials.getAccessToken());
      } else if (this.basicCredentialsBase64 != null) {
        this.connection.setRequestProperty("Authorization", "Basic " + this.basicCredentialsBase64);
      }

      if (this.body != null) {

        if (this.httpMethod == BaseQuery.HttpMethod.GET) {
          throw new QueryException("GET requests cannot have a body.");
        }

        this.connection.setDoOutput(true);

        outputStream = connection.getOutputStream();
        outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");

        String bodyAsString;

        if (this.body instanceof String) {
          bodyAsString = (String) this.body;
        } else {
          bodyAsString = JSON.std.asString(this.body);
        }

        outputStreamWriter.write(bodyAsString);
        outputStreamWriter.close();
        outputStream.close();
      }

      return this.connection;
    } catch (URISyntaxException | IOException | QueryException exception) {
      try {
        if (this.connection != null) {
          this.connection.disconnect();
        }

        if (outputStreamWriter != null) {
          outputStreamWriter.close();
        }

        if (outputStream != null) {
          outputStream.close();
        }
      } catch (IOException cleanupException) {
        // no-op
      }

      if (exception instanceof QueryException) {
        throw new QueryException((QueryException) exception);
      }

      throw new QueryException(exception);
    }
  }

  /**
   * Returns the query parameter separator for the given string builder.
   *
   * @param stringBuilder the string builder to get the query parameter separator
   *                      from.
   * @return the query parameter separator.
   */
  private static String getQueryParamSeparator(StringBuilder stringBuilder) {
    return BaseQuery.getQueryParamSeparator(stringBuilder.toString());
  }

  /**
   * Returns the query parameter separator for the given URL.
   *
   * @param url the URL to check for query parameters.
   * @return the query parameter separator ("&" if the URL already contains a
   *         query parameter, "?" otherwise).
   */
  private static String getQueryParamSeparator(String url) {
    if (url.contains("?")) {
      return "&";
    } else {
      return "?";
    }
  }

  /**
   * Adds a query parameter to the given URL.
   *
   * @param urlBuilder The StringBuilder representing the URL.
   * @param key        The key of the query parameter.
   * @param value      The value of the query parameter.
   */
  private static void addQueryParameter(StringBuilder urlBuilder, String key, Object value) {
    String separator = BaseQuery.getQueryParamSeparator(urlBuilder);
    urlBuilder.append(separator).append(key).append("=").append(value.toString());
  }
}
