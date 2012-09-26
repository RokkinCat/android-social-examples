package com.rokkincat.integation;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.OAuthConfig;

public class LinkedIn2Api extends DefaultApi20 {

	
	
	@Override
	public String getAccessTokenEndpoint() {
		return "https://api.linkedin.com/uas/oauth/accessToken";
	}

	@Override
	public String getAuthorizationUrl(OAuthConfig arg0) {
		return ""; //String.format(AUTHORIZE_URL, requestToken.getToken())
	}

}
