package com.wake_e.model;

import java.util.Date;
import java.util.UUID;

/**
 * @author Wake-E team
 * @brief Event of an agenda
 */

public class Event {
    // event's id
    private UUID id;

    // event's name
    private String name;

    // event's date
    private Date date;

    // event's location
    private Location location;

    @SuppressWarnings("unused")
    private Event() {
	super();
    }

    /**
     * @param id
     * @param name
     * @param date
     * @param location
     */
    public Event(UUID id, String name, Date date, Location location) {
	this.id = id;
	this.name = name;
	this.date = date;
	this.location = location;

    }

    /**
     * @return the location
     */
    public Location getLocation() {
	return location;
    }

    /**
     * @return the date
     */
    public Date getDate() {
	return date;
    }

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @return the id
     */
    public UUID getId() {
	return id;
    }

}
