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

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.wake_e.constants.WakeEConstants;
import com.wake_e.model.Location;

/**
 * @author Wake-E team
 * @brief Class representing an Alarm
 */

public class AlarmIntentService extends IntentService {

    // Alarm's id
    private UUID id;

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


    /**
     * @param depart
     * @param arrivee
     * @param preparationDuration
     * @param ringtone
     * @brief Alarm's constructor
     */
    public AlarmIntentService(Location depart, Location arrivee,
	    long preparationDuration, String ringtone, String transport, long endHour) {

	super("AlarmIntentService");
	this.id = UUID.randomUUID();
	this.enabled = true;
	this.depart = depart;
	this.arrivee = arrivee;
	this.preparationDuration = preparationDuration;
	this.modeTransport = transport;
	this.endHour = endHour;

	// TODO : appel à l'API google/mappi pour le travelDuration
	this.synchronize();
	this.setRingtone(ringtone);
    }

    /**
     * @brief get the alarm id
     * @return the alarm id
     */
    public UUID getId() {
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
	Long wake_up = c.getTimeInMillis() + this.travelDuration + this.preparationDuration;
	if(wake_up > this.endHour){
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
	// TODO make the ringtone ring (Activity in order not to block the app)
	// Lancement de l'activity réveil (snooze, etc)
    }

    /**
     * @param context
     * @brief enable the alarm
     */
    public void enable(Context context) {
	this.enabled = true;
	Intent intent = new Intent(context, this.getClass());
	this.startService(intent);
    }

    /**
     * @brief disable the alarm
     */
    public void disable() {
	this.stopSelf();
	this.enabled = false;
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
	StringBuilder stringBuilder = new StringBuilder();
	try {
	    //Construire l'URL a questionner
	    String url = "http://route.cit.api.here.com/routing/7.2/calculateroute.json?"
		    + "app_id=" + WakeEConstants.WakeENokiaMaps.app_id
		    + "&app_code=" + WakeEConstants.WakeENokiaMaps.app_code
		    + "&waypoint0=geo!" + this.depart.getGps().getLatitude() + "," + this.depart.getGps().getLongitude()
		    + "&waypoint1=geo!" + this.arrivee.getGps().getLatitude() + "," + this.arrivee.getGps().getLongitude()
		    + "&mode=fastest;" + this.modeTransport
		    + ";"
		    + "traffic:enabled";

	    //Envoi de la requete
	    HttpPost httppost = new HttpPost(url);

	    HttpClient client = new DefaultHttpClient();
	    HttpResponse response;

	    stringBuilder = new StringBuilder();

	    response = client.execute(httppost);

	    //Reception de la reponse
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
	//Parsing du JSON
	try {

	    jsonObject = new JSONObject(stringBuilder.toString());

	    JSONObject response = jsonObject.getJSONObject("response");

	    JSONArray array = response.getJSONArray("routes");

	    JSONObject routes = array.getJSONObject(0);

	    JSONArray legs = routes.getJSONArray("legs");

	    JSONObject summary = legs.getJSONObject(0);

	    JSONObject time = summary.getJSONObject("traffictime");

	    //	    Log.i("Distance", time.toString());
	    //Mise a jour du temps de trajet
	    this.travelDuration = Long.valueOf(time.toString()).longValue();

	} catch (JSONException e) {
	}

    }

    @Override
    protected void onHandleIntent(Intent intent) {
	Calendar c = Calendar.getInstance();
	boolean it_is_time = false;
	int cpt = 0;
	
	//On ne commence la synchronisation qu'à partir de 4h avant la date de réveil
	while(this.computeWakeUp() < (c.getTimeInMillis() + 14400000)){}
	
	while (!it_is_time) {
	    try {
		Thread.sleep(1000);
		cpt++;
	    } catch (InterruptedException e) {
	    }

	    // On se synchronise
	    if (cpt == 1200000) {
		this.synchronize();
	    }

	    // Si l'heure du portable égale ou inférieur à l'heure de réveil
	    // on lance une activity pour sonner
	    if (this.computeWakeUp() >= c.getTimeInMillis()) {
		//check mails, agenda & meteo
		it_is_time = true;
		this.ring();
	    }
	}

    }

}
