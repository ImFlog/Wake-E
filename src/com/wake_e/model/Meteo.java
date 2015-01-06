package com.wake_e.model;

import com.wake_e.services.deliverers.MeteoDeliverer;

/**
 * @brief Today's meteo
 * @author Wake-E team
 */
public class Meteo {
    
    //meteo's type (sunny, cloudy, rainy...)
    private String type;

    //meteo's temperature
    private double temperature;

    //meteo's humidity
    private double humidite;

    //meteo's wind strength
    private double windStrength;
    
    //meteo's icon
    private String icon; 

    //meteo's city
    private String city ;
    
    public Meteo(){
    	super();
    }
    
    public Meteo(String city) {
	super();
	this.city = city;
    }

    public Meteo(String city, String icon, int humidity, float temp,
			float wind) {
		// TODO Auto-generated constructor stub
    	this.city = city;
    	this.icon = icon;
    	this.humidite = humidity;
    	this.temperature = temp;
    	this.windStrength = wind;
	}

	/**
     * @brief get the meteo's icon
     * @return an icon regarding the meteo's caracteristics
     */
    //TODO : trouver la classe qui correspondra à l'icone en android
    public int image() {
	// TODO implement me
	return 0;
    }
    
    /**
     * @return the meteo's type
     */
    public String getType() {
        return type;
    }
    
    /**
     * @return the temperature's type
     */
    public double getTemperature() {
        return temperature;
    }
    
    /**
     * @return the temperature's humidity
     */
    public double getHumidite() {
        return humidite;
    }

    /**
     * @return the temperature's wind strength
     */
    public double getWindStrength() {
        return windStrength;
    }

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
