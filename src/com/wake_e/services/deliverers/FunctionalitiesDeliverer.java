package com.wake_e.services.deliverers;

/**
 * @brief A functionality deliverer
 * @author Wake-E team
 * @param <T>
 */
public interface FunctionalitiesDeliverer<T> {


    /**
     * @brief deliver the functionality
     * @return the delivery
     */
    public abstract T deliver();

}
