package com.timokhin.weatherforgearfit.WeatherUpdate;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.timokhin.weatherforgearfit.util.JSONParsable;

public class HourWeatherForecast implements JSONParsable{
	
	private WeatherData data[];
	private String location;
	
	@Override
	public void parse(JSONObject json) throws JSONException {
		location = json.getString("name") + ", " + json.getJSONObject("sys").getString("country");
		
		JSONArray list = json.getJSONArray("list");	
		data = new WeatherData[list.length()];
		
		for (int i = 0; i < list.length(); i++)	{
			JSONObject item = list.getJSONObject(i);
			JSONObject main = item.getJSONObject("main");
			JSONObject weather = item.getJSONObject("weather");
			JSONObject wind = item.getJSONObject("wind");
			
			data[i] = new WeatherData();		
			data[i].setTemperature(main.getDouble("temp"));
			data[i].setTemperatureMax(main.getDouble("temp_max"));
			data[i].setTemperatureMin(main.getDouble("temp_min"));
			data[i].setPressure(main.getDouble("pressure"));
			data[i].setHumidity(main.getInt("humidity"));
			data[i].setDescription(weather.getString("description"));
			data[i].setIcon(weather.getString("icon"));
			data[i].setActualId(weather.getInt("id"));
			data[i].setWindSpeed(wind.getDouble("wind"));
			data[i].setWindDeg(wind.getDouble("deg"));		
			data[i].setDate(new Date(item.getLong("dt")*1000));
		}
	}

	public WeatherData[] getData() {
		return data;
	}
	
	public String getLocation() {
		return location;
	}
}
