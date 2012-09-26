package com.rokkincat.integation;

import android.app.Activity;
import android.os.Bundle;
import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompleteUser;

public class FoursquareAuthenticationHandler extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FoursquareApi fqapi = new FoursquareApi(getString(R.string.foursquare_client_id), getString(R.string.foursquare_client_secret), getString(R.string.foursquare_authentication_callback_url));
		try {
			fqapi.authenticateCode(getIntent().getData().getQueryParameter("code"));
			Result<CompleteUser> user = fqapi.user("self");
			
			System.out.println(user.getResult().getFriends().getCount()); // Get number of friends
			
		} catch (FoursquareApiException e) {
			e.printStackTrace();
		}
		
	}
}
