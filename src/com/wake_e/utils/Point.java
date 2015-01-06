package com.wake_e.utils;

/**
 * @brief A point is coordinate
 * @author Wake-E team
 */
public class Point {
    
    //point's latitude
    private double latitude;

    //point's longitude
    private double longitude;

    
    /**
     * @param latitude 
     * @param longitude 
     */
    public Point(double latitude, double longitude) {
	super();
	this.latitude = latitude;
	this.longitude = longitude;
    }


    /**
     * @brief get point's latitude
     * @return point's latitude
     */
    public double getLatitude() {
        return latitude;
    }


    /**
     * @brief get point's longitude
     * @return point's longitude
     */
    public double getLongitude() {
        return longitude;
    }


    public String toSQLite() {
	return this.latitude + ";" + this.longitude;
    }

}
