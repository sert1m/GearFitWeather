package Dialog;

import java.util.Date;

import WeatherUpdate.WeatherDataConsumer;
import WeatherUpdate.WeatherManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.samsung.android.sdk.cup.ScupDialog;
import com.samsung.android.sdk.cup.ScupListBox;
import com.samsung.android.sdk.cup.ScupListBox.ItemClickListener;
import com.timokhin.weatherforgearfit.R;
import com.timokhin.weatherforgearfit.WeatherActivity;

/**
 * Widget on wearable device. Contains ListBox with one element to display
 * weather icon, temperature, humid and pressure.
 * @author timokhin
 *
 */
public class GearFitWeather extends ScupDialog implements WeatherDataConsumer {

	private WeatherActivity activity;
	private WeatherManager weatherMngr;
	private ScupListBox descr;
	
	public GearFitWeather(Context activity) {
		super(activity);
		this.activity = (WeatherActivity) activity;
		weatherMngr = WeatherManager.getInstance();
	}
	
	@Override
	public void updateWeather() {
		if (weatherMngr.getLocation() == null)	{
			showToast(activity.getString(R.string.place_not_found), ScupDialog.TOAST_DURATION_SHORT);
			return;
		}
		// Set new Data
		setTitle(weatherMngr.getLocation());
	
		descr.setItemMainText(0, weatherMngr.getDescription());
		descr.setItemSubText(0, weatherMngr.getTemperature() + " " + weatherMngr.getHumidity() + " " + weatherMngr.getPressure());
		descr.setItemIcon(0, getIcon(weatherMngr.getActualId(), weatherMngr.getSunrise(), weatherMngr.getSunset()));
		// Update Dialog for displaying a new weather data
		update();		
	}
	
	@Override
	protected void onCreate() {
		super.onCreate();
		setTitle("Weather for Gear Fit!");
		setTitleTextColor(0xffff6600);
		
		descr = new ScupListBox(this);
		descr.setItemMainTextSize(6);
		descr.setItemSubTextSize(5);
		descr.addItem(0, "");
		descr.setItemClickListener(new ItemClickListener() {		
			@Override
			public void onClick(ScupListBox list, int id, int groupId, boolean buttonState) {
				if (id == 0)
					activity.updateWeatherData();
			}
		});	
		descr.show();
		
		updateWeather();
		
		setBackEnabled(true);
		setBackPressedListener(new BackPressedListener() {
            @Override
            public void onBackPressed(ScupDialog arg0) {
                finish();
            }
        });
	}
	
	private  Bitmap getIcon(int actualId, long sunrise, long sunset) {
		// There is a little code duplicate in case Gear Fit does not supports third party fonts.
		// So I need another way to display icons.
        int id = actualId / 100;
        int imageId = 0;
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
            	imageId = R.drawable.sunny;
            } else {
                imageId = R.drawable.clear_night;
            }
        } else {
            switch(id) {
            case 2 : imageId = R.drawable.thunder;
                     break;         
            case 3 : imageId = R.drawable.drizzle;
                     break;     
            case 7 : imageId = R.drawable.foggy;
                     break;
            case 8 : imageId = R.drawable.cloudy;
                     break;
            case 6 : imageId = R.drawable.snowy;
                     break;
            case 5 : imageId = R.drawable.rainy;
                     break;
            }
        }
        
        Bitmap image = BitmapFactory.decodeResource(activity.getResources(), imageId);
		image = Bitmap.createScaledBitmap(image, 90 , 90, true);
		
        return image;
	}
}
