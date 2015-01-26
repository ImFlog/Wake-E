package com.wake_e.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.os.Process;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.wake_e.Controller;
import com.wake_e.MainActivity;
import com.wake_e.R;
import com.wake_e.SnoozeActivity;
import com.wake_e.constants.WakeEConstants;
import com.wake_e.model.Credentials;
import com.wake_e.model.Location;
import com.wake_e.tools.TokenRequester;


/**
 * @author Wake-E team
 * @brief Class representing an Alarm
 */

public class AlarmIntentService extends Service implements Parcelable{

    // Alarm's id
    private ParcelUuid id;

    // Path to the ringtone
    private String ringtone;

    // Is the alarm enabled ?
    private boolean enabled;

    // Duration of the travel in milliseconds
    private long travelDuration;

    // Duration of the user's preparation in milliseconds
    private long preparationDuration;

    // start location
    private Location depart;

    // end location
    private Location arrivee;

    // transport mode
    private String modeTransport;

    // end hour
    private long endHour;

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private static AlarmIntentService instance;

    /**
     * @brief Alarm's constructor
     */

    public AlarmIntentService() {
	//super("AlarmIntentService");
	this.id = new ParcelUuid(UUID.randomUUID());
    }


    public AlarmIntentService(Parcel in) {
	//super("AlarmIntentService");
	this.id = in.readParcelable(ParcelUuid.class.getClassLoader());
	this.ringtone = in.readString();
	this.enabled = in.readByte() != 0;
	this.travelDuration = in.readLong();
	this.preparationDuration = in.readLong();
	this.depart = in.readParcelable(Location.class.getClassLoader());
	this.arrivee = in.readParcelable(Location.class.getClassLoader());
	this.modeTransport = in.readString();
    }

    /**
     * @brief get the alarm id
     * @return the alarm id
     */
    public ParcelUuid getId() {
	return this.id;
    }

    /**
     * @return the ringtone
     */
    public String getRingtone() {
	return ringtone;
    }

    /**
     * @param ringtone
     *            the ringtone to set
     */
    public void setRingtone(String ringtone) {
	this.ringtone = ringtone;
    }

    /**
     * @return the travelDuration
     */
    public long getTravelDuration() {
	return travelDuration;
    }

    /**
     * @return the preparationDuration
     */
    public long getPreparationDuration() {
	return preparationDuration;
    }

    /**
     * @return the depart
     */
    public Location getDepart() {
	return depart;
    }

    /**
     * @return the arrivee
     */
    public Location getArrivee() {
	return arrivee;
    }

    /**
     * @return the transport
     */

    public String getModeTransport() {
	return modeTransport;
    }

    /**
     * @return the end hour
     */
    public long getEndHour() {
	return endHour;

    }

    /**
     * @brief compute wake up time
     * @return wake up time
     */
    public Long computeWakeUp() {
	Calendar c = Calendar.getInstance();
	Long wake_up = c.getTimeInMillis() + this.travelDuration
		+ this.preparationDuration;
	if (wake_up > this.endHour) {
	    wake_up = c.getTimeInMillis();
	}
	return wake_up;
    }

    /**
     * @brief get a string of the wake up time
     * @return the wake up time in string
     */
    public String toStringWakeUp() {
	return this.computeWakeUp().toString();
    }

    /**
     * @brief ring the alarm
     */
    public void ring() {
	// Est-ce que l'on a déjà des credentials ?
	Credentials c = Controller.getInstance(Controller.getContext())
		.getCredentials(WakeEConstants.Credentials.GMAIL);
	// Si oui on refresh le token
	if(c != null){
	    TokenRequester.initiateRequest(null, c.getUser());
	}
	// Une fois que le token est refreshed on lance la snooze activity
	Intent i = new Intent(Controller.getContext(), SnoozeActivity.class);
	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	this.startActivity(i);
    }

    /**
     * @param context
     * @brief enable the alarm
     */
    public void enable(Context context) {
	this.enabled = true;
    }

    /**
     * @brief disable the alarm
     */
    public void disable() {
	this.enabled = false;
	this.stopSelf();
    }

    /**
     * @brief Is the alarm enabled ?
     * @return TRUE if the alarm is enabled, else FALSE
     */
    public boolean isEnabled() {
	return this.enabled;
    }

    /**
     * @brief synchronize the alarm according to the Google API / Mappi API
     */
    public void synchronize() {
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
	.permitAll().build();
	StrictMode.setThreadPolicy(policy);
	StringBuilder stringBuilder = new StringBuilder();
	try {
	    // Construire l'URL a questionner
	    String url = "http://route.cit.api.here.com/routing/7.2/calculateroute.json?"
		    + "app_id="

		    + WakeEConstants.Here.app_id
		    + "&app_code="
		    + WakeEConstants.Here.app_code
		    + "&waypoint0=geo!"
		    + this.depart.getGps().getLatitude()
		    + ","
		    + this.depart.getGps().getLongitude()
		    + "&waypoint1=geo!"
		    + this.arrivee.getGps().getLatitude()
		    + ","
		    + this.arrivee.getGps().getLongitude()
		    + "&mode=fastest;"
		    + this.modeTransport
		    + ";"
		    + "traffic:enabled";

	    // Envoi de la requete
	    HttpPost httppost = new HttpPost(url);

	    HttpClient client = new DefaultHttpClient();
	    HttpResponse response;

	    stringBuilder = new StringBuilder();

	    response = client.execute(httppost);

	    // Reception de la reponse
	    HttpEntity entity = response.getEntity();
	    InputStream stream = entity.getContent();
	    int b;
	    while ((b = stream.read()) != -1) {
		stringBuilder.append((char) b);
	    }
	} catch (ClientProtocolException e) {
	} catch (IOException e) {
	}

	JSONObject jsonObject = new JSONObject();
	// Parsing du JSON
	try {

	    jsonObject = new JSONObject(stringBuilder.toString());

	    JSONObject response = jsonObject.getJSONObject("response");

	    JSONArray array = response.getJSONArray("routes");

	    JSONObject routes = array.getJSONObject(0);

	    JSONArray legs = routes.getJSONArray("legs");

	    JSONObject summary = legs.getJSONObject(0);

	    JSONObject time = summary.getJSONObject("traffictime");

	    // Log.i("Distance", time.toString());
	    // Mise a jour du temps de trajet
	    this.travelDuration = Long.valueOf(time.toString()).longValue();

	} catch (JSONException e) {
	}

    }



