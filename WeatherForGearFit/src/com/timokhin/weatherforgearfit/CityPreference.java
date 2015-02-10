package com.timokhin.weatherforgearfit;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
/**
 * Shared preference, that contains city.
 * @author timokhin
 *
 */
public class CityPreference {
     
    SharedPreferences prefs;
     
    public CityPreference(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }
    
    public String getCity(){
        return prefs.getString("city", "Kharkov, UA");        
    }
     
    void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }
}