package io.github.lm_pakkanen.tidal_api.models.queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

import com.fasterxml.jackson.jr.ob.JSON;

import io.github.lm_pakkanen.tidal_api.models.entities.Credentials;
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
  protected HttpURLConnection connection;

  private OutputStream outputStream;
  private OutputStreamWriter outputStreamWriter;

  private BaseQuery.HttpMethod httpMethod;
  private BaseQuery.ContentType contentType;

  private String basicCredentialsBase64;
  private Credentials credentials;
  private Object body;

  private String countryCode;
  private Integer limit;
  private Integer skip;

  public static enum HttpMethod {
    GET, POST, PUT, DELETE
  }

  public static enum ContentType {
    JSON("application/json"),
    TIDAL_JSON("application/vnd.tidal.v1+json");

    public final String value;

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
  }

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
  protected BaseQuery auth(Credentials credentials) throws QueryException {
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
   * @return the updated BaseQuery object.
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
   * Sets the item count limit for the query.
   *
   * @param limit the maximum number of results to be returned.
   * 
   * @return the updated BaseQuery object.
   * 
   * @throws QueryException if the query is already built.
   */
  protected BaseQuery limit(int limit) throws QueryException {
    if (this.connection != null) {
      throw new QueryException("Query is already built.");
    }

    this.limit = limit;
    return this;
  }

  /**
   * Sets the number of items to skip in the query result.
   *
   * @param skip the number of items to skip.
   * 
   * @return the updated BaseQuery object.
   * 
   * @throws QueryException if the query is already built.
   */
  protected BaseQuery skip(int skip) throws QueryException {
    if (this.connection != null) {
      throw new QueryException("Query is already built.");
    }

    this.skip = skip;
    return this;
  }

  /**
   * Sets the country code for the query.
   *
   * @param countryCode the country code to set.
   * 
   * @return the updated BaseQuery object.
   * 
   * @throws QueryException if the query is already built.
   */
  protected BaseQuery countryCode(String countryCode) throws QueryException {
    if (this.connection != null) {
      throw new QueryException("Query is already built.");
    }

    this.countryCode = countryCode;
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

    try {
      final StringBuilder urlBuilder = new StringBuilder(url);

      if (this.skip != null) {
        BaseQuery.addQueryParameter(urlBuilder, "offset", this.skip);
      }

      if (this.limit != null) {
        BaseQuery.addQueryParameter(urlBuilder, "limit", this.limit);
      }

      if (this.countryCode != null) {
        BaseQuery.addQueryParameter(urlBuilder, "countryCode", this.countryCode);
      }

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

        this.outputStream = connection.getOutputStream();
        this.outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");

        String bodyAsString;

        if (this.body instanceof String) {
          bodyAsString = (String) this.body;
        } else {
          bodyAsString = JSON.std.asString(this.body);
        }

        this.outputStreamWriter.write(bodyAsString);

        this.outputStreamWriter.close();
        this.outputStream.close();
      }

      return this.connection;
    } catch (URISyntaxException | IOException exception) {
      try {
        if (this.connection != null) {
          this.connection.disconnect();
        }

        if (this.outputStreamWriter != null) {
          this.outputStreamWriter.close();
        }

        if (this.outputStream != null) {
          this.outputStream.close();
        }
      } catch (IOException cleanupException) {
        // no-op
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
