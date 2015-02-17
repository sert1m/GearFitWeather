package com.timokhin.weatherforgearfit;

import com.timokhin.weatherforgearfit.WeatherUpdate.CurrentWeatherData;

/**
 * Interface for objects, that display weather data and need updating.
 * @author timokhin
 *
 */
public interface WeatherDataConsumer {
	 void setCurrentWeatherData(CurrentWeatherData data);
	 void renderWeather();
}
