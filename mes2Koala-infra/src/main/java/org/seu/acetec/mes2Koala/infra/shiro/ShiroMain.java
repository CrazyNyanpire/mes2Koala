package org.seu.acetec.mes2Koala.infra.shiro;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * An example Java client to authenticate against CAS using REST services.
 * Please ensure you have followed the necessary setup found on the <a
 * href="http://www.ja-sig.org/wiki/display/CASUM/RESTful+API">wiki</a>.
 *
 * @author <a href="mailto:jieryn@gmail.com">jesse lauren farinacci</a>
 * @since 3.4.2
 */
public final class ShiroMain {
	private static final Logger LOG = Logger.getLogger(ShiroMain.class
			.getName());
	private static final int TIME_OUT_MSECONDS = 1000;	//连接超时毫秒数
	HttpClient client = new HttpClient();
	Cookie[] cookies = client.getState().getCookies();


	private static ShiroMain instance = new ShiroMain();
	private ShiroMain() {
		// static-only access
	}
	public static ShiroMain getInstance(){
		return instance;
	}

	public String getTicket(String server, String username, String password,
			String service) throws UnsupportedEncodingException {
		notNull(server, "server must not be null");
		notNull(username, "username must not be null");
		notNull(password, "password must not be null");
		notNull(service, "service must not be null");
		NameValuePair un = new NameValuePair("username", username);
		NameValuePair pwd = new NameValuePair("password", password);
		LOG.info("***************************");
		// 获取service cookie
		getTicketGrantingTicketCookie(server, new NameValuePair[] { un, pwd });
		// 获取数据
		return getService(service);
	}

	public Map<String, String> getTicketGrantingTicketCookie(String server,
			NameValuePair[] nameValuePair) {
		Map<String, String> result = new HashMap<String, String>();

		PostMethod post = new PostMethod(server);

		post.setRequestBody(nameValuePair);
		System.setProperty("jsse.enableSNIExtension", "false");
		try {
			post.setRequestHeader("Cookie", cookies.toString());
			client.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT_MSECONDS);
			client.getState().addCookies(cookies);
			client.executeMethod(post);
			final String response = post.getResponseBodyAsString();
			LOG.info("Location:" + post.getResponseHeader("Location"));
			LOG.info("getStatusCode:" + post.getStatusCode());

			switch (post.getStatusCode()) {
			case 201: {
				final Matcher matcher = Pattern.compile(
						".*action=\".*/(.*?)\".*").matcher(response);

				if (matcher.matches()) {
					result.put("ticket", matcher.group(1));
					LOG.info("ticket:" + result.get("ticket"));
					return result;
				}
				LOG.warning("Successful ticket granting request, but no ticket found!");
				LOG.info("Response (1k):"
						+ response.substring(0,
								Math.min(1024, response.length())));
				break;
			}
			case 302: {
				String location = post.getResponseHeader("Location").toString();
				if (location != null && !"".equals(location)) {
					result.put("ticket", location.split("\\?")[1]);
					LOG.info("ticket:" + result.get("ticket"));
					return result;
				}
				LOG.warning("Successful ticket granting request, but no ticket found!");
				LOG.info("Response (1k):"
						+ response.substring(0,
								Math.min(1024, response.length())));
				break;
			}
			case 200: {
				LOG.info("Response (1k):"
						+ response.substring(0,
								Math.min(20480, response.length())));
				return result;

			}

			default:
				LOG.warning("Invalid response code (" + post.getStatusCode()
						+ ") from CAS server!");

				LOG.info("Response (1k):"
						+ response.substring(0,
								Math.min(20480, response.length())));

				break;
			}
		}

		catch (final IOException e) {
			LOG.warning(e.getMessage());
		}

		finally {
			post.releaseConnection();
		}

		return null;
	}

	private String getService(String service) {
		GetMethod get = new GetMethod(service);
		System.setProperty("jsse.enableSNIExtension", "false");
		get.setRequestHeader("Cookie", cookies.toString());
		client.getState().addCookies(cookies);
		try {
			client.executeMethod(get);
			String response = get.getResponseBodyAsString();
			switch (get.getStatusCode()) {
			case 200:
				LOG.info("Response (1k):"
						+ response.substring(0,
								Math.min(1024, response.length())));
				return response;
			default:
				LOG.warning("Invalid response code (" + get.getStatusCode()
						+ ") from CAS server!");
				LOG.info("Response (1k):"
						+ response.substring(0,
								Math.min(1024, response.length())));
				break;
			}
		}

		catch (final IOException e) {
			LOG.warning(e.getMessage());
		}

		finally {
			get.releaseConnection();
		}

		return null;
	}

	private void notNull(Object object, String message) {
		if (object == null)
			throw new IllegalArgumentException(message);
	}

	public static void main(final String[] args) {
		final String server = "http://192.168.1.44:8280/login.koala";
		final String username = "koala";
		final String password = "qwerty";
//		final String service = "http://192.168.1.44:8280/ChangeStatus/pageJson.koala?page=0&pagesize=5";
		final String service = "http://192.168.1.44:8280/Equipment/pageJson.koala?page=0&pagesize=10000000&equipmentId=&nowLot=&category=oven";
		ShiroMain sso = new ShiroMain();
		try {
			LOG.info(sso.getTicket(server, username, password, service));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
