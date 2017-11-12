package com.forgetfulman.alexa.connect;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

	public String get(String url) {
		return run(url, HttpMethod.GET, null);
	}

	public String post(String url, String requestData, Map<String,String> requestProperties) {
		return run(url, HttpMethod.POST, requestData, requestProperties);
	}

	public String request(String url, HttpMethod method) {
		return run(url, method, null);
	}

	public String request(String url, HttpMethod method, Map<String,String> requestProperties) {
		return run(url, method, null, requestProperties);
	}

	public String request(String url, HttpMethod method, String requestData, Map<String,String> requestParameters) {
		return run(url, method, requestData, requestParameters);
	}

	public String request(String url, HttpMethod method, String requestData) {
		return run(url, method, requestData);
	}

	private String run(String url, HttpMethod method, String data) {
		return run( url, method, data, new HashMap<>());
	}

	private String run(String url, HttpMethod method, String data, Map<String, String> requestProperties) {
		try {
			final URLConnection connection = getConnection(url, method.toString(), requestProperties);
			connection.setDoOutput(true);

			if (data != null) {
				writeRequest(data, connection.getOutputStream());
			}

			final String response = readResponseString(getInputStream(connection));
			return response;
		} catch (final Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private InputStream getInputStream(URLConnection connection) throws IOException {
		if (connection instanceof HttpURLConnection) {
			final HttpURLConnection castConnection = (HttpURLConnection) connection;
			castConnection.connect();
			if (castConnection.getResponseCode() < 400)
				return castConnection.getInputStream();
			return castConnection.getErrorStream();
		} else if (connection instanceof HttpsURLConnection) {
			final HttpsURLConnection castConnection = (HttpsURLConnection) connection;
			if (castConnection.getResponseCode() < 400)
				return castConnection.getInputStream();
			return castConnection.getErrorStream();
		}
		return null;

	}

	private String readResponseString(final InputStream is) {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(is));) {
			final StringBuffer responseBuffer = new StringBuffer();
			String line = null;
			while ((line = in.readLine()) != null) {
				responseBuffer.append(line);
			}
			return responseBuffer.toString();
		} catch (final IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private void writeRequest(String message, final OutputStream os) {
		try (OutputStreamWriter writer = new OutputStreamWriter(os);) {
			writer.write(message);
		} catch (final Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public URLConnection getConnection(String urlString, String method, Map<String, String> requestProperties) throws Exception {
		final URL url = new URL(urlString);
		Proxy proxy = Proxy.NO_PROXY;
		/*if (!urlString.contains("localhost")) {
			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("19.12.1.40", 83));
		}*/
		if (url.getProtocol().toLowerCase().equals("https")) {
			final HttpsURLConnection httpsConnection = (HttpsURLConnection) url.openConnection(proxy);
			requestProperties.forEach(httpsConnection::addRequestProperty);
			httpsConnection.setRequestMethod(method);
			return httpsConnection;
		}
		final HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection(proxy);
		requestProperties.forEach(httpConnection::addRequestProperty);
		httpConnection.setRequestMethod(method);
		return httpConnection;
	}

}
