package com.wake_e.services.managers;

import android.content.Context;
import android.content.Intent;

import com.wake_e.constants.WakeEConstants;
import com.wake_e.exceptions.NoRouteFoundException;
import com.wake_e.model.Location;
import com.wake_e.model.sqlite.WakeEDBHelper;
import com.wake_e.services.AlarmIntentService;
import com.wake_e.services.AlarmSynchroIntentService;

/**
 * @brief Manage all alarms
 * @author Wake-E team
 */

public class AlarmsManager{
    // the synchronized alarm of the application
    private AlarmSynchroIntentService alarmSynchro;

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

    private WakeEDBHelper db;

    
    public AlarmsManager(WakeEDBHelper db) {
	super();
	this.db = db;
	
    }

    public void loadAlarm(){
	this.db.loadAlarm();
    }

    /**
     * @return 
     * @brief create an alarm and add it to the list
     */
    public Intent createAlarm(Context context, Location depart, Location arrivee,
	    long preparationDuration, String ringtone, String transport,
	    long endHour) throws NoRouteFoundException {

	// TODO On teste si une Route est trouvee avec le depart et l'arrivee
	// Si ce n'est pas le cas, on throw une exception

	Intent intent = new Intent(context, AlarmIntentService.class);
	intent.putExtra(WakeEConstants.AlarmServiceExtras.DEPART, depart);
	intent.putExtra(WakeEConstants.AlarmServiceExtras.ARRIVEE, arrivee);
	intent.putExtra(WakeEConstants.AlarmServiceExtras.PREPARATION, preparationDuration);
	intent.putExtra(WakeEConstants.AlarmServiceExtras.RINGTONE, ringtone);
	intent.putExtra(WakeEConstants.AlarmServiceExtras.TRANSPORT, transport);
	intent.putExtra(WakeEConstants.AlarmServiceExtras.END_HOUR, endHour);

	return intent;
    }

    /**
     * @brief retrieve an alarm
     * @param alarmId
     *            the alarm id
     * @return the alarm
     */
    public AlarmIntentService getAlarm() {
	return AlarmIntentService.getInstance();
    }

    /**
     * @brief enable or disable an alarm
     * @param alarmId
     * @param enabled
     *            TRUE=enabled FALSE=disabled
     * @param context
     */
    public void enableAlarm(boolean enabled, Context context) {
	AlarmIntentService a = this.getAlarm();
	if (a != null) {
	    if (enabled) {
		if (!a.isEnabled()) {
		    this.db.insertAlarm(this.getAlarm());
		    a.enable(context);
		}
	    } else {
		if (a.isEnabled()) {
		    a.disable();
		}
	    }
	}
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
    }

    public String getWakeUpHour() {
	Long ms = AlarmIntentService.getInstance().computeWakeUp();
	StringBuffer text = new StringBuffer("");
	if (ms > DAY) {
	    text.append(ms / DAY).append(" days ");
	    ms %= DAY;
	}
	if (ms > HOUR) {
	    text.append(ms / HOUR).append(" hours ");
	    ms %= HOUR;
	}
	if (ms > MINUTE) {
	    text.append(ms / MINUTE).append(" minutes ");
	    ms %= MINUTE;
	}
	if (ms > SECOND) {
	    text.append(ms / SECOND).append(" seconds ");
	    ms %= SECOND;
	}
	return text.toString();
    }

    public boolean isAlarmRunning() {
	// TODO Auto-generated method stub
	
	return (this.getAlarm() != null && this.getAlarm().isEnabled());
    }



}
