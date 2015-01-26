package com.wake_e.services;

import android.os.Parcel;
import android.os.Parcelable;



/**
 * @brief Cette alarme redéfinit simplement la méthode "synchronize" puisqu'elle va
 * prendre en compte le ou les agendas avec le(s)quel(s) est synchronisée
 * l'application. Elle appelle getNextEvent().
 * @author Wake-E team
 */



public class AlarmSynchroIntentService extends AlarmIntentService {
    
    //name of the next event
    private String eventName;


    /**
     * @param depart 
     * @param arrivee 
     * @param preparationDuration 
     * @param ringtone 
     * @brief AlarmSynchro constructor
     */
    public AlarmSynchroIntentService() {
	super();

    }


    public AlarmSynchroIntentService(Parcel in) {
	super(in);
	this.eventName = in.readString();
    }


    /**
     * @brief get the event's name
     * @return the eventName
     */
    public String getEventName() {
	return eventName;
    }
    
    @Override
    public void synchronize() {
        // TODO Update travelDuration regarding the agenda
	
    }
    @Override
    public int describeContents() {
	return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
	super.writeToParcel(dest, flags);
	dest.writeString(this.eventName);

    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
	public AlarmSynchroIntentService createFromParcel(Parcel in) {
	    return new AlarmSynchroIntentService(in);
	}

	public AlarmSynchroIntentService[] newArray(int size) {
	    return new AlarmSynchroIntentService[size];
	}
    };
}
