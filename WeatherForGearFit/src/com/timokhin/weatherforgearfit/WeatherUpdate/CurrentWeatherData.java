package com.timokhin.weatherforgearfit.WeatherUpdate;

import java.text.DateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.timokhin.weatherforgearfit.util.JSONParsable;

public class CurrentWeatherData implements JSONParsable{
	
	private WeatherData data = new WeatherData();
	
	protected String location;
	protected String updatedOn;
	
	protected long sunrise;
	protected long sunset;
	
	@Override
	public void parse(JSONObject json) throws JSONException {
		
		location = json.getString("name") + ", " +json.getJSONObject("sys").getString("country");
		
		DateFormat df = DateFormat.getDateTimeInstance();
		updatedOn = df.format(new Date(json.getLong("dt")*1000));
		
		sunrise = json.getJSONObject("sys").getLong("sunrise") * 1000;
		sunset =  json.getJSONObject("sys").getLong("sunset") * 1000;
		
		JSONObject details = json.getJSONArray("weather").getJSONObject(0);
		JSONObject main = json.getJSONObject("main");
		JSONObject wind = json.getJSONObject("wind");
		 
		data.setDescription(details.getString("description"));
		data.setIcon(details.getString("icon"));
		data.setActualId(details.getInt("id"));
		data.setHumidity(main.getInt("humidity"));
		data.setPressure(main.getDouble("pressure"));
		data.setTemperature(main.getDouble("temp"));
		data.setTemperatureMax(main.getDouble("temp_max"));
		data.setTemperatureMin(main.getDouble("temp_min"));
		data.setWindSpeed(wind.getDouble("speed"));
		data.setWindDeg(wind.getDouble("deg"));
		data.setDate(new Date(json.getLong("dt")*1000));
			
	}

	public WeatherData getData() {
		return data;
	}

	public String getLocation() {
		return location;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public long getSunrise() {
		return sunrise;
	}

	public long getSunset() {
		return sunset;
	}
}
