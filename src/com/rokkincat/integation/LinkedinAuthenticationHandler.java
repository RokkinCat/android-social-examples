package com.rokkincat.integation;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class LinkedinAuthenticationHandler extends Activity { 
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

    	OAuthService service = new ServiceBuilder()
    		.provider(LinkedInApi.class)
    		.apiKey(getString(R.string.linkedin_api_key))
    		.apiSecret(getString(R.string.linkedin_api_secret))
    		.callback(getString(R.string.linkedin_authentication_callback_url))
    		.scope("rw_nus")
    		.build();
    	
    	// We need the same request token as the one we created in the main activity, get it out of preferences
    	Token requestToken = new Token(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("linkedin-request-token-token", ""),
    			PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("linkedin-request-token-secret",""));
    	
    	Token accessToken = service.getAccessToken(requestToken, new Verifier(getIntent().getData().getQueryParameter("oauth_verifier")));
    	
    	OAuthRequest request = new OAuthRequest(Verb.POST, "http://api.linkedin.com/v1/people/~/shares");
    	request.addPayload(
    			"<share>" +
	    			"<comment>Testing out booth tag posting</comment>" +
	    			"<visibility>" +
	    				"<code>anyone</code>" +
	    			"</visibility>" +
    			"</share>");
    	request.addHeader("Content-Length", Integer.toString(request.getBodyContents().length()));
    	request.addHeader("Content-Type","text/xml");
    	service.signRequest(accessToken, request);
    	request.send();
  
		
	}

}
