package com.wake_e.services;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.xml.datatype.Duration;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.directions.route.Routing.TravelMode;
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

    // Duration of the travel
    private Duration travelDuration;

    // Duration of the user's preparation
    private Duration preparationDuration;

    // start location
    private Location depart;

    // end location
    private Location arrivee;

    private TravelMode transport;

    /**
     * @param depart
     * @param arrivee
     * @param preparationDuration
     * @param ringtone
     * @brief Alarm's constructor
     */
    public AlarmIntentService(Location depart, Location arrivee,
	    Duration preparationDuration, String ringtone, TravelMode transport) {
	super("AlarmIntentService");
	this.id = UUID.randomUUID();
	this.enabled = true;
	this.depart = depart;
	this.arrivee = arrivee;
	this.preparationDuration = preparationDuration;
	this.transport = transport;
	// TODO : appel à l'API google/mappi pour le travelDuration

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
    public Duration getTravelDuration() {
	return travelDuration;
    }

    /**
     * @return the preparationDuration
     */
    public Duration getPreparationDuration() {
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
    public TravelMode getTransport() {
	return transport;
    }

    /**
     * @brief compute wake up time
     * @return wake up time
     */
    public Date computeWakeUp() {
	// TODO compute wake up time regarding travelDuration +
	// preparationDuration
	return null;
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
	// TODO API call, travelDuration update
//	start = new LatLng(18.015365, -77.499382);
//	end = new LatLng(18.012590, -77.500659);
//
//	Routing routing = new Routing(Routing.TravelMode.WALKING);
//	routing.registerListener(this);
//	routing.execute(start, end);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
	Calendar c = Calendar.getInstance();
	boolean it_is_time = false;
	int cpt = 0;
	while (!it_is_time) {
	    try {
		Thread.sleep(1000);
		cpt++;
	    } catch (InterruptedException e) {
	    }

	    // On se synchronise
	    if (cpt == 600000) {
		this.synchronize();
	    }

	    // Si l'heure du portable égale ou inférieur à l'heure de réveil
	    // on lance une activity pour sonner
	    if (this.computeWakeUp().equals(c.getTime())) {
		it_is_time = true;
		this.ring();
	    }
	}

    }

}
