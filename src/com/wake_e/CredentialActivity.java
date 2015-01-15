package com.wake_e;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		controller = Controller.getInstance(getApplicationContext());
		accountManager = AccountManager.get(this);
		Intent intent = getIntent();
		this.type = intent.getExtras().getString("type");

		if (this.type.equals("gmail")) {
			this.SCOPE = WakeEConstants.WakeEAPICalls.GMAIL_SCOPE;
		} else {
			throw new UnsupportedOperationException("Implement other scopes");
		}

		Credentials cr = controller.getCredentials(this.type);
		if (cr != null) {
			this.user = cr.getUser();
			invalidateToken();
			requestToken();
		} else {
			chooseAccount();
		}
	}

	private void chooseAccount() {
		Intent intent = AccountManager.newChooseAccountIntent(null, null,
				new String[] { "com.google" }, false, null, null, null, null);
		startActivityForResult(intent, WakeEConstants.WakeEAPICalls.ACCOUNT_CODE);
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
			if (requestCode == WakeEConstants.WakeEAPICalls.AUTHORIZATION_CODE) {
				requestToken();
			} else if (requestCode == WakeEConstants.WakeEAPICalls.ACCOUNT_CODE) {
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
					startActivityForResult(launch, WakeEConstants.WakeEAPICalls.AUTHORIZATION_CODE);
				} else {
					String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);

					// save token
					Credentials c = new Credentials(that.type, that.user, token);
					Controller.getInstance(getApplicationContext()).updateCredentials(c);
					finish();
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}