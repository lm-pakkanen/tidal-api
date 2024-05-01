package io.github.lm_pakkanen.tidal_api.models.queries;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import com.fasterxml.jackson.jr.ob.JSON;

import io.github.lm_pakkanen.tidal_api.interfaces.TidalListResponse;
import io.github.lm_pakkanen.tidal_api.models.ListQueryResult;
import io.github.lm_pakkanen.tidal_api.models.entities.TidalCredentials;
import io.github.lm_pakkanen.tidal_api.models.exceptions.QueryException;

/**
 * Represents a list query to be executed against a Tidal API endpoint.
 * This class extends the {@link BaseQuery} class and provides wrapped
 * methods for building and executing list queries.
 */
public class ListQuery extends BaseQuery {
  private final String url;

  private String offsetParamName;
  private String limitParamName;

  /**
   * Constructs a new list query with the given URL.
   * 
   * @param url the URL to be called.
   */
  public ListQuery(String url) {
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
  public ListQuery method(BaseQuery.HttpMethod httpMethod) throws QueryException {
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
  public ListQuery contentType(BaseQuery.ContentType contentType) throws QueryException {
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
   * @throws QueryException if the query cannot be authenticated.
   */
  public ListQuery auth(TidalCredentials credentials) throws QueryException {
    super.auth(credentials);
    return this;
  }

  /**
   * Authenticates the query with the given basic credentials.
   * 
   * @see BaseQuery#auth(String)
   * 
   * @param basicCredentialsBase64 the basic credentials to authenticate the query
   *                               with.
   * 
   * @return this instance.
   * 
   * @throws QueryException if the query cannot be authenticated.
   */
  public ListQuery auth(String basicCredentialsBase64) throws QueryException {
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
  public ListQuery body(Object data) throws QueryException {
    super.body(data);
    return this;
  }

  /**
   * Adds a parameter to the query.
   * 
   * @see BaseQuery#parameter(String, Object)
   * 
   * @param key   the name of the parameter.
   * @param value the value of the parameter.
   * 
   * @return this instance.
   * 
   * @throws QueryException if the parameter cannot be added.
   */
  public ListQuery parameter(String key, Object value) throws QueryException {
    super.parameter(key, value);
    return this;
  }

  /**
   * Adds a limit parameter to the query.
   * 
   * This is a convenience method for adding a limit parameter to the query
   * without specifying the parameter name.
   * 
   * @param limit the number of items to limit to.
   * 
   * @return this instance.
   * 
   * @throws QueryException if the parameter cannot be added.
   */
  public ListQuery limit(int limit) throws QueryException {
    return this.limit("limit", limit);
  }

  /**
   * Adds a limit parameter to the query.
   * 
   * @param paramName the name of the parameter, usually 'limit'.
   * @param limit     the number of items to limit to.
   * 
   * @return this instance.
   * 
   * @throws QueryException if the parameter cannot be added.
   */
  public ListQuery limit(String paramName, int limit) throws QueryException {
    this.limitParamName = paramName;
    super.parameter(paramName, limit);
    return this;
  }

  /**
   * Adds an offset parameter to the query.
   * 
   * This is a convenience method for adding a offset parameter to the query
   * without specifying the parameter name.
   * 
   * @param offset the number of items to offset by.
   * 
   * @return this instance.
   * 
   * @throws QueryException if the parameter cannot be added.
   */
  public ListQuery offset(int offset) throws QueryException {
    return this.offset("offset", offset);
  }

  /**
   * Adds an offset parameter to the query.
   * 
   * @param paramName the name of the parameter, usually 'offset'.
   * @param offset    the number of items to offset by.
   * 
   * @return this instance.
   * 
   * @throws QueryException if the parameter cannot be added.
   */
  public ListQuery offset(String paramName, int offset) throws QueryException {
    this.offsetParamName = paramName;
    super.parameter(paramName, offset);
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
   * @param <T>            the model class.
   * @param <TList>        the list model class.
   * @param toBean         the model class.
   * @param toListBean     the list model class.
   * @param pathInResponse the path in the response to the list of items.
   * 
   * @return the response as an instance of ListQueryResult. The query result
   *         status is set to FAILURE if the query fails or throws an exception.
   * 
   * @throws QueryException if the query is executed with a null pathInResponse.
   */
  public <T, TList extends TidalListResponse<?>> ListQueryResult<T> execute(Class<T> toBean, Class<TList> toListBean,
      String pathInResponse)
      throws QueryException {

    if (pathInResponse == null) {
      throw new QueryException("pathInResponse cannot be null");
    }

    Integer offset = null;
    Integer limit = null;

    if (this.offsetParamName != null) {
      offset = (Integer) super.queryParameters.get(this.offsetParamName);
    }

    if (this.limitParamName != null) {
      limit = (Integer) super.queryParameters.get(this.limitParamName);
    }

    try {
      final HttpURLConnection connection = super.build(url);

      final int statusCode = connection.getResponseCode();

      if (statusCode < 200 || statusCode >= 300) {
        throw new QueryException("Request failed with status code " + statusCode);
      }

      final String message = null;

      final String responseString = BaseQuery.responseToString(connection);
      final TList listResponse = this.tryGetResponse(toListBean, responseString);

      @SuppressWarnings("unchecked")
      final List<T> items = (List<T>) listResponse.getData();

      return new ListQueryResult<T>(ListQueryResult.Status.SUCCESS, message, items, offset, limit);
    } catch (IOException | QueryException exception) {
      QueryException queryException = null;

      if (exception instanceof QueryException) {
        queryException = new QueryException((QueryException) exception);
      } else {
        queryException = new QueryException(exception);
      }

      final String message = queryException.getMessage();
      final TList listResponse = this.tryGetResponse(toListBean, "{}");

      @SuppressWarnings("unchecked")
      final List<T> items = (List<T>) listResponse.getData();

      return new ListQueryResult<>(ListQueryResult.Status.FAILURE, message, items, offset, limit);
    }
  }

  /**
   * Attempts to get a response from the given input.
   * 
   * @param <T>    the model class.
   * @param toBean the model class.
   * @param input  the input string.
   * @return the response as an instance of the model class, or null if the input
   *         is invalid.
   */
  private <T> T tryGetResponse(Class<T> toBean, String input) {
    try {
      return JSON.std.beanFrom(toBean, input);
    } catch (IOException exception) {
      return null;
    }
  }
}
