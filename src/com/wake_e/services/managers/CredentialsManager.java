package com.wake_e.services.managers;

import com.wake_e.model.sqlite.WakeEDBHelper;
import com.wake_e.utils.Credentials;

public class CredentialsManager {
    
    
    private static Credentials credentials = null;
    
    private CredentialsManager() {
    }
    
    public CredentialsManager(WakeEDBHelper db){
	this();
	this.loadCredentials(db);
    }

    private void loadCredentials(WakeEDBHelper db) {
	this.credentials = db.getCredentials();
    }

    public Credentials getCredentials(){
	return this.credentials;
    }
    
    public void updateCredentials(WakeEDBHelper db, Credentials c){
	
    }
}
