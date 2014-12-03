package com.wake_e.model;
import java.util.Date;
import java.util.UUID;

import javax.xml.datatype.Duration;


/**
 * @author Wake-E team
 * @brief Class representing an Alarm
 */

public class Alarm
{

    //Alarm's id
    private UUID id;

    //Path to the ringtone
    private String ringtone;

    //Is the alarm enabled ?
    private boolean enabled;

    //Duration of the travel
    private Duration travelDuration;

    //Duration of the user's preparation
    private Duration preparationDuration;

    //start location
    private Location depart;

    //end location
    private Location arrivee;

    /**
     * @param depart 
     * @param arrivee 
     * @param preparationDuration 
     * @param ringtone 
     * @brief Alarm's constructor
     */
    public Alarm(Location depart, Location arrivee, Duration preparationDuration, String ringtone){
	super();
	this.id = UUID.randomUUID();
	this.enabled = true;
	this.depart = depart;
	this.arrivee = arrivee;
	this.preparationDuration = preparationDuration;
	//TODO : appel à l'API google/mappi pou le travelDuration

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
     * @param ringtone the ringtone to set
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
     * @brief compute wake up time
     * @return wake up time
     */
    public Date computeWakeUp() {
	// TODO compute wake up time regarding travelDuration + preparationDuration
	return null;	
    }


    /**
     * @brief get a string of the wake up time
     * @return the wake up time in string
     */
    public String toStringWakeUp() {
	return this.computeWakeUp().toLocaleString();
    }

    /**
     * @brief ring the alarm
     */
    public void ring() {
	// TODO make the ringtone ring (thread in order not to block the app)
    }



    /**
     * @brief enable the alarm
     */
    public void enable() {
	this.enabled = true;	
    }

    /**
     * @brief disable the alarm
     */
    public void disable() {
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
    }


}

