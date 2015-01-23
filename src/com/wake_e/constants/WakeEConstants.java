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
    public abstract class APICalls{
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
    public abstract class Parsing{
	/**
	 * Point split character
	 */
	public static final String POINT_SPLIT_CHARACTER = ";";
    }


    /**
     * @brief Transports types constants
     * @author Wake-E team
     */
    public abstract class Transports{
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
     * @brief intent extrat for Alarm service
     * @author Wake-E team
     */
    public abstract class AlarmServiceExtras {
	public static final String DEPART = "depart";
	public static final String ARRIVEE = "arrivee";
	public static final String PREPARATION = "preparation";
	public static final String RINGTONE = "ringtone";
	public static final String TRANSPORT = "transport";
	public static final String END_HOUR = "endhour";
	public static final String MANAGER = "manager";
    }
    /**
     * @brief Nokia Maps contsants
     * @author Wake-E team
     */
    public abstract class Here{
	public static final String app_id = "Kpmuj02vUeakK6N5z3mK" ;
	public static final String app_code = "iKL-xOAc6YOvwMNalfrKeA";
    }
    /**
     */
    private WakeEConstants(){
	super();
    }


    /**
     * @brief notif constants
     * @author Wake-E team
     */
    public abstract class Notif{
	public static final int NOTIFICATION = 10002; //Any unique number for this notification
    }


    public abstract class Credentials{
	public static final String GMAIL = "gmail";
    }

    public abstract class Signal{
	public static final int SETTINGS = 1;
    }

    public abstract class SlidesNames {
	public static final String SLIDE_METEO = "Météo";
	public static final String SLIDE_MAIL = "Mail";
	public static final String SLIDE_AGENDA = "Agenda";
    }
}

