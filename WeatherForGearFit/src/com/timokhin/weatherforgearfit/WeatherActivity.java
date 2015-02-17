package com.timokhin.weatherforgearfit;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.samsung.android.sdk.cup.Scup;
import com.timokhin.weatherforgearfit.Dialogs.GearFitWeather;
import com.timokhin.weatherforgearfit.WeatherUpdate.CurrentWeatherData;
import com.timokhin.weatherforgearfit.WeatherUpdate.OpenWeatherAPI;
import com.timokhin.weatherforgearfit.WeatherUpdate.WeatherManager;
/**
 * Main activity class. It displays weather on the phone, initialize 
 * SCUP (Samsung Companion User Interface Profile) and creates a widget 
 * on wearable device side.
 * @author timokhin
 *
 */
public class WeatherActivity extends Activity {
	
	// List of consumers, that need updating weather
	private List<WeatherDataConsumer> consumers;
	private WeatherManager manager = WeatherManager.getInstance();
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);  
        
        consumers = new ArrayList<WeatherDataConsumer>();
        manager.setWeatherAPI(new OpenWeatherAPI(getString(R.string.open_weather_maps_app_id)));   
        
        WeatherFragment weatherFrgmnt = new WeatherFragment();    
        consumers.add(weatherFrgmnt); 
        	
        try {
        	// For displaying data on wearable device, scup must be initialized.
        	Scup scup = new Scup();
        	scup.initialize(this);
        	// Creating a dialog on wearable device side.
	        GearFitWeather gearWeather = new GearFitWeather(this);	        
	    	consumers.add(gearWeather);   	
        }catch (Exception e){
        	Log.e("Scup", "Vendor not Supported!");
        }   
        
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, weatherFrgmnt).commit();
        }
        
        updateByCity();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId())
    	{
	    	case(R.id.change_city):
	            showInputDialog();
	            return true;
	    	case(R.id.update_by_city):
	        	updateByCity();
	        	return true;
	    	case(R.id.update_by_location):
	        	updateByCurrentLocation();
	        	return true;
    	}
        return false;
    }
        
    /**
     * Changing a city from user input
     */
    private void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change city");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeCity(input.getText().toString());
            }
        });
        builder.show();
    }
    
    public void updateByCity() {
    	try {
	    	CurrentWeatherData data = manager.getCurrentWeatherData(new CityPreference(this).getCity());
	    	for (WeatherDataConsumer c : consumers) {
	    		c.setCurrentWeatherData(data);
	    		c.renderWeather();
	    	}

    	} catch (IllegalStateException e) {
    		e.printStackTrace();
    	}	    	
    }
    
    public void updateByCurrentLocation() {
        try {
	    	LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    	String provider = locationManager.getBestProvider(new Criteria(), true);
	    	Location location = locationManager.getLastKnownLocation(provider);
	    	    	
		    final double lat = location.getLatitude();
		    final double lon = location.getLongitude();

		    CurrentWeatherData data = manager.getCurrentWeatherData(lat, lon);
	  
	    	for (WeatherDataConsumer c : consumers) {
	    		c.setCurrentWeatherData(data);
	    		c.renderWeather();
	    	}
	    	
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "No location found =(", Toast.LENGTH_SHORT).show();
		}
    }
    
    private void changeCity(String city){
    	CityPreference cityPreference =  new CityPreference(this);
    	cityPreference.setCity(city);
    	updateByCity();
    }
}
