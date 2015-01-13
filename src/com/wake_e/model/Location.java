package com.wake_e.model;

import java.util.UUID;

import com.wake_e.utils.Point;

/**
 * @brief A location
 * @author Wake-E team
 *
 */
public class Location {
    // location's id
    private UUID id;

    // location's coordinates
    private Point gps;

    // location's address
    private String address;

    @SuppressWarnings("unused")
    private Location() {
	super();
    }

    /**
     * @param id
     * @param gps
     * @param city
     * @param address
     * @param pc
     */
    public Location(Point gps, String address) {
	this.id = UUID.randomUUID();
	this.gps = gps;
	this.address = address;
    }

    
    /**
     * @brief get the location's id
     * @return the location's id
     */

    public UUID getId() {
	return this.id;
    }
    
    /**
     * @brief get location's coordinates
     * @return the location's coordinates
     */
    public Point getGps() {
	return this.gps;
    }

    /**
     * @brief get the location's address
     * @return the location's address
     */
    public String getAddress() {
	return this.address;
    }


}
