package com.timokhin.weatherforgearfit;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
/**
 * Class, that parses and contains weather data.
 * @author timokhin
 *
 */
public class WeatherManager{
	
	private static WeatherManager mngr;
	
	Activity weatherActivity;
	
	private String location;
	private String description;
	private String updatedOn;
	private String humidity;
	private String pressure;
	private String temperature;
	private int actualId;
	private long sunrise;
	private long sunset;

	private WeatherManager() {
		
	}
	
	public static WeatherManager getInstance() {
		if (mngr == null)
			mngr = new WeatherManager();
		
		return mngr;
	}
	
	public synchronized void update(Context context, String city) {
		try {			
			final JSONObject json = RemoteFetch.getJSON(context, city);
			
			location = json.getString("name").toUpperCase(Locale.US) + 
					   ", " +json.getJSONObject("sys").getString("country");
			
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            
			description = details.getString("description").toUpperCase(Locale.US);
			humidity = main.getInt("humidity") + "%";
			pressure = main.getString("pressure") + " hPa";
			temperature = String.format("%.2f", main.getDouble("temp")) + " ℃";
			
            DateFormat df = DateFormat.getDateTimeInstance();
            updatedOn = df.format(new Date(json.getLong("dt")*1000));
            
            actualId = details.getInt("id");
            sunrise = json.getJSONObject("sys").getLong("sunrise") * 1000;
            sunset =  json.getJSONObject("sys").getLong("sunset") * 1000;
		} catch(Exception e) {
			Log.e("WeatherManager", "One or more fields not found in the JSON data");
        }
	}
	
	public String getLocation() {
		return location;
	}
	public String getDescription() {
		return description;
	}
	public String getUpdatedOn() {
		return updatedOn;
	}
	public String getHumidity() {
		return humidity;
	}
	public String getPressure() {
		return pressure;
	}
	public String getTemperature() {
		return temperature;
	}
	public int getActualId() {
		return actualId;
	}
	public long getSunrise() {
		return sunrise;
	}
	public long getSunset() {
		return sunset;
	}
}