    @Override
    public int describeContents() {
	return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

	dest.writeParcelable(this.id, flags);
	dest.writeString(this.ringtone);
	dest.writeByte((byte) (this.enabled ? 1 : 0));
	dest.writeLong(this.travelDuration);
	dest.writeLong(this.preparationDuration);
	dest.writeParcelable(this.depart, flags);
	dest.writeParcelable(this.arrivee, flags);
	dest.writeString(this.modeTransport);

    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
	public AlarmIntentService createFromParcel(Parcel in) {
	    return new AlarmIntentService(in);
	}

	public AlarmIntentService[] newArray(int size) {
	    return new AlarmIntentService[size];
	}
    };

    @Override
    public void onCreate() {
	super.onCreate();
	if(AlarmIntentService.instance != null){
	    AlarmIntentService.instance.disable();
	}
	AlarmIntentService.instance = this;

	HandlerThread thread = new HandlerThread("ServiceStartArguments",
		Process.THREAD_PRIORITY_BACKGROUND);
	thread.start();

	// Get the HandlerThread's Looper and use it for our Handler
	mServiceLooper = thread.getLooper();
	mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    public static AlarmIntentService getInstance(){
	return AlarmIntentService.instance;
    }

    @Override
    public void onDestroy(){
	Log.i("DESTRUCTION ALARME", "alarme détruite");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	//Initialisation
	this.depart = intent
		.getParcelableExtra(WakeEConstants.AlarmServiceExtras.DEPART);
	this.arrivee = intent
		.getParcelableExtra(WakeEConstants.AlarmServiceExtras.ARRIVEE);
	this.enabled = true;
	this.endHour = intent.getLongExtra(
		WakeEConstants.AlarmServiceExtras.END_HOUR, -1);
	this.modeTransport = intent
		.getStringExtra(WakeEConstants.AlarmServiceExtras.TRANSPORT);
	this.preparationDuration = intent.getLongExtra(
		WakeEConstants.AlarmServiceExtras.PREPARATION, -1);
	this.ringtone = intent
		.getStringExtra(WakeEConstants.AlarmServiceExtras.RINGTONE);

	Message msg = mServiceHandler.obtainMessage();
	msg.arg1 = startId;
	mServiceHandler.sendMessage(msg);


	// If we get killed, after returning from here, restart
	return START_REDELIVER_INTENT;
    }


    @Override
    public IBinder onBind(Intent intent) {
	// TODO Auto-generated method stub
	return null;
    }

    ;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
	public ServiceHandler(Looper looper) {
	    super(looper);
	}
	
	private void showNotification(Context context) {
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(context)
		.setSmallIcon(R.drawable.notification)
		.setOngoing(true)
		.setContentTitle("Wake-E")
		.setContentText("Sleep tight!");
		
		
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(context, MainActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
				stackBuilder.getPendingIntent(
						0,
						PendingIntent.FLAG_UPDATE_CURRENT
						);
		mBuilder.setContentIntent(resultPendingIntent);
		
		startForeground(WakeEConstants.Notif.NOTIFICATION, mBuilder.build());
	}
	@Override
	public void handleMessage(Message msg) {
	    // Normally we would do some work here, like download a file.
	    // For our sample, we just sleep for 5 seconds.


	    //Controller.getInstance(Controller.getContext()).setAlarm(this);
	    
	    this.showNotification(Controller.getContext());

	    Calendar c = Calendar.getInstance();
	    boolean it_is_time = false;
	    int cpt = 0;

	    // On ne commence la synchronisation qu'à partir de 4h avant la date de
	    // réveil
	    while (AlarmIntentService.instance.computeWakeUp() < (c.getTimeInMillis() + 14400000)) {
		if(!AlarmIntentService.instance.isEnabled()){
		    it_is_time = true;
		    break;
		}
		Log.i("ALARME", "alarme en cours");
	    }

	    while (!it_is_time) {
		try {
		    Thread.sleep(1000);
		    cpt++;
		} catch (InterruptedException e) {
		}

		if(AlarmIntentService.instance.isEnabled()){
		    // On se synchronise
		    if (cpt == 1200000) {
			AlarmIntentService.instance.synchronize();
		    }

		    // Si l'heure du portable égale ou inférieur à l'heure de réveil
		    // on lance une activity pour sonner
		    if (AlarmIntentService.instance.computeWakeUp() >= c.getTimeInMillis()) {
			// check mails, agenda & meteo
			it_is_time = true;
			AlarmIntentService.instance.ring();
		    }
		}else{
		    it_is_time = true;
		}
	    }
	    // Stop the service using the startId, so that we don't stop
	    // the service in the middle of handling another job
	    stopSelf(msg.arg1);
	}
    }
}
