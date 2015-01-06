package com.wake_e.services.deliverers;

/**
 * @brief A functionality deliverer
 * @author Wake-E team
 * @param <T>
 */
public abstract class FunctionnalitiesDeliverer<T> {
    /**
     * @brief deliver the functionality
     * @return the delivery
     */
    public abstract T deliver();
    
    
    private static FunctionnalitiesDeliverer<?> deliverer;
    

    public static FunctionnalitiesDeliverer<?> getInstance(){
	return FunctionnalitiesDeliverer.deliverer;
    }
}
