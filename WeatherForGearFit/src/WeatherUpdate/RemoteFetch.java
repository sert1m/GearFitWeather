package WeatherUpdate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
 



import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
 




import com.timokhin.weatherforgearfit.R;



import android.content.Context;
/**
 * Class that recieves data from server.
 * @author timokhin
 *
 */
public class RemoteFetch {
 
    private static final String OPEN_WEATHER_MAP_API_BY_CITY = 
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String OPEN_WEATHER_MAP_API_BY_LOCATION = 
    		"http://api.openweathermap.org/data/2.5/weather?lat=%.6f&lon=%.6f";
    /**
     * 
     * @param context
     * @param city city name for getting forecast
     * @return null if no data or error occurred, or JSON object with new data
     */
    public static JSONObject getJSON(Context context, String city){
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API_BY_CITY, city));
            
            return getJSON(context, url);
        }catch(Exception e){
            return null;
        }
    }
    /**
     * 
     * @param context
     * @param lat latitude of location
     * @param lon longitude of location
     * @return null if no data or error occurred, or JSON object with new data
     */
    public static JSONObject getJSON(Context context, double lat, double lon) {
    	try {
    		URL url = new URL(String.format(Locale.US, OPEN_WEATHER_MAP_API_BY_LOCATION, lat, lon));
            System.out.println(url);
            return getJSON(context, url);
    	} catch(Exception e){
            return null;
        }
    }
    
    private static JSONObject getJSON(Context context, URL url) throws IOException, JSONException {
    	
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setConnectTimeout(5000);
        connection.addRequestProperty("x-api-key", context.getString(R.string.open_weather_maps_app_id));
    	
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        
        StringBuffer json = new StringBuffer(1024);      
        String tmp="";
        while((tmp = reader.readLine()) != null){
            json.append(tmp).append("\n");
        }
        reader.close();
        
        JSONObject data = new JSONObject(json.toString());
        
        if(data.getInt("cod") != 200){
            return null;
        }
         
        return data;
    }
}