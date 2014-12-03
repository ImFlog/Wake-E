package com.wake_e.services.managers;

import java.util.Set;
import java.util.UUID;

import com.wake_e.model.Location;

/**
 * @brief Management of all locations
 * @author Wake-E team
 */

public class LocationsManager {
    //Locations
    private Set<Location> locations;


    /**
     * 
     */
    public LocationsManager() {
	super();
    }

    /**
     * @brief add a location
     * @param location the location to add
     */
    public void addLocation(Location location) {
	this.locations.add(location);
    }


    /**
     * @brief remove a location
     * @param locationId the id of the location to remove
     */
    public void removeLocation(UUID locationId) {
	for(Location l : locations){
	    if(l.getId() == locationId){
		locations.remove(l);
		return;
	    }
	}
    }

    /**
     * @brief retrieve a location
     * @param locationId the id of the location
     * @return the location or null
     */

    public Location getLocation(UUID locationId) {
	for(Location l : locations){
	    if(l.getId() == locationId){
		return l;
	    }
	}
	return null;
    }


}
