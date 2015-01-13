package com.wake_e.services.deliverers;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.json.JSONException;

import android.os.AsyncTask;
import android.widget.ListView;

import com.wake_e.Controller;
import com.wake_e.R;
import com.wake_e.adapt.MeteoAdapter;
import com.wake_e.fragment.station.PageMeteoFragment;
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
    public List<Meteo> deliver(PageMeteoFragment view) {
	// TODO implement me
    	this.view = view;
	    JSONWeatherTask task = new JSONWeatherTask();
	    Controller.getInstance(view.getView().getContext()).getLocations();
	    task.execute(new String[]{"Paris"});
	    meteos = new ArrayList<Meteo>();
	    return this.meteos;
    }
    
	private class JSONWeatherTask extends AsyncTask<String, Void, Meteo> {
		
		@Override
		protected Meteo doInBackground(String... params) {
			Meteo weather = new Meteo();
			String data = ( (new WeatherHttpClient()).getWeatherData(params[0]));

			try {
				weather = JSONWeatherParser.getWeather(data);
								
			} catch (JSONException e) {				
				e.printStackTrace();
			}
			return weather;
		}
		
		
	@Override
		protected void onPostExecute(Meteo weather) {
			super.onPostExecute(weather);
			view.updateView(meteos);
		}
	}

}
