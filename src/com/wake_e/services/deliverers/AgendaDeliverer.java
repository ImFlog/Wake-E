package com.wake_e.services.deliverers;

import java.util.List;

import com.wake_e.model.Event;

/**
 * @brief The agenda deliverer
 * @author Wake-E team
 */

public class AgendaDeliverer extends FunctionalitiesDeliverer<Event> {
    // The type of agenda mapped : local or Google ?
    //TODO : constantes données par l'utilisateur via les paramètres ?
    @SuppressWarnings("unused")
    private String type;
    
    //Today's events
    @SuppressWarnings("unused")
    private List<Event> events;


    /**
     * 
     */
    public AgendaDeliverer() {
	super();
    }

    /**
     * @brief retrieve today's events from the synchronized agenda
     */
    public void getEvents() {
    }

    /**
     * @brief deliver the next event of the day
     */
    public Event deliver() {
	/*TODO : pop the first evet of the list*/
	return null;
    }

}
