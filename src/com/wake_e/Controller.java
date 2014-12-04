package com.wake_e;

import java.util.HashMap;

import com.wake_e.services.deliverers.FunctionalitiesDeliverer;
import com.wake_e.services.managers.AlarmsManager;
import com.wake_e.services.managers.LocationsManager;
import com.wake_e.services.managers.SlidesManager;

/**
 * @brief Singleton and main controller of the application
 * @author Wake-E team
 */
public class Controller {
    //all our deliverers
    @SuppressWarnings({ "rawtypes", "unused" })
    private HashMap<String,FunctionalitiesDeliverer> functionalitiesDeliverer;
    
    //TODO : faire une hashmap comme pour les deliverers
    //slides manager
    @SuppressWarnings("unused")
    private SlidesManager slidesManager;

    //alarms manager
    @SuppressWarnings("unused")
    private AlarmsManager alarmsManager;

    //locations manager
    @SuppressWarnings("unused")
    private LocationsManager locationsManager;


    /**
     * 
     */
    public Controller() {
	super();
	//TODO : instantiate managers and deliverers
    }

}
