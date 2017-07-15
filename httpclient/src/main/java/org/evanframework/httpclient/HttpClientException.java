package org.evanframework.httpclient;

import org.apache.http.client.methods.HttpUriRequest;

public class HttpClientException extends RuntimeException {

	private static final long serialVersionUID = -1394212883706716807L;

	public HttpClientException(String string, Throwable cause) {
		super(string, cause);
	}

	public HttpClientException(HttpUriRequest request, Throwable cause) {
		
//		catch (ClientProtocolException ex) {
//			if (failHanlder != null) {
//				failHanlder.hanlder(ex);
//			} else {
//				throw new HttpClientException("Call [" + request + "] fail,Client protocol is error", ex);
//			}
		
		super("Call [" + request + "] fail,Can not connect to http service", cause);
	}

}
