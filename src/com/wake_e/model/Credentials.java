package com.wake_e.model;

public class Credentials {
    
    private String type, user, accessToken;
    
    public Credentials(String type, String user, String accessToken) {
	this.type = type;
	this.user = user;
	this.accessToken = accessToken;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
