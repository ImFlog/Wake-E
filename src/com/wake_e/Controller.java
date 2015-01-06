package com.wake_e;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.wake_e.constants.WakeEConstants;
import com.wake_e.model.sqlite.WakeEDBHelper;
import com.wake_e.services.deliverers.AgendaDeliverer;
import com.wake_e.services.deliverers.FunctionnalitiesDeliverer;
import com.wake_e.services.deliverers.MailDeliverer;
import com.wake_e.services.deliverers.MeteoDeliverer;
import com.wake_e.services.managers.SlidesManager;

/**
 * @brief Singleton and main controller of the application
 * @author Wake-E team
 */
public class Controller {
    // all our deliverers
    private HashMap<String, FunctionnalitiesDeliverer<?>> functionalitiesDeliverer;
    private WakeEDBHelper db;
    private Context context;
    private SlidesManager slidesManager;
    
    private static Controller controller;
    
    /**
     * @param context le contexte de l'application
     */
    private Controller(Context context) {
	super();
	this.functionalitiesDeliverer = new HashMap<String, FunctionnalitiesDeliverer<?>>();
	this.functionalitiesDeliverer.put(WakeEConstants.AGENDA_DELIVERER,
		new AgendaDeliverer());
	this.functionalitiesDeliverer.put(WakeEConstants.MAIL_DELIVERER,
		new MailDeliverer());
	this.functionalitiesDeliverer.put(WakeEConstants.METEO_DELIVERER,
		new MeteoDeliverer());
	this.context = context;
	this.db = new WakeEDBHelper(context);
	this.slidesManager = new SlidesManager(context, db);
    }

    public static Controller getInstance(Context context) {
	if (Controller.controller == null) {
	    Controller.controller = new Controller(context);
	}
	return Controller.controller;
    }
    
    public List<Fragment> getVisibleFragments(){
	return this.slidesManager.getVisibleFragments(this.context);
    }
    
    /**
     * @brief deliver a functionnality
     * @param delivererType
     * @return
     */
    public FunctionnalitiesDeliverer<?> getFunctionnalitiesDeliverer(
	    String delivererType) {
	return this.functionalitiesDeliverer.get(delivererType);
    }
}
