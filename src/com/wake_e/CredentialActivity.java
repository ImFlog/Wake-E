package com.wake_e;

import java.util.List;

import com.wake_e.constants.WakeEConstants;
import com.wake_e.model.Credentials;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class CredentialActivity extends Activity {
	private AccountManager accountManager;
	private Controller controller;
	private String SCOPE = null;
	private String user = null;
	private String type = null;
	protected CredentialActivity that = this;

	public String getType() {
		return this.type;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		controller = Controller.getInstance(getApplicationContext());
		accountManager = AccountManager.get(this);
		Intent intent = getIntent();
		this.type = intent.getExtras().getString("type");
		
		if (this.type.equals("gmail")) {
			this.SCOPE = WakeEConstants.GMAIL_SCOPE;
		} else {
			throw new UnsupportedOperationException("Implement other scopes");
		}

		boolean tokenExist = false;
		List<Credentials> cr = controller.getCredentials();

		for (Credentials c : cr) {
		    if (c.getType() == this.type) {
		    	tokenExist = true;
		    }
		}

		if (!tokenExist) {
			chooseAccount();
		} else {
			finish();
		}
	}

	private void chooseAccount() {
		Intent intent = AccountManager.newChooseAccountIntent(null, null,
				new String[] { "com.google" }, false, null, null, null, null);
		startActivityForResult(intent, WakeEConstants.ACCOUNT_CODE);
	}

	private void requestToken() {
		Account userAccount = null;
		for (Account account : accountManager.getAccountsByType("com.google")) {
			if (account.name.equals(this.user)) {
				userAccount = account;
				break;
			}
		}

		accountManager.getAuthToken(userAccount, "oauth2:" + this.SCOPE, null, this,
				new OnTokenAcquired(), null);
	}

	/**
	 * call this method if your token expired, or you want to request a new
	 * token for whatever reason. call requestToken() again afterwards in order
	 * to get a new token.
	 */
	private void invalidateToken() {
		AccountManager accountManager = AccountManager.get(this);
		Controller controller = Controller.getInstance(getApplicationContext());
		Credentials c = controller.getCredentials(this.type);
		if (c != null) {
			accountManager.invalidateAuthToken("com.google", c.getAccessToken());
			controller.deleteCredentials(this.type);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			if (requestCode == WakeEConstants.AUTHORIZATION_CODE) {
				requestToken();
			} else if (requestCode == WakeEConstants.ACCOUNT_CODE) {
				this.user = data
						.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

				// invalidate old tokens which might be cached. we want a fresh
				// one, which is guaranteed to work
				invalidateToken();

				requestToken();
			}
		}
	}

	private class OnTokenAcquired implements AccountManagerCallback<Bundle> {

		@Override
		public void run(AccountManagerFuture<Bundle> result) {
			try {
				Bundle bundle = result.getResult();

				Intent launch = (Intent) bundle.get(AccountManager.KEY_INTENT);
				if (launch != null) {
					startActivityForResult(launch, WakeEConstants.AUTHORIZATION_CODE);
				} else {
					String token = bundle
							.getString(AccountManager.KEY_AUTHTOKEN);

					// save token
					Credentials c = new Credentials(that.type, token);
					Controller.getInstance(getApplicationContext()).updateCredentials(c);
					// Back to settings activity
					finish();
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}