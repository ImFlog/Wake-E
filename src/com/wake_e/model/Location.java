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

    // location's city
    private String city;

    // location's address
    private String address;

    // location's postal code
    private String postalCode;

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
    public Location(UUID id, Point gps, String city, String address, String pc) {
	this.id = id;
	this.gps = gps;
	this.city = city;
	this.address = address;
	this.postalCode = pc;
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
     * @brief get the location's city
     * @return the location's city
     */
    public String getCity() {
	return this.city;
    }

    /**
     * @brief get the location's address
     * @return the location's address
     */
    public String getAddress() {
	return this.address;
    }

    /**
     * @brief get the location's postal code
     * @return the location's postal code
     */
    public String getPostalCode() {
	return this.postalCode;
    }

}
