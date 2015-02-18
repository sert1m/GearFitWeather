package com.timokhin.weatherforgearfit;

import java.util.Date;

import com.timokhin.weatherforgearfit.WeatherUpdate.CurrentWeatherData;
import com.timokhin.weatherforgearfit.WeatherUpdate.WeatherData;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Fragment for displaying weather data in main acticity
 * @author timokhin
 *
 */
public class WeatherFragment extends Fragment implements WeatherDataConsumer {
    Typeface weatherFont;
     
    TextView cityField;
    TextView updatedField;
    TextView detailsField;
    TextView currentTemperatureField;
    TextView weatherIcon;
    
    CurrentWeatherData data;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        cityField = (TextView)rootView.findViewById(R.id.city_field);
        updatedField = (TextView)rootView.findViewById(R.id.updated_field);
        detailsField = (TextView)rootView.findViewById(R.id.details_field);
        currentTemperatureField = (TextView)rootView.findViewById(R.id.current_temperature_field);
        weatherIcon = (TextView)rootView.findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        renderWeather();
        return rootView; 
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");   
    }
    
    public void renderWeather(){
        try {
        	WeatherData d = data.getData();
            cityField.setText(data.getLocation());
            
            StringBuilder builder = new StringBuilder();
            builder.append(d.getDescription());
            builder.append("\n");
            builder.append(d.getHumidity());
            builder.append(" %\n");
            builder.append(d.getPressure());
            builder.append(" hPa");
            detailsField.setText(builder.toString());       
            currentTemperatureField.setText(String.format("%.2f  ℃", d.getTemperature()));
            updatedField.setText("Last update: " + d.getDate());
     
            weatherIcon.setText(getIcon(d.getActualId(), data.getSunrise(), data.getSunset()));      
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * The values of weather id are taken from here:
     * http://openweathermap.org/weather-conditions
     * 
     * @param actualId id of weather
     * @param sunrise time of sunrise
     * @param sunset time of sunset
     * @return
     */
	private  String getIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch(id) {
            case 2 : icon = getActivity().getString(R.string.weather_thunder);
            	break;         
            case 3 : icon = getActivity().getString(R.string.weather_drizzle);
            	break;     
            case 5 : icon = getActivity().getString(R.string.weather_rainy);
            	break;
            case 6 : icon = getActivity().getString(R.string.weather_snowy);
            	break;
            case 7 : icon = getActivity().getString(R.string.weather_foggy);
            	break;
            case 8 : icon = getActivity().getString(R.string.weather_cloudy);
            	break;
            }
        }
        return icon;
	}
    
    public void setCurrentWeatherData(CurrentWeatherData data){
		if(data == null || data.getLocation() == null)
            Toast.makeText(getActivity(), getActivity().getString(R.string.place_not_found), Toast.LENGTH_LONG).show(); 
        else 
		    this.data = data;          
	}
}