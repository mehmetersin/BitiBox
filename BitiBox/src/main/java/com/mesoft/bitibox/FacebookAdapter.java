package com.mesoft.bitibox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.types.Page;

public class FacebookAdapter {

	final static Logger logger = LoggerFactory.getLogger(FacebookAdapter.class);

	public static String getValue() {

		try {

			Util util = new Util();

			FacebookClient facebookClient = new DefaultFacebookClient();

			String appId = "407593439318747";
			String appSecret = "911e28a01e06a505734d2f486656c918";

			AccessToken accessToken = facebookClient.obtainAppAccessToken(
					appId, appSecret);
			String accessTokenString = accessToken.getAccessToken();

			FacebookClient facebookClient2 = new DefaultFacebookClient(
					accessTokenString, appSecret);

			String pageName = util.getConfig("pageName");

			Page page = facebookClient2.fetchObject(pageName, Page.class);

			Long count = page.getLikes();

			logger.debug("Page " + pageName + " like count " + count);

			return String.valueOf(page.getLikes());

		} catch (Exception e) {
			logger.error("Error in facebookadapter ", e);
			return "1";
		}

	}
}
