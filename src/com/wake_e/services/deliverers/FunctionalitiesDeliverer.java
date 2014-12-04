package com.wake_e.services.deliverers;

/**
 * @brief A functionality deliverer
 * @author Wake-E team
 * @param <T>
 */
public abstract class FunctionalitiesDeliverer<T> {

    /**
     * 
     */
    public FunctionalitiesDeliverer() {
	super();
    }

    /**
     * @brief deliver the functionality
     * @return the delivery
     */
    public abstract T deliver();

}
