package com.wake_e;

import java.util.HashMap;

import android.content.Context;

import com.wake_e.constants.WakeEConstants;
import com.wake_e.services.deliverers.AgendaDeliverer;
import com.wake_e.services.deliverers.FunctionnalitiesDeliverer;
import com.wake_e.services.deliverers.MailDeliverer;
import com.wake_e.services.deliverers.MeteoDeliverer;
import com.wake_e.services.managers.AlarmsManager;
import com.wake_e.services.managers.LocationsManager;
import com.wake_e.services.managers.SlidesManager;

/**
 * @brief Singleton and main controller of the application
 * @author Wake-E team
 */
public class Controller {
    // all our deliverers
    private HashMap<String, FunctionnalitiesDeliverer<?>> functionalitiesDeliverer;

    private static Controller controller;

    /**
     * 
     */
    private Controller() {
	super();
	this.functionalitiesDeliverer = new HashMap<String, FunctionnalitiesDeliverer<?>>();
	this.functionalitiesDeliverer.put(WakeEConstants.AGENDA_DELIVERER,
		new AgendaDeliverer());
	this.functionalitiesDeliverer.put(WakeEConstants.MAIL_DELIVERER,
		new MailDeliverer());
	this.functionalitiesDeliverer.put(WakeEConstants.METEO_DELIVERER,
		new MeteoDeliverer());
    }

    public static Controller getInstance() {
	if (Controller.controller == null) {
	    Controller.controller = new Controller();
	}
	return Controller.controller;
    }

    /**
     * @brief get a SlidesManager instance
     * @param context
     *            the application's context
     * @return a SlidesManager instance according to the context provided
     */
    public SlidesManager getSlidesManager(Context context) {
	return new SlidesManager(context);
    }

    /**
     * @brief get an AlarmManager instance
     * @return an AlarmManager instance
     */
    public AlarmsManager getAlarmManager() {
	return new AlarmsManager();
    }

    /**
     * @brief get a LocationManager instance
     * @return a LocationManager instance
     */
    public LocationsManager getLocationManager() {
	return new LocationsManager();
    }

    public FunctionnalitiesDeliverer<?> getFunctionnalitiesDeliverer(
	    String delivererType) {
	return this.functionalitiesDeliverer.get(delivererType);
    }
}
