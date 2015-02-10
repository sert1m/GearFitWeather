package com.timokhin.weatherforgearfit;

import android.app.Activity;
import android.content.SharedPreferences;
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
        return prefs.getString("city", "Kharkiv, Ukraine");        
    }
     
    void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }
}