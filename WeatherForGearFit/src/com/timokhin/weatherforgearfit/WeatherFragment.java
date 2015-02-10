package com.timokhin.weatherforgearfit;

import java.util.Date;

import WeatherUpdate.WeatherDataConsumer;
import WeatherUpdate.WeatherManager;
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
    
    WeatherManager weatherMngr;
 
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
        weatherMngr = WeatherManager.getInstance();
        updateWeather();
        return rootView; 
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        System.out.println("OnCreate");
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");   
        
    }
    
    private void renderWeather(){
        try {
            cityField.setText(weatherMngr.getLocation());            
            detailsField.setText(weatherMngr.getDescription() + "\n" + 
            					 weatherMngr.getHumidity() + "\n" + 
            					 weatherMngr.getPressure());
             
            currentTemperatureField.setText(weatherMngr.getTemperature());
            updatedField.setText("Last update: " + weatherMngr.getUpdatedOn());
     
            weatherIcon.setText(getIcon(weatherMngr.getActualId(), weatherMngr.getSunrise(), weatherMngr.getSunset()));      
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
    
    public void updateWeather(){
		if(weatherMngr == null || weatherMngr.getLocation() == null)
             Toast.makeText(getActivity(), getActivity().getString(R.string.place_not_found), Toast.LENGTH_LONG).show(); 
        else 
		     renderWeather();          
	}
}