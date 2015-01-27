package com.timokhin.weatherforgearfit;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.samsung.android.sdk.cup.Scup;
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
	/**
	 * Thread for updating weather data from server.
	 * @author timokhin
	 *
	 */
	private class UpdateThread extends Thread {
		public void run() {
			WeatherManager.getInstance().update(WeatherActivity.this, new CityPreference(WeatherActivity.this).getCity());
		}
	}
	
	public WeatherActivity() {
		consumers = new ArrayList<WeatherDataConsumer>();
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        
        updateWeatherManager();
        
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
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.change_city){
            showInputDialog();
        }
        else if (item.getItemId() == R.id.update) {
        	updateWeatherData();
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
    /**
     * Updating weather data
     */
    private void updateWeatherManager() {
        try {
	        Thread updateWeather = new UpdateThread();
	        updateWeather.start();
	        updateWeather.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void changeCity(String city){
    	CityPreference cityPreference =  new CityPreference(this);
    	cityPreference.setCity(city);
    	updateWeatherData();
    }
    
    public void updateWeatherData() {
    	updateWeatherManager();
    	for (WeatherDataConsumer c : consumers)
    		c.updateWeather();
    }
}
