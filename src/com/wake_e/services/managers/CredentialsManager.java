package com.wake_e.services.managers;

import java.util.ArrayList;
import java.util.List;
import com.wake_e.model.Credentials;
import com.wake_e.model.sqlite.WakeEDBHelper;

public class CredentialsManager {


	private WakeEDBHelper db;
	private List<Credentials> credentials = new ArrayList<Credentials>();

	private CredentialsManager() {
	}

	public CredentialsManager(WakeEDBHelper db) {
		this();
		this.db = db;
		this.loadCredentials();
	}

	private void loadCredentials() {
		this.credentials.clear();
		this.credentials.addAll(db.getCredentials());
	}

	public List<Credentials> getCredentials() {
		this.loadCredentials();
		return this.credentials;
	}

	public Credentials getCredentials(String type) {
		this.loadCredentials();
		for (Credentials c: this.credentials) {
			if (c.getType().equals(type)) {
				return c;
			}
		}
		return null;
	}
	
	public void updateCredentials(Credentials c) {
		db.updateCredentials(c);
		this.loadCredentials();
	}
	
	public void deleteCredentials(String type) {
		db.deleteCredential(type);
		this.loadCredentials();
	}
}
