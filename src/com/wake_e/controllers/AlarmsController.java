package com.wake_e.controllers;

import java.util.Set;
import java.util.UUID;

import javax.xml.datatype.Duration;

import android.content.Context;

import com.wake_e.exceptions.NoRouteFoundException;
import com.wake_e.model.Location;
import com.wake_e.model.sqlite.WakeEDBHelper;
import com.wake_e.services.AlarmIntentService;
import com.wake_e.services.AlarmSynchroIntentService;
import com.wake_e.services.managers.AlarmsManager;

/**
 * @brief Singleton and main controller of the application
 * @author Wake-E team
 */
public class AlarmsController {
    
    //the db helper
    private WakeEDBHelper db;
    
    //the app context
    private Context context;
    
    //all our managers
    private AlarmsManager alarmsManager;

    private static AlarmsController controller;

    /**
     * @param context
     *            le contexte de l'application
     */
    private AlarmsController(Context context) {
	super();
	
	this.context = context;
	this.db = new WakeEDBHelper(context);
	this.alarmsManager = new AlarmsManager();
    }

    /**
     * @brief get the Controller instance
     * @param context
     *            the app context
     * @return the Controller instance
     */
    public static AlarmsController getInstance(Context context) {
	if (AlarmsController.controller == null) {
	    AlarmsController.controller = new AlarmsController(context);
	}
	return AlarmsController.controller;
    }

    // ########### ALARMS ###########
    
    /**
     * @brief create a new alarm
     * @param context the app context
     * @param depart the start location
     * @param arrivee the end location
     * @param preparationDuration the preparation duration
     * @param ringtone the ringtone
     * @throws NoRouteFoundException 
     */
    public void createAlarm(Context context, Location depart, Location arrivee,
	    long preparationDuration, String ringtone, String transport, long endHour) throws NoRouteFoundException {
	this.alarmsManager.createAlarm(context, depart, arrivee, preparationDuration, ringtone, transport, endHour);
    }
    
    /**
     * @brief delete an alarm
     * @param alarmId
     */
    public void deleteAlarm(UUID alarmId){
	this.alarmsManager.removeAlarm(alarmId);
    }
    
    /**
     * @brief enable or disable an alarm
     * @param alarmId
     * @param enabled TRUE=enabled FALSE=disabled
     * @param context 
     */
    public void enableAlarm(UUID alarmId, boolean enabled, Context context){
	this.alarmsManager.enableAlarm(alarmId, enabled, context);
    }
    
    /**
     * @brief get the enabled alarm
     * @return the enabled alarm
     */
    public AlarmIntentService getEnabledAlarm(){
	return this.alarmsManager.getEnabledAlarm();
    }
    
    /**
     * @brief get all alarms
     * @return all alarms
     */
    public Set<AlarmIntentService> getAlarms(){
	return this.alarmsManager.getAlarms();
    }
    
    /**
     * @brief get the alarm synchro
     * @return the alarm synchro
     */
    public AlarmSynchroIntentService getAlarmSynchro(){
	return this.alarmsManager.getAlarmSynchro();
    }
    
    /**
     * @brief enable the alarm synchro
     * @param enabled TRUE=enabled FALSE=disabled
     * @param context 
     */
    public void enabledAlarmSynchro(boolean enabled, Context context){
	this.alarmsManager.enableAlarmSynchro(enabled, context);
    }
    
    
}
