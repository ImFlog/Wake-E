package com.wake_e.services.deliverers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONException;

import android.os.AsyncTask;
import android.view.View;

import com.wake_e.Controller;
import com.wake_e.fragment.station.PageMeteoFragment;
import com.wake_e.model.Location;
import com.wake_e.model.Meteo;
import com.wake_e.tools.JSONWeatherParser;
import com.wake_e.tools.WeatherHttpClient;

/**
 * @brief deliver today's meteo
 * @author Wake-E team
 */

public class MeteoDeliverer{
    //The meteos to deliver
    private List<Meteo> meteos;
    private PageMeteoFragment view;
    
    /**
     * 
     */
    public MeteoDeliverer() {
	super();
    }
    
    /**
     * @brief deliver today's meteo
     * @return today's meteo
     */
    public void deliver(PageMeteoFragment view) {
	// TODO implement me
    	this.view = view;
    	meteos = new ArrayList<Meteo>();
	    JSONWeatherTask task = new JSONWeatherTask();
	    List<Location> l = Controller.getInstance(Controller.getContext()).getLocations();
	    Iterator<Location> iterator = l.iterator();
	    while(iterator.hasNext()) {
	        Location setElement = iterator.next();
	        task.execute(new String[]{setElement.getCity()});
	    }
    }
    
	private class JSONWeatherTask extends AsyncTask<String, Void, Meteo> {
		
		@Override
		protected Meteo doInBackground(String... params) {
			Meteo weather = new Meteo();
			String data = ( (new WeatherHttpClient()).getWeatherData(params[0]));

			try {
				if (data != null)
					weather = JSONWeatherParser.getWeather(data);
				else
					weather = null;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return weather;
		}

	@Override
		protected void onPostExecute(Meteo weather) {
			super.onPostExecute(weather);
			if (weather != null)
				meteos.add(weather);
			view.updateView(meteos);
		}
	}

}
