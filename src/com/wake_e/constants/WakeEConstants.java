package com.wake_e.constants;


/**
 * Wake-E's constants
 * @author Wake-E team
 */

public class WakeEConstants
{

    /**
     * @brief APIs's calls constants
     * @author Wake-E team
     */
    public static final class WakeEAPICalls{
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
    }


    /**
     * @brief Parsing constants
     * @author Wake-E team
     */
    public static final class WakeEParsing{
	/**
	 * Point split character
	 */
	public static final String POINT_SPLIT_CHARACTER = ";";
    }


    /**
     * @brief Transports types constants
     * @author Wake-E team
     */
    public static final class WakeETransports{
	/**
	 * transport voiture
	 */
	public static final String TRANSPORT_VOITURE = "voiture";

	/**
	 * transport commun
	 */
	public static final String TRANSPORT_COMMUN = "commun";

	/**
	 * transport velo
	 */
	public static final String TRANSPORT_VELO = "velo";

    }

    /**
     */
    private WakeEConstants(){
	super();
    }

}

