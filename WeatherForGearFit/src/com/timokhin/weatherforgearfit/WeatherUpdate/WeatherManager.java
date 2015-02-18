package com.timokhin.weatherforgearfit.WeatherUpdate;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.timokhin.weatherforgearfit.util.JSONParsable;

/**
 * Class, that parses and contains weather data.
 * @author timokhin
 *
 */
public class WeatherManager{
	private static WeatherManager manager = new WeatherManager();
	private WeatherAPI api;
	
	private enum Requests {CURRENT_BY_CITY, CURRENT_BY_LOCATION,
						   DAILY_BY_CITY, DAILY_BY_LOCATION,
						   HOURS_BY_CITY, HOURS_BY_LOCATION }
	
	private class UpdateThread extends Thread {
		
		JSONParsable data;
		Requests request;
		String city;
		double lat, lon;
		
		UpdateThread(JSONParsable data, Requests requests) {
			this.data = data;
			this.request = requests; 
		}
		
		void setLocaction(String city) {
			this.city = city;
		}
		void setLocation(double lat, double lon) {
			this.lat = lat;
			this.lon = lon;
		}
		public void run() {
			JSONObject json = null;
			
			switch (request) {
				case CURRENT_BY_CITY:
					json = api.getCurrentWeather(city);
					break;
				case CURRENT_BY_LOCATION:
					json = api.getCurrentWeather(lat, lon);
					break;
				case DAILY_BY_CITY:
					json = api.getDailyWeatherForecast(city);
					break;
				case DAILY_BY_LOCATION:
					json = api.getDailyWeatherForecast(lat, lon);
					break;
				case HOURS_BY_CITY: 
					json = api.getHoursWeatherForecast(city);
					break;
				case HOURS_BY_LOCATION:
					json = api.getHoursWeatherForecast(lat, lon);
					break;
				default:
					return;
			}
			
			try {
				data.parse(json);
			} catch (JSONException e) {
				e.printStackTrace();
				data = null;
			} catch (Exception e) {
				e.printStackTrace();
				data = null;
			}
		}
	}
	
	public static WeatherManager getInstance() {
		if (manager == null)
			manager = new WeatherManager();
		
		return manager;
	}
	
	public WeatherAPI getApi() {
		return api;
	}

	public void setWeatherAPI(WeatherAPI api) {
		this.api = api;
	}
	
	public CurrentWeatherData getCurrentWeatherData(String city) {
		try {
			UpdateThread thread = new UpdateThread(new CurrentWeatherData(), Requests.CURRENT_BY_CITY);
			thread.setLocaction(city);
			thread.start();
			thread.join();
			return (CurrentWeatherData) thread.data;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	public CurrentWeatherData getCurrentWeatherData(double lat, double lon) {
		try {
			UpdateThread thread = new UpdateThread(new CurrentWeatherData(), Requests.CURRENT_BY_LOCATION);
			thread.setLocation(lat, lon);
			thread.start();
			thread.join();
			return (CurrentWeatherData) thread.data;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	public HourWeatherForecast getHourWeatherForecast(String city) {
		try {
			UpdateThread thread = new UpdateThread(new HourWeatherForecast(), Requests.HOURS_BY_CITY);
			thread.setLocaction(city);
			thread.start();
			thread.join();
			return (HourWeatherForecast) thread.data;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	public HourWeatherForecast getHourWeatherForecast(double lat, double lon){
		try {
			UpdateThread thread = new UpdateThread(new HourWeatherForecast(), Requests.CURRENT_BY_LOCATION);
			thread.setLocation(lat, lon);
			thread.start();
			thread.join();
			return (HourWeatherForecast) thread.data;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	public DailyWeatherForecast getDailyWeatherForecast(String city) {
		try {
			UpdateThread thread = new UpdateThread(new DailyWeatherForecast(), Requests.DAILY_BY_CITY);
			thread.setLocaction(city);;
			thread.start();
			thread.join();
			return (DailyWeatherForecast) thread.data;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	public DailyWeatherForecast getDailyWeatherForecast(double lat, double lon) {
		try {
			UpdateThread thread = new UpdateThread(new DailyWeatherForecast(), Requests.DAILY_BY_CITY);
			thread.setLocation(lat, lon);
			thread.start();
			thread.join();
			return (DailyWeatherForecast) thread.data;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	public Bitmap getWeatherIcon(final String icon) {
		
		class IconThread extends Thread {
			Bitmap image;
			public void run() {			
				try {
					image = BitmapFactory.decodeStream(api.getIconInputStream(icon));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			IconThread thread = new IconThread();
			thread.start();
			thread.join();
			return thread.image;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
