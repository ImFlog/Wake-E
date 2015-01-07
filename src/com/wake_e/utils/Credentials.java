package com.wake_e.utils;

public class Credentials {
    
    private String type, userId, accessToken;
    
    public Credentials(String userId, String type, String accessToken) {
	this.userId = userId;
	this.type = type;
	this.accessToken = accessToken;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
	return userId;
    }

    /**
     * @return the type
     */
    public String getType() {
	return type;
    }

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
	return accessToken;
    }

}
