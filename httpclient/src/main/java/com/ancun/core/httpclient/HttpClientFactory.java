package com.ancun.core.httpclient;

import org.apache.http.client.HttpClient;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class HttpClientFactory implements FactoryBean<HttpClient>, InitializingBean {
	private HttpClient httpClient;

	private int maxTotalConnections = 128;
	private int maxConnectionsPerRoute = 8;
	private int readTimeoutSeconds = 60;

	// private final static String CHARSET = "UTF-8";

	public void setMaxTotalConnections(int maxTotalConnections) {
		this.maxTotalConnections = maxTotalConnections;
	}

	public void setMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
		this.maxConnectionsPerRoute = maxConnectionsPerRoute;
	}

	public void setReadTimeoutSeconds(int readTimeoutSeconds) {
		this.readTimeoutSeconds = readTimeoutSeconds;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		createHttpClient();

	}

	protected void createHttpClient() {
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		SocketConfig.Builder socketConfigBuilder = SocketConfig.custom();
		socketConfigBuilder.setSoTimeout(readTimeoutSeconds * 1000);

		SocketConfig socketConfig = socketConfigBuilder.build();
		connManager.setDefaultSocketConfig(socketConfig);
		connManager.setMaxTotal(maxTotalConnections);
		connManager.setDefaultMaxPerRoute(maxConnectionsPerRoute);

		httpClient = HttpClients.custom().setConnectionManager(connManager).build();

		// httpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
		// CHARSET);

	}

	@Override
	public HttpClient getObject() throws Exception {
		if (this.httpClient == null) {
			createHttpClient();
		}

		return this.httpClient;
	}

	@Override
	public Class<?> getObjectType() {
		return this.httpClient == null ? HttpClient.class : this.httpClient.getClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
