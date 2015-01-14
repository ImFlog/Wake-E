package com.wake_e.services;

import com.wake_e.model.Location;

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
    public AlarmSynchroIntentService(Location depart, Location arrivee,
	    long preparationDuration, String ringtone, String transport, long endHour) {
	super(arrivee, arrivee, preparationDuration, ringtone, transport, endHour);
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

}
