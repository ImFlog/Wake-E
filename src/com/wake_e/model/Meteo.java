package com.wake_e.model;


/**
 * @brief Today's meteo
 * @author Wake-E team
 */
public class Meteo {
    
    //meteo's type (sunny, cloudy, rainy...)
    private String type;

    //meteo's temperature
    private int temperature;

    //meteo's humidity
    private double humidite;

    //meteo's wind strength
    private double windStrength;

    
    private Meteo() {
	super();
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
    public int getTemperature() {
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

}
