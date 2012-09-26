package com.rokkincat.integation;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import fi.foyt.foursquare.api.FoursquareApi;

public class MainActivity extends Activity {

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void onFoursquareConnectButtonClicked(View view) {
    	
    	Intent foursquareConnectBrowserIntent = new Intent(Intent.ACTION_VIEW);
    	try{
    		foursquareConnectBrowserIntent.setData(Uri.parse("fsq+"+this.getString(R.string.foursquare_client_id)+"+authorize://?fsqCallback="+this.getString(R.string.foursquare_authentication_callback_url)));
    		startActivity(foursquareConnectBrowserIntent);
    	} catch(ActivityNotFoundException e) {
    		FoursquareApi fqapi = new FoursquareApi(getString(R.string.foursquare_client_id), getString(R.string.foursquare_client_secret), getString(R.string.foursquare_authentication_callback_url));
    		foursquareConnectBrowserIntent.setData(Uri.parse(fqapi.getAuthenticationUrl()));
        	startActivity(foursquareConnectBrowserIntent);
    	}
    }
    
    public void onLinkedInConnectButtonClicked(View view) {
    	
    	OAuthService service = new ServiceBuilder()
    		.provider(LinkedInApi.class)
    		.apiKey(getString(R.string.linkedin_api_key))
    		.apiSecret(getString(R.string.linkedin_api_secret))
    		.callback(getString(R.string.linkedin_authentication_callback_url))
    		.scope("rw_nus")
    		.build();
    	
    	
    	Token requestToken = service.getRequestToken();
    	SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
    	editor.putString("linkedin-request-token-secret", requestToken.getSecret());
    	editor.putString("linkedin-request-token-token", requestToken.getToken());
    	editor.commit();
    	
    	Intent linkedinConnectIntent = new Intent(Intent.ACTION_VIEW);
    	linkedinConnectIntent.setData(Uri.parse(service.getAuthorizationUrl(requestToken)));
    	startActivity(linkedinConnectIntent);
    }
    
    public void onTwitterConnectButtonClicked(View view) {
    	OAuthService service = new ServiceBuilder()
    		.provider(TwitterApi.class)
    		.apiKey(getString(R.string.twitter_api_key))
    		.apiSecret(getString(R.string.twitter_api_secret))
    		.callback(getString(R.string.twitter_authentication_callback_url))
    		.build();
    	
		Token requestToken = service.getRequestToken();
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();
    	editor.putString("twitter-request-token-secret", requestToken.getSecret());
    	editor.putString("twitter-request-token-token", requestToken.getToken());
    	editor.commit();
    	
    	Intent twitterConnectIntent = new Intent(Intent.ACTION_VIEW);
    	twitterConnectIntent.setData(Uri.parse(service.getAuthorizationUrl(requestToken)));
    	startActivity(twitterConnectIntent);
    }
    
}
