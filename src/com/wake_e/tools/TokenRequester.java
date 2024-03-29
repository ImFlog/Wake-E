package com.wake_e.tools;

import java.io.IOException;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.wake_e.Controller;
import com.wake_e.SettingsActivity;
import com.wake_e.constants.WakeEConstants;
import com.wake_e.model.Credentials;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class TokenRequester extends AsyncTask<Void, Void, Void> {
	Activity mActivity;
	String mScope;
	String mEmail;

	public static void initiateRequest(Activity mActivity, String user) {
		// Async token request
		TokenRequester requester = new TokenRequester(
				mActivity, user, WakeEConstants.APICalls.GMAIL_SCOPE);
		requester.execute();
	}

	public TokenRequester(Activity activity, String name, String scope) {
		this.mActivity = activity;
		this.mScope = scope;
		this.mEmail = name;
	}

	/**
	 * Executes the asynchronous job. This runs when you call execute()
	 * on the AsyncTask instance.
	 */
	@Override
	protected Void doInBackground(Void... params) {
		try {
			String token = fetchToken();
			if (token != null) {
				Log.i("Token request", token);
				Credentials c = new Credentials("gmail", mEmail, token);
				if(this.mActivity != null){
				    Controller.getInstance((Context) mActivity).updateCredentials(c);
				} else {
				    Controller.getInstance(Controller.getContext()).updateCredentials(c);
				}
				
			} else {
				Log.i("Token request", "Oups no token");
			}
		} catch (IOException e) {
			// The fetchToken() method handles Google-specific exceptions,
			// so this indicates something went wrong at a higher level.
			Log.e("Token request", e.getMessage());
		}
		return null;
	}

	/**
	 * Gets an authentication token from Google and handles any
	 * GoogleAuthException that may occur.
	 */
	protected String fetchToken() throws IOException {
		try {
			return GoogleAuthUtil.getToken(mActivity, mEmail, mScope);
		} catch (UserRecoverableAuthException userRecoverableException) {
			// GooglePlayServices.apk is either old, disabled, or not present
			// so we need to show the user some UI in the activity to recover.
			Log.e("recoverable", userRecoverableException.getMessage());
			((SettingsActivity) mActivity).handleException(userRecoverableException);
		} catch (GoogleAuthException fatalException) {
			// Some other type of unrecoverable exception has occurred.
			// Report and log the error as appropriate for your app.
			Log.e("fatal", fatalException.getMessage());
		}
		return null;
	}
}