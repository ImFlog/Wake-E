package com.wake_e;

import com.wake_e.constants.WakeEConstants;
import com.wake_e.model.Credentials;
import com.wake_e.tools.TokenRequester;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

public class SplashScreenActivity extends FragmentActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    private static SplashScreenActivity that;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_splash);
	that = this;
	new Handler().postDelayed(new Runnable() {

	    /*
	     * Showing splash screen with a timer. This will be useful when you
	     * want to show case your app logo / company
	     */

	    @Override
	    public void run() {
		// This method will be executed once the timer is over
		// Start your app main activity
		Credentials c = Controller.getInstance(getApplicationContext()).getCredentials(
			WakeEConstants.Credentials.GMAIL);

		if(c != null){
		    TokenRequester
		    .initiateRequest(
			    SplashScreenActivity.that,c.getUser());
		}
		Intent i = new Intent(SplashScreenActivity.this,
			MainActivity.class);
		startActivity(i);

		// close this activity
		finish();
	    }
	}, SPLASH_TIME_OUT);
    }
}