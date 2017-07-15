package org.evanframework.httpclient;

import org.apache.http.HttpResponse;

public interface HttpClientSuccessHanlder<T> {
	T hanlder(int statusCode, String responseBody, HttpResponse httpResponse);
}
