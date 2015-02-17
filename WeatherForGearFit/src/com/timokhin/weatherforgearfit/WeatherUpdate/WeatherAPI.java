package com.timokhin.weatherforgearfit.WeatherUpdate;

import java.io.InputStream;

import org.json.JSONObject;

public interface WeatherAPI {
	
    /**
     * 
     * @param city city name for getting forecast
     * @return null if no data or error occurred, or JSON object with new data
     */
	 public JSONObject getCurrentWeather(String city);
	/**
	 * 
	 * @param lat latitude of location
	 * @param lon longitude of location
	 * @return null if no data or error occurred, or JSON object with new data
	 */
	 public JSONObject getCurrentWeather(double lat, double lon);
	 public JSONObject getHoursWeatherForecast(String city);
	 public JSONObject getHoursWeatherForecast(double lat, double lon);
	 public JSONObject getDailyWeatherForecast(String city);
	 public JSONObject getDailyWeatherForecast(double lat, double lon);
	 public InputStream getIconInputStream(String icon);
}
