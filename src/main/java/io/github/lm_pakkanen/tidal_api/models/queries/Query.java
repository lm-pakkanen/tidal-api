package io.github.lm_pakkanen.tidal_api.models.queries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.jr.ob.JSON;
import com.fasterxml.jackson.jr.ob.impl.DeferredMap;

import io.github.lm_pakkanen.tidal_api.models.entities.Credentials;
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
   * @throws QueryException
   */
  public Query(String url) {
    super();
    this.url = url;
  }

  /**
   * @see BaseQuery#method(BaseQuery.HttpMethod)
   */
  public Query method(BaseQuery.HttpMethod httpMethod) throws QueryException {
    super.method(httpMethod);
    return this;
  }

  /**
   * @see BaseQuery#contentType(BaseQuery.ContentType)
   */
  public Query contentType(BaseQuery.ContentType contentType) throws QueryException {
    super.contentType(contentType);
    return this;
  }

  /**
   * @see BaseQuery#auth(Credentials)
   */
  public Query auth(Credentials credentials) throws QueryException {
    super.auth(credentials);
    return this;
  }

  /**
   * @see BaseQuery#auth(String)
   */
  public Query auth(String basicCredentialsBase64) throws QueryException {
    super.auth(basicCredentialsBase64);
    return this;
  }

  /**
   * @see BaseQuery#body(Object)
   */
  public Query body(Object data) throws QueryException {
    super.body(data);
    return this;
  }

  /**
   * @see BaseQuery#parameter(String, String)
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

    InputStreamReader inputStreamReader = null;
    BufferedReader bufferedReader = null;

    try {
      int statusCode = connection.getResponseCode();

      if (statusCode != 200) {
        throw new QueryException("Request failed with status code " + statusCode);
      }

      final String response = BaseQuery.responseToString(inputStreamReader, bufferedReader, connection);
      final T entity = JSON.std.beanFrom(toBean, response);

      return entity;
    } catch (IOException | QueryException exception) {
      if (exception instanceof QueryException) {
        throw new QueryException((QueryException) exception);
      }

      throw new QueryException(exception);
    } finally {
      try {
        if (inputStreamReader != null) {
          inputStreamReader.close();
        }

        if (bufferedReader != null) {
          bufferedReader.close();
        }
      } catch (IOException exception) {
        // no-op
      }
    }
  }
}
