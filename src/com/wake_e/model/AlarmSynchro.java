package com.wake_e.model;

import javax.xml.datatype.Duration;

/**
 * @brief Cette alarme redéfinit simplement la méthode "synchronize" puisqu'elle va
 * prendre en compte le ou les agendas avec le(s)quel(s) est synchronisée
 * l'application. Elle appelle getNextEvent().
 * @author Wake-E team
 */


//TODO Make this class a Singleton

public class AlarmSynchro extends Alarm {
    
    //name of the next event
    private String eventName;


    /**
     * @param depart 
     * @param arrivee 
     * @param preparationDuration 
     * @param ringtone 
     * @brief AlarmSynchro constructor
     */
    public AlarmSynchro(Location depart, Location arrivee,
	    Duration preparationDuration, String ringtone) {
	super(arrivee, arrivee, preparationDuration, ringtone);
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
