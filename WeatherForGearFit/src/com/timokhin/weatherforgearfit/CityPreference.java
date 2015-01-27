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
    
    String getCity(){
        return prefs.getString("city", "Kharkov, UA");        
    }
     
    void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }
     
}