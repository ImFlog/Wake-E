package com.wake_e.tools;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wake_e.model.Meteo;

/*
 * Copyright (C) 2013 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class JSONWeatherParser {

	public static Meteo getWeather(String data) throws JSONException  {

		// We create out JSONObject from the data
		JSONObject jObj = new JSONObject(data);
		
		
		JSONObject coordObj = getObject("coord", jObj);
		getFloat("lat", coordObj);
		getFloat("lon", coordObj);
		
		JSONObject sysObj = getObject("sys", jObj);
		getString("country", sysObj);
		getInt("sunrise", sysObj);
		getInt("sunset", sysObj);
		String city = getString("name", jObj);
		
		// We get weather info (This is an array)
		JSONArray jArr = jObj.getJSONArray("weather");
		
		// We use only the first value
		JSONObject JSONWeather = jArr.getJSONObject(0);
		getInt("id", JSONWeather);
		getString("description", JSONWeather);
		getString("main", JSONWeather);
		String icon = getString("icon", JSONWeather);
		
		JSONObject mainObj = getObject("main", jObj);
		int humidity = getInt("humidity", mainObj);
		getInt("pressure", mainObj);
		getFloat("temp_max", mainObj);
		getFloat("temp_min", mainObj);
		float temp = (float) (getFloat("temp", mainObj) -  273.15);
		
		// Wind
		JSONObject wObj = getObject("wind", jObj);
		float wind = getFloat("speed", wObj);
		getFloat("deg", wObj);
		
		// Clouds
		JSONObject cObj = getObject("clouds", jObj);
		getInt("all", cObj);
		
		// We download the icon to show
		
		
		return new Meteo(city, icon, humidity, temp, wind);
	}
	
	
	private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
		JSONObject subObj = jObj.getJSONObject(tagName);
		return subObj;
	}
	
	private static String getString(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getString(tagName);
	}

	private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
		return (float) jObj.getDouble(tagName);
	}
	
	private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getInt(tagName);
	}
	
}