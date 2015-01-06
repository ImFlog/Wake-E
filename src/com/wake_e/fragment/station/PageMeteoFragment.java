package com.wake_e.fragment.station;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.wake_e.R;
import com.wake_e.adapt.MeteoAdapter;
import com.wake_e.model.Meteo;
import com.wake_e.tools.JSONWeatherParser;
import com.wake_e.tools.WeatherHttpClient;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class PageMeteoFragment extends Fragment {

	View v;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.station, container, false);
		if (v != null){
			String city = "Paris";

			TextView title = (TextView) v.findViewById(R.id.title_station);
			title.setText(v.getContext().getString(R.string.meteo));
			
		    JSONWeatherTask task = new JSONWeatherTask();
		    task.execute(new String[]{city});
		}
		return v;/*inflater.inflate(R.layout.station, container, false);*/
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
			if (v != null){
				List<Meteo> meteos = new ArrayList<Meteo>();
				meteos.add(weather);
				ListView gridview = (ListView) v.findViewById(R.id.content);
			    gridview.setAdapter(new MeteoAdapter(v.getContext(),meteos));
			}
		}
	}
}
