package com.wake_e.services.managers;

import java.util.ArrayList;
import java.util.List;

import com.wake_e.model.Credentials;
import com.wake_e.model.sqlite.WakeEDBHelper;

public class CredentialsManager {


	private List<Credentials> credentials = new ArrayList<Credentials>();

	private CredentialsManager() {
	}

	public CredentialsManager(WakeEDBHelper db) {
		this();
		this.loadCredentials(db);
	}

	private void loadCredentials(WakeEDBHelper db) {
		this.credentials.addAll(db.getCredentials());
	}

	public List<Credentials> getCredentials() {
		return this.credentials;
	}

	public Credentials getCredentials(String type) {
		for (Credentials c: this.credentials) {
			if (c.getType() == type) {
				return c;
			}
		}
		return null;
	}
	
	public void updateCredentials(WakeEDBHelper db, Credentials c) {
		db.updateCredentials(c);
		this.loadCredentials(db);
	}
	
	public void deleteCredentials(WakeEDBHelper db, String type) {
		db.deleteCredential(type);
		this.loadCredentials(db);
	}
}
