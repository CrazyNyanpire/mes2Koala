package org.seu.acetec.mes2Koala.infra.httpclient;

import org.apache.commons.httpclient.HttpClient;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;



public class HttpClientUtil {
	private static final Logger LOG = Logger.getLogger(HttpClientUtil.class
			.getName());
	HttpClient client = new HttpClient();

	public String getPost(String server, NameValuePair[] nameValuePair) {
		Map<String, String> result = new HashMap<String, String>();
		PostMethod post = new PostMethod(server);
		post.setRequestBody(nameValuePair);
		System.setProperty("jsse.enableSNIExtension", "false");
		try {
			client.executeMethod(post);
			String response = post.getResponseBodyAsString();
			result.put("response", response);
			return response;
		} catch (final IOException e) {
			LOG.warning(e.getMessage());
		}

		finally {
			post.releaseConnection();
		}

		return null;
	}

	public byte[] getPostByte(String server, NameValuePair[] nameValuePair) {
		// Map<String, String> result = new HashMap<String, String>();
		PostMethod post = new PostMethod(server);
		post.setRequestBody(nameValuePair);
		try {
			client.executeMethod(post);
			return post.getResponseBody();
		} catch (final IOException e) {
			LOG.warning(e.getMessage());
		} finally {
			post.releaseConnection();
		}
		return null;
	}
}
