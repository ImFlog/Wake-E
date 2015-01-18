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
		task.execute(l);
	}

	private class JSONWeatherTask extends AsyncTask<List<Location>, Void, List<Meteo>> {

		@Override
		protected List<Meteo> doInBackground(List<Location>... params) {
			List<Meteo> weatherList = new ArrayList<Meteo>();
			for (Location loc: params[0]) {
				Meteo weather = new Meteo();
				String data = ( (new WeatherHttpClient()).getWeatherData(loc.getCity()));

				try {
					if (data != null) {
						weather = JSONWeatherParser.getWeather(data);
						weatherList.add(weather);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return weatherList;
		}

		@Override
		protected void onPostExecute(List<Meteo> weatherList) {
			super.onPostExecute(weatherList);
			meteos.clear();
			meteos.addAll(weatherList);
			view.updateView(meteos);
		}
	}

}
