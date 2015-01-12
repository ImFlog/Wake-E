package com.wake_e.utils;

import com.wake_e.constants.WakeEConstants;

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
    
    @Override
    public String toString(){
	return this.toSQLite();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	long temp;
	temp = Double.doubleToLongBits(latitude);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(longitude);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	return result;
    }


    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Point other = (Point) obj;
	if (Double.doubleToLongBits(latitude) != Double
		.doubleToLongBits(other.latitude))
	    return false;
	if (Double.doubleToLongBits(longitude) != Double
		.doubleToLongBits(other.longitude))
	    return false;
	return true;
    }

    
    /**
     * @brief get a point from a string : lattitude;longitude
     * @param str
     * @return a point or null
     */
    public static Point pointFromString(String str){
	return PointParser.strToPoint(str);
    }

    /**
     * @brief parse a string into a point
     * @author Wake-E teamnugetchar
     */
    private static class PointParser{
	
	/**
	 * @brief convert a string to a point : lattitude;longitude
	 * @param str the string
	 * @return a point or null
	 */
	public static Point strToPoint(String str){
	    int count = str.length() - str.replace(WakeEConstants.POINT_SPLIT_CHARACTER, "").length();
	    if(count != 1){
		return null;
	    }
	    String[] parts = str.split(WakeEConstants.POINT_SPLIT_CHARACTER);
	    
	    double lattitude;
	    double longitude;
	    Point p = null;
	    try{
		lattitude = Double.parseDouble(parts[0]);
		longitude = Double.parseDouble(parts[1]);
		p = new Point(lattitude, longitude);	
	    }catch(Exception e){
	    }
	    
	    return p;
	}
    }

}
