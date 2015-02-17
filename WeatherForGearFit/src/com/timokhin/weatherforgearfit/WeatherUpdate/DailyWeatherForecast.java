package com.timokhin.weatherforgearfit.WeatherUpdate;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.timokhin.weatherforgearfit.util.JSONParsable;

public class DailyWeatherForecast implements JSONParsable{
	
	private WeatherData data[];
	private String location;
	
	@Override
	public void parse(JSONObject json) throws JSONException {
		location = json.getString("name") + ", " + json.getJSONObject("sys").getString("country");
		
		JSONArray list = json.getJSONArray("list");	
		data = new WeatherData[list.length()];
		
		for (int i = 0; i < list.length(); i++) {
			JSONObject item = list.getJSONObject(i);
			JSONObject temp = item.getJSONObject("temp");
			JSONObject weather = item.getJSONObject("weather");
			
			data[i] = new WeatherData();
			data[i].setDate(new Date(item.getLong("dt")*1000));
			data[i].setTemperature(temp.getDouble("day"));
			data[i].setTemperatureMin(temp.getDouble("min"));
			data[i].setTemperatureMax(temp.getDouble("max"));
			data[i].setTemperatureNight(temp.getDouble("night"));
			data[i].setTemperatureEvening(temp.getDouble("eve"));
			data[i].setTemperatureMorning(temp.getDouble("morn"));
			data[i].setPressure(item.getDouble("pressure"));
			data[i].setHumidity(item.getInt("humidity"));
			data[i].setDescription(weather.getString("description"));
			data[i].setIcon(weather.getString("icon"));
			data[i].setActualId(weather.getInt("id"));
		}
	}

	public WeatherData[] getData() {
		return data;
	}

	public String getLocation() {
		return location;
	}
}
