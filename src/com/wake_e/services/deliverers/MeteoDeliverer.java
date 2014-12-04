package com.wake_e.services.deliverers;

import java.util.Set;

import com.wake_e.model.Meteo;

/**
 * @brief deliver today's meteo
 * @author Wake-E team
 */

public class MeteoDeliverer extends FunctionalitiesDeliverer<Meteo> {
    //The meteos to deliver
    private Set<Meteo> meteos;

    
    /**
     * 
     */
    public MeteoDeliverer() {
	super();
    }

    /**
     * @brief deliver today's meteo
     * @return today's meteo
     */
    /*TODO - TTO :  petite incohérence dans ma conception : on ne peut pas avoir
     * plusieurs météo avec une méthode qui ne retourne qu'une météo. De même, il faut une relation
     * entre Meteo et Location
     */
    @Override
    public Meteo deliver() {
	// TODO implement me
	return null;
    }

}
