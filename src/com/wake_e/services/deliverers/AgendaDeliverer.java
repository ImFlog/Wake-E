package com.wake_e.services.deliverers;

import java.util.Queue;

import com.wake_e.model.Event;

/**
 * @brief The agenda deliverer
 * @author Wake-E team
 */

public class AgendaDeliverer{
    // The type of agenda mapped : local or Google ?
    //TODO : constantes données par l'utilisateur via les paramètres ?
    @SuppressWarnings("unused")
    private String type;
    
    //Today's events
    private Queue<Event> events;
    
    
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
	return events.poll();
    }

}
