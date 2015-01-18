package com.wake_e.model;

import java.util.UUID;

import com.wake_e.utils.Point;

/**
 * @brief A location
 * @author Wake-E team
 *
 */
public class Location {
	// location's name
	private String name;

	// location's coordinates
	private Point gps;

	// location's complete address
	private String address;

	// location's city
	private String city;

	// location's postal code
	private String cp;

	// location's country
	private String country;

	// location's address line
	private String address_line;

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
	public Location(String name, Point gps, String address, String city, String cp, String country, String address_line) {
		this.name = name;
		this.gps = gps;
		this.address = address;
		this.city = city;
		this.cp = cp;
		this.country = country;
		this.address_line = address_line;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @brief get the location's name
	 * @return the location's name
	 */

	public String getName() {
		return this.name;
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

	/**
	 * @brief get the location's city
	 * @return the location's city
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * @brief get the location's cp
	 * @return the location's cp
	 */
	public String getCP() {
		return this.cp;
	}

	/**
	 * @brief get the location's address line
	 * @return the location's address line
	 */
	public String getAddressLine() {
		return this.address_line;
	}


}
