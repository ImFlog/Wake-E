package com.wake_e.constants;


/**
 * Wake-E's constants
 * @author Wake-E team
 */

public class WakeEConstants
{
	/**
	 * String to access the Mail Deliverer in the WakeE's HashMap
	 */
	
	public static final String MAIL_DELIVERER = "MAIL_DELIVERER";
	
	/**
	 * String to access the Agenda Deliverer in the WakeE's HashMap
	 */
	
	public static final String AGENDA_DELIVERER = "AGENDA_DELIVERER";
	
	/**
	 * String to access the Meteo Deliverer in the WakeE's HashMap
	 */
	
	public static final String METEO_DELIVERER = "METEO_DELIVERER";
	
	// API CALLS
	
	/**
	 * Scope for gmail api auth.
	 */
	public static final String GMAIL_SCOPE = "https://www.googleapis.com/auth/gmail.readonly";

	/**
	 * API calls authorization code.
	 */
	public static final int AUTHORIZATION_CODE = 1993;

	/**
	 * Api calls account code.
	 */
	public static final int ACCOUNT_CODE = 1601;
	
	
	/**
	 */
	private WakeEConstants(){
		super();
	}

}

