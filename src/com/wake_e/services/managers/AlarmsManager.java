package com.wake_e.services.managers;

import java.util.Set;
import java.util.UUID;

import android.content.Context;
import android.content.Intent;

import com.wake_e.exceptions.NoRouteFoundException;
import com.wake_e.model.Location;
import com.wake_e.services.AlarmIntentService;
import com.wake_e.services.AlarmSynchroIntentService;

/**
 * @brief Manage all alarms
 * @author Wake-E team
 */

public class AlarmsManager {
    // the synchronized alarm of the application
    private AlarmSynchroIntentService alarmSynchro;

    // list of set alarms
    private Set<AlarmIntentService> alarms;

    /**
     * 
     */
    public AlarmsManager() {
	super();
    }

    /**
     * @brief create an alarm and add it to the list
     */
    // TODO spécifier les paramètres en fonction de ce que nous donnera la vue
    public void createAlarm(Context context, Location depart, Location arrivee,
	    long preparationDuration, String ringtone, String transport, long endHour) throws NoRouteFoundException {
	//On teste si une Route est trouvee avec le depart et l'arrivee
	//Si ce n'est pas le cas, on throw une exception
	
	Intent intent = new Intent(context, AlarmIntentService.class);
	AlarmIntentService alarm = new AlarmIntentService(depart, arrivee,
		preparationDuration, ringtone, transport, endHour);
	alarm.startService(intent);
	this.alarms.add(alarm);
	this.disabledAllTheOthers(alarm.getId());
    }

    /**
     * @brief remove an alarm
     * @param alarmId
     *            the id of the alarm to remove
     */
    public void removeAlarm(UUID alarmId) {
	for (AlarmIntentService a : alarms) {
	    if (a.getId() == alarmId) {
		a.stopSelf();
		this.alarms.remove(a);
		return;
	    }
	}
    }

    /**
     * @brief retrieve an alarm
     * @param alarmId
     *            the alarm id
     * @return the alarm
     */
    public AlarmIntentService getAlarm(UUID alarmId) {
	for (AlarmIntentService a : alarms) {
	    if (a.getId().equals(alarmId)) {
		return a;
	    }
	}
	return null;
    }

    /**
     * @brief enable or disable an alarm
     * @param alarmId
     * @param enabled
     *            TRUE=enabled FALSE=disabled
     * @param context
     */
    public void enableAlarm(UUID alarmId, boolean enabled, Context context) {
	AlarmIntentService a = this.getAlarm(alarmId);
	if (a != null) {
	    if (enabled) {
		if (!a.isEnabled()) {
		    a.enable(context);
		    this.disabledAllTheOthers(alarmId);
		}
	    } else {
		if (a.isEnabled()) {
		    a.disable();
		}
	    }
	}
    }

    /**
     * @brief get the enabled alarm
     * @return the enabled alarm
     */
    public AlarmIntentService getEnabledAlarm() {
	if (this.alarmSynchro.isEnabled()) {
	    return this.alarmSynchro;
	}
	for (AlarmIntentService a : this.alarms) {
	    if (a.isEnabled()) {
		return a;
	    }
	}
	return null;
    }

    /**
     * @brief get all alarms
     * @return all alarms
     */
    public Set<AlarmIntentService> getAlarms() {
	return this.alarms;
    }

    /**
     * @brief get the alarm synchro
     * @return the alarm synchro
     */
    public AlarmSynchroIntentService getAlarmSynchro() {
	return this.alarmSynchro;
    }

    /**
     * @brief enable the alarm synchro
     * @param enabled
     *            TRUE=enabled FALSE=disabled
     * @param context
     */
    public void enableAlarmSynchro(boolean enabled, Context context) {
	this.alarmSynchro.enable(context);
	this.disabledAllTheOthers(this.alarmSynchro.getId());
    }

    /**
     * @brief disable all the others alarms
     * @param alarmId
     *            the alarm ID
     */
    private void disabledAllTheOthers(UUID alarmId) {
	if (this.getAlarm(alarmId) == null) {
	    return;
	}

	for (AlarmIntentService a : this.alarms) {
	    if (!a.getId().equals(alarmId)) {
		a.disable();
	    }
	}
	if (!this.alarmSynchro.getId().equals(alarmId)) {
	    this.alarmSynchro.disable();
	}
    }

}
