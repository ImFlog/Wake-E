package com.wake_e.model;

import java.util.Date;

/**
 * @author Wake-E team
 * @brief Event of an agenda
 */

public class Event {
	// event's id
	private long id;

	// event's name
	private String name;

	// event's date
	private Date begin;

	// event's date
	private Date end;

	// event's location
	private String location;
	
	private String description;

	@SuppressWarnings("unused")
	private Event() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 * @param begin
	 * @param end
	 * @param location
	 */
	public Event(long id, String name, Date begin, Date end, String location, String description) {
		this.id = id;
		this.name = name;
		this.begin = begin;
		this.end = end;
		this.location = location;
		this.description = description;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the begin date
	 */
	public Date getBegin() {
		return begin;
	}
	
	/**
	 * @return the ed date
	 */
	public Date getEnd() {
		return end;
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
	public long getId() {
		return id;
	}
	
	/**
	 * @return the id
	 */
	public String getDescription() {
		return description;
	}

	public String toString() {
		return this.id+" : "+this.name+" ("+this.begin.toString()+" - "+this.end.toString()+")";
	}
}
