package com.wake_e.services.managers;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;

import com.wake_e.model.Location;
import com.wake_e.model.sqlite.WakeEDBHelper;
import com.wake_e.utils.Point;

/**
 * @brief Management of all locations
 * @author Wake-E team
 */

public class LocationsManager {
    //Locations
    private Set<Location> locations;

    //tools
    private Geocoder geocoder;
    private LocationManager locationManager;



    /**
     * 
     */
    public LocationsManager(Context context, WakeEDBHelper db) {
	super();
	this.locations = new TreeSet<Location>();
	this.geocoder = new Geocoder(context);
	this.loadLocations(db);
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

    /**
     * @brief create a new Location
     * @param address
     * @return a new address or null
     * @throws IOException
     */
    public Location createLocation(String address, WakeEDBHelper db) throws IOException {

	//Get addresses
	List<Address> addresses = this.geocoder.getFromLocationName(address, 1);
	Location l;
	//If we got an address
	if(addresses.size() > 0) {
	    double latitude= addresses.get(0).getLatitude();
	    double longitude= addresses.get(0).getLongitude();
	    Point p = new Point(latitude, longitude);

	    String city = addresses.get(0).getLocality();
	    String cp = addresses.get(0).getPostalCode();
	    
	    String address_line = addresses.get(0).getAddressLine(0);
	    
		    //If this Location already exists
		    if((l=this.getLocation(p)) == null){
			l = new Location(p, address, city, cp, address_line);
			this.addLocation(l);
		    } else {
			db.createLocation(l);
		    }
	    return l;
	}
	return null;
    }

    /**
     * @brief get a Location from a Point
     * @param p a point
     * @return a Location or null
     */
    public Location getLocation(Point p) {
	for(Location l : this.locations){
	    if(l.getGps().equals(p)){
		return l;
	    }
	}
	return null;
    }

    private void loadLocations(WakeEDBHelper db) {
	this.locations.addAll(db.getLocations());
    }

}
