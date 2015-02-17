package com.timokhin.weatherforgearfit.WeatherUpdate;

import java.util.Date;

public class WeatherData {
	
	private String description;
	private int humidity;
	
	private double pressure;
	
	private double windSpeed;
	private double windDeg;
	
	private double temperature;
	private double temperatureMin;
	private double temperatureMax;
	private double temperatureDay;
	private double temperatureNight;
	private double temperatureEvening;
	private double tempteratureMorning;
	
	private String icon;
	private int actualId;
	
	private Date date;

	public String getDescription() {
		return description;
	}

	public int getHumidity() {
		return humidity;
	}

	public double getPressure() {
		return pressure;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public double getWindDeg() {
		return windDeg;
	}

	public double getTemperature() {
		return temperature;
	}

	public double getTemperatureMin() {
		return temperatureMin;
	}

	public double getTemperatureMax() {
		return temperatureMax;
	}

	public double getTemperatureDay() {
		return temperatureDay;
	}

	public double getTemperatureNight() {
		return temperatureNight;
	}

	public double getTemperatureEvening() {
		return temperatureEvening;
	}

	public double getTempteratureMorning() {
		return tempteratureMorning;
	}

	public String getIcon() {
		return icon;
	}
	
	public int getActualId() {
		return actualId;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public void setWindDeg(double windDeg) {
		this.windDeg = windDeg;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public void setTemperatureMin(double temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

	public void setTemperatureMax(double temperatureMax) {
		this.temperatureMax = temperatureMax;
	}

	public void setTemperatureDay(double temperatureDay) {
		this.temperatureDay = temperatureDay;
	}

	public void setTemperatureNight(double temperatureNight) {
		this.temperatureNight = temperatureNight;
	}

	public void setTemperatureEvening(double temperatureEvening) {
		this.temperatureEvening = temperatureEvening;
	}

	public void setTemperatureMorning(double tempteratureMorning) {
		this.tempteratureMorning = tempteratureMorning;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setActualId(int i) {
		this.actualId = i;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
