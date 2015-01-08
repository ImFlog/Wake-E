package com.wake_e.model;

public class Credentials {

	private String type, accessToken;

	public Credentials(String type, String accessToken) {
		this.type = type;
		this.accessToken = accessToken;
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
