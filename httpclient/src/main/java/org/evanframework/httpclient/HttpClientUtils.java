package org.evanframework.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

/**
 * HttpClient 工具类
 * 
 * <p>
 * create at 2015年1月8日 上午10:14:31
 * 
 * @author shen.wei
 * @version %I%, %G%
 *
 */
public class HttpClientUtils {
	private final static Charset DEFAILT_CHARSET = Charset.forName("UTF-8");

	public static List<NameValuePair> createNameValuePairList(Object arg0) {
		final List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (Map.class.isInstance(arg0)) {
			putNameValuePairListWithMap(nvps, (Map<String, ?>) arg0, "");
		} else {
			putNameValuePairListWithObject(nvps, arg0, "");
		}
		return nvps;
	}

	private static void putNameValuePairListWithObject(final List<NameValuePair> nvps, Object arg0, final String prefix) {
//		BeanUtils.eachProperties(arg0, new BeanUtils.EachPropertiesHandler() {
//			@Override
//			public void handler(String property, Object value) {
//				putNameValuePairProperty(nvps, property, value, prefix);
//			}
//		});
		throw new UnsupportedOperationException("This method is not supported for the time being");
	}

	private static void putNameValuePairListWithMap(List<NameValuePair> nvps, Map<String, ?> map, String prefix) {
		for (Map.Entry<String, ?> entry : map.entrySet()) {
			Object value = entry.getValue();
			String property = entry.getKey();
			putNameValuePairProperty(nvps, property, value, prefix);
		}
	}

	private static void putNameValuePairProperty(List<NameValuePair> nvps, String property, Object value, String prefix) {
		if (value == null) {
			return;
		}
		if (value instanceof String || value instanceof Integer || value instanceof Long || value instanceof Double
				|| value instanceof BigDecimal || value instanceof Float || value instanceof Boolean
				|| value instanceof Byte || value instanceof Short || value instanceof Character) {
			nvps.add(new BasicNameValuePair(prefix + property, value + ""));
		} else if (Map.class.isInstance(value)) {
			putNameValuePairListWithMap(nvps, (Map<String, ?>) value, prefix + property + ".");
		} else if (!(value instanceof Enum)) {
			putNameValuePairListWithObject(nvps, value, property + ".");
		}
	}

	public static UrlEncodedFormEntity createUrlEncodedFormEntity(Object arg0, String charset) {
		List<NameValuePair> nvps = createNameValuePairList(arg0);
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(nvps, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Charset [" + charset + "] is not support", e);
		}
		//entity.setContentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
		return entity;
	}

	public static UrlEncodedFormEntity createUrlEncodedFormEntity(Object arg0) {
		return createUrlEncodedFormEntity(arg0, DEFAILT_CHARSET.name());
	}

	public static StringEntity createJsonEntity(Object arg0, String charset) {
		StringEntity entity = new StringEntity(JSON.toJSONString(arg0), Charset.forName(charset));
		//entity.setContentType(MediaType.APPLICATION_JSON_VALUE);
		return entity;
	}

	public static StringEntity createJsonEntity(Object arg0) {
		return createJsonEntity(arg0, DEFAILT_CHARSET.name());
	}

	// public static CloseableHttpResponse execute(CloseableHttpClient
	// httpClient, HttpUriRequest request) {
	// CloseableHttpResponse httpResponse = null;
	// try {
	// httpResponse = httpClient.execute(request);
	// } catch (IOException e) {
	// throw new RuntimeException(e);
	// } finally {
	// request.abort();
	// try {
	// httpClient.close();
	// } catch (IOException e) {
	// throw new RuntimeException(e);
	// }
	// }
	// return httpResponse;
	// }

	public static <T> T execute(HttpClient httpClient, HttpUriRequest request,
			final HttpClientSuccessHanlder<T> successHanlder, HttpClientFailHanlder failHanlder) {
		T t = null;

		try {
			t = httpClient.execute(request, new ResponseHandler<T>() {
				@Override
				public T handleResponse(HttpResponse response) throws IOException {
					int statusCode = response.getStatusLine().getStatusCode();
					HttpEntity entity = response.getEntity();
					String responseBody = null;
					if (entity != null) {
						responseBody = EntityUtils.toString(entity, DEFAILT_CHARSET);
						EntityUtils.consume(entity);
					}
					T t = successHanlder.hanlder(statusCode, responseBody, response);
					return t;
				}
			});
		} catch (IOException ex) {
			if (failHanlder != null) {
				failHanlder.hanlder(ex);
			} else {
				throw new HttpClientException(request, ex);
			}
		} finally {
			request.abort();
			// try {
			// httpClient.close();
			// } catch (IOException e) {
			// throw new RuntimeException(e);
			// }
		}

		return t;
	}

	public static <T> T execute(HttpClient httpClient, HttpUriRequest request,
			final HttpClientSuccessHanlder<T> successHanlder) {
		return execute(httpClient, request, successHanlder, null);
	}

	/**
	 * 
	 * <p>
	 * <a href="mailto:shenw@hundsun.com">Evan.Shen</a> create at 2013-12-6
	 * 上午1:32:11
	 * </p>
	 * 
	 * @param httpEntity
	 *
	 */
	// public static String getResponseBody(HttpEntity httpEntity) {
	// StringBuilder str = new StringBuilder(1024);
	// if (httpEntity == null) {
	// return str.toString();
	// }
	// try {
	// BufferedReader in = new BufferedReader(new
	// InputStreamReader(httpEntity.getContent()));
	// String line = null;
	// while ((line = in.readLine()) != null) {
	// str.append(line);
	// }
	// } catch (IOException e) {
	// throw new RuntimeException(e);
	// }
	// return str.toString();
	// }
	//
	// public static void closeHttpResponse(CloseableHttpResponse httpResponse)
	// {
	// try {
	// httpResponse.close();
	// } catch (IOException e) {
	// throw new RuntimeException(e);
	// }
	// }
}
