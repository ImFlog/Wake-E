package com.wake_e.services.managers;

import java.util.Set;
import java.util.UUID;

import com.wake_e.model.Alarm;
import com.wake_e.model.AlarmSynchro;

/**
 * @brief Manage all alarms
 * @author Wake-E team
 */

public class AlarmsManager {
    //the synchronized alarm of the application
    @SuppressWarnings("unused")
    private AlarmSynchro alarmSynchro;

    //list of set alarms
    private Set<Alarm> alarms;


    /**
     * 
     */
    public AlarmsManager() {
	super();
    }


    /**
     * @brief synchronize every alarm
     */
    public void synchronizeAlarms() {
	// TODO boucle sur chaque alarme et appel de sa méthod synchronize
    }


    /**
     * @brief create an alarm and add it to the list
     */
    //TODO spécifier les paramètres en fonction de ce que nous donnera la vue
    public void createAlarm() {
	// TODO implement me
    }


    /**
     * @brief remove an alarm
     * @param alarmId the id of the alarm to remove
     */
    public void removeAlarm(UUID alarmId) {
	for(Alarm a : alarms){
	    if(a.getId() == alarmId){
		this.alarms.remove(a);
		return;
	    }
	}
    }


    /**
     * @brief synchronize an alarm
     * @param alarmId the alarm id
     */
    public void synchronizeAlarm(UUID alarmId) {
	// TODO implement me
    }


    /**
     * @brief retrieve an alarm
     * @param alarmId the alarm id
     * @return the alarm
     */
    public Alarm getAlarm(UUID alarmId) {
	for(Alarm a : alarms){
	    if(a.getId() == alarmId){
		return a;
	    }
	}
	return null;
    }

}
