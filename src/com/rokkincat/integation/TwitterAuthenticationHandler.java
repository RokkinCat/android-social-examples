package com.rokkincat.integation;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class TwitterAuthenticationHandler extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		OAuthService service = new ServiceBuilder()
		.provider(TwitterApi.class)
		.apiKey(getString(R.string.twitter_api_key))
		.apiSecret(getString(R.string.twitter_api_secret))
		.build();
    	
    	// We need the same request token as the one we created in the main activity, get it out of preferences
    	Token requestToken = new Token(PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("twitter-request-token-token", ""),
    			PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("twitter-request-token-secret",""));
    	
    	Token accessToken = service.getAccessToken(requestToken, new Verifier(getIntent().getData().getQueryParameter("oauth_verifier")));
    	
    	
    	OAuthRequest request = new OAuthRequest(Verb.POST, "https://api.twitter.com/1.1/statuses/update.json");
    	
    	request.addBodyParameter("status", "Testing boothtag android tweets!");
    	service.signRequest(accessToken, request);
    	request.send();
		
	}
	
}
