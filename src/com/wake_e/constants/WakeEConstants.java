package com.wake_e.constants;

import com.directions.route.Routing;
import com.directions.route.Routing.TravelMode;


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
	public static final TravelMode TRANSPORT_VOITURE = Routing.TravelMode.DRIVING;

	/**
	 * transport commun
	 */
	public static final TravelMode TRANSPORT_COMMUN = Routing.TravelMode.TRANSIT;

	/**
	 * transport velo
	 */
	public static final TravelMode TRANSPORT_VELO = Routing.TravelMode.BIKING;

    }

    /**
     */
    private WakeEConstants(){
	super();
    }

}

