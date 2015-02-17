package com.timokhin.weatherforgearfit.WeatherUpdate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import org.json.JSONObject;

public class OpenWeatherAPI implements WeatherAPI{
	
    private static final String OPEN_WEATHER_MAP_API_BY_CITY = 
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String OPEN_WEATHER_MAP_API_BY_LOCATION = 
    		"http://api.openweathermap.org/data/2.5/weather?lat=%.6f&lon=%.6f&units=metric";  
    private static final String OPEN_WEATHER_MAP_API_ICON = 
    		"http://openweathermap.org/img/w/%s.png";
    private static final String OPEN_WEATHER_MAP_API_HOUR_FORECAST_BY_CITY = 
    		"http://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric";
    private static final String OPEN_WEATHER_MAP_API_HOUR_FORECAST_BY_LOCATION = 
    		"http://api.openweathermap.org/data/2.5/forecast?lat=%.6f&lon=%.6f&units=metric";
    private static final String OPEN_WEATHER_MAP_API_DAILY_FORECAST_BY_CITY = 
    		"http://api.openweathermap.org/data/2.5/forecast/daily?q=%s&units=metric&cnt=7";
    private static final String OPEN_WEATHER_MAP_API_DAILY_FORECAST_BY_LOCATION = 
    		"http://api.openweathermap.org/data/2.5/forecast/daily?lat=%.6f&lon=%.6f&&units=metric&cnt=7";
    
    private String apiId;
    
    public OpenWeatherAPI(String apiId) {
		this.apiId = apiId;
	}
	
    @Override
    public JSONObject getCurrentWeather(String city){
    	return getJSON(String.format(Locale.US, OPEN_WEATHER_MAP_API_BY_CITY, city));
    }
    @Override
    public JSONObject getCurrentWeather(double lat, double lon) {
    	return getJSON(String.format(Locale.US, OPEN_WEATHER_MAP_API_BY_LOCATION, lat, lon));
    }

	@Override
	public JSONObject getHoursWeatherForecast(String city) {
		return getJSON(String.format(Locale.US, OPEN_WEATHER_MAP_API_HOUR_FORECAST_BY_CITY, city));
	}

	@Override
	public JSONObject getHoursWeatherForecast(double lat, double lon) {
		return getJSON(String.format(Locale.US, OPEN_WEATHER_MAP_API_HOUR_FORECAST_BY_LOCATION, lat, lon));
	}

	@Override
	public JSONObject getDailyWeatherForecast(String city) {
		return getJSON(String.format(Locale.US, OPEN_WEATHER_MAP_API_DAILY_FORECAST_BY_CITY, city));
	}

	@Override
	public JSONObject getDailyWeatherForecast(double lat, double lon) {
		return getJSON(String.format(Locale.US, OPEN_WEATHER_MAP_API_DAILY_FORECAST_BY_LOCATION, lat, lon));
	}

	@Override
	public InputStream getIconInputStream(String icon) {
    	try {
	    	URL url = new URL(String.format(OPEN_WEATHER_MAP_API_ICON, icon));
	    	
	        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	        connection.setConnectTimeout(5000);
	        connection.addRequestProperty("x-api-key", apiId);
	        
	        return connection.getInputStream();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
	}  

    private JSONObject getJSON(String request) {
    	try {
	    	URL url = new URL(request);
	    	
	        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	        connection.setConnectTimeout(5000);
	        connection.addRequestProperty("x-api-key", apiId);
	   
	        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        
	        StringBuffer json = new StringBuffer(1024);      
	        String tmp="";
	        while((tmp = reader.readLine()) != null){
	            json.append(tmp).append("\n");
	        }
	        reader.close();
	        
	        JSONObject data = new JSONObject(json.toString());
	        
	        if(data.getInt("cod") != 200){
	            return null;
	        }
	         
	        return data;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
}
