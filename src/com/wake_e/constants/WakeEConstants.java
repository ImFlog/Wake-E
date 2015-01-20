package com.wake_e.constants;

/**
 * Wake-E's constants
 * @author Wake-E team
 */

public abstract class WakeEConstants
{

    /**
     * @brief APIs's calls constants
     * @author Wake-E team
     */
    public abstract class WakeEAPICalls{
	/**
	 * Scope for gmail api auth.
	 */
	public static final String GMAIL_SCOPE = "oauth2:https://www.googleapis.com/auth/gmail.readonly";

	/**
	 * API calls authorization code.
	 */
	public static final int AUTHORIZATION_CODE = 1993;

	/**
	 * Api calls account code.
	 */
	public static final int ACCOUNT_CODE = 1601;
    
	/**
	 * Play service error.
	 */
	public static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1001;
    }

    

    /**
     * @brief Parsing constants
     * @author Wake-E team
     */
    public abstract class WakeEParsing{
	/**
	 * Point split character
	 */
	public static final String POINT_SPLIT_CHARACTER = ";";
    }


    /**
     * @brief Transports types constants
     * @author Wake-E team
     */
    public abstract class WakeETransports{
	/**
	 * transport voiture
	 */
	public static final String TRANSPORT_VOITURE = "car";


	/**
	 * transport commun
	 */
	public static final String TRANSPORT_COMMUN = "transit";


	/**
	 * transport velo
	 */
	public static final String TRANSPORT_VELO = "biking";


    }

    /**
     * @brief Nokia Maps contsants
     * @author Wake-E team
     */
    public abstract class WakeENokiaMaps{
	public static final String app_id = "Kpmuj02vUeakK6N5z3mK" ;
	public static final String app_code = "iKL-xOAc6YOvwMNalfrKeA";
    }
    /**
     */
    private WakeEConstants(){
	super();
    }

}

