package io.github.lm_pakkanen.tidal_api.models.queries;

import java.io.IOException;
import java.net.HttpURLConnection;
import com.fasterxml.jackson.jr.ob.JSON;
import io.github.lm_pakkanen.tidal_api.models.entities.TidalCredentials;
import io.github.lm_pakkanen.tidal_api.models.exceptions.QueryException;

/**
 * Represents a query to be executed against a Tidal API endpoint.
 * This class extends the {@link BaseQuery} class and provides wrapped
 * methods for building and executing queries.
 */
public class Query extends BaseQuery {
  private final String url;

  /**
   * Constructs a new query with the given URL.
   * 
   * @param url the URL to be called.
   */
  public Query(String url) {
    super();
    this.url = url;
  }

  /**
   * Sets the HTTP method of the query.
   * 
   * @see BaseQuery#method(BaseQuery.HttpMethod)
   * 
   * @param httpMethod the HTTP method of the query.
   * 
   * @return this instance.
   * 
   * @throws QueryException if the HTTP method cannot be set.
   */
  public Query method(BaseQuery.HttpMethod httpMethod) throws QueryException {
    super.method(httpMethod);
    return this;
  }

  /**
   * Set the content type of the query.
   * 
   * @see BaseQuery#contentType(BaseQuery.ContentType)
   * 
   * @param contentType the content type of the query.
   * 
   * @return this instance.
   * 
   * @throws QueryException if the content type cannot be set.
   */
  public Query contentType(BaseQuery.ContentType contentType) throws QueryException {
    super.contentType(contentType);
    return this;
  }

  /**
   * Authenticates the query with the given credentials.
   * 
   * @see BaseQuery#auth(TidalCredentials)
   * 
   * @param credentials the credentials to authenticate the query with.
   * 
   * @return this instance.
   * 
   * @throws QueryException if the authentication fails.
   */
  public Query auth(TidalCredentials credentials) throws QueryException {
    super.auth(credentials);
    return this;
  }

  /**
   * Authenticates the query with the given basic credentials.
   * 
   * @see BaseQuery#auth(String)
   * 
   * @param basicCredentialsBase64 the basic credentials in base64 format.
   * 
   * @return this instance.
   * 
   * @throws QueryException if the authentication fails.
   */
  public Query auth(String basicCredentialsBase64) throws QueryException {
    super.auth(basicCredentialsBase64);
    return this;
  }

  /**
   * Adds a body to the query.
   * 
   * @see BaseQuery#body(Object)
   * 
   * @param data the body of the query.
   * 
   * @return this instance.
   * 
   * @throws QueryException if the body cannot be added.
   */
  public Query body(Object data) throws QueryException {
    super.body(data);
    return this;
  }

  /**
   * Adds a parameter to the query.
   * 
   * @see BaseQuery#parameter(String, String)
   * 
   * @param key   the key of the parameter.
   * @param value the value of the parameter.
   * 
   * @return this instance.
   * 
   * @throws QueryException if the parameter cannot be added.
   */
  public Query parameter(String key, String value) throws QueryException {
    super.parameter(key, value);
    return this;
  }

  /**
   * Executes this instance query and returns the HTTP status code.
   * 
   * @return the HTTP status code.
   * @throws QueryException if the query fails.
   */
  public int execute() throws QueryException {
    final HttpURLConnection connection = super.build(url);

    try {
      final int statusCode = connection.getResponseCode();
      return statusCode;
    } catch (IOException exception) {
      throw new QueryException(exception);
    }
  }

  /**
   * Executes this instance query and returns the response as an instance of a
   * provided model class.
   * 
   * @param <T>    the model class.
   * @param toBean the model class.
   * 
   * @return the response as an instance of the provided model class.
   * 
   * @throws QueryException if the query fails.
   */
  public <T> T execute(Class<T> toBean) throws QueryException {
    final HttpURLConnection connection = super.build(url);

    try {
      int statusCode = connection.getResponseCode();

      if (statusCode != 200) {
        throw new QueryException("Request failed with status code " + statusCode);
      }

      final String response = BaseQuery.responseToString(connection);
      final T entity = JSON.std.beanFrom(toBean, response);

      return entity;
    } catch (IOException | QueryException exception) {
      if (exception instanceof QueryException) {
        throw new QueryException((QueryException) exception);
      }

      throw new QueryException(exception);
    }
  }
}
