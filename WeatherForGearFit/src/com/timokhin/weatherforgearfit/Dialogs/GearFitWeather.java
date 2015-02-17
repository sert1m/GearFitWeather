package com.timokhin.weatherforgearfit.Dialogs;

import android.content.Context;
import android.graphics.Bitmap;

import com.samsung.android.sdk.cup.ScupDialog;
import com.samsung.android.sdk.cup.ScupListBox;
import com.samsung.android.sdk.cup.ScupListBox.ItemClickListener;
import com.timokhin.weatherforgearfit.R;
import com.timokhin.weatherforgearfit.WeatherActivity;
import com.timokhin.weatherforgearfit.WeatherDataConsumer;
import com.timokhin.weatherforgearfit.WeatherUpdate.CurrentWeatherData;
import com.timokhin.weatherforgearfit.WeatherUpdate.WeatherData;
import com.timokhin.weatherforgearfit.WeatherUpdate.WeatherManager;

/**
 * Widget on wearable device. Contains ListBox with one element to display
 * weather icon, temperature, humid and pressure.
 * @author timokhin
 *
 */
public class GearFitWeather extends ScupDialog implements WeatherDataConsumer {

	private WeatherActivity activity;
	private ScupListBox descr;
	
	private CurrentWeatherData data;
	
	public GearFitWeather(Context activity) {
		super(activity);
		this.activity = (WeatherActivity) activity;
	}
	
	@Override
	protected void onCreate() {
		super.onCreate();
		setTitle("Weather for Gear Fit!");
		setTitleTextColor(0xffff6600);
		
		descr = new ScupListBox(this);
		descr.setItemMainTextSize(8);
		descr.setItemSubTextSize(5);
		descr.addItem(0, "");
		descr.setItemClickListener(new ItemClickListener() {		
			@Override
			public void onClick(ScupListBox list, int id, int groupId, boolean buttonState) {
				if (id == 0)
					activity.updateByCity();
			}
		});	
		descr.show();
		
		setBackEnabled(true);
		setBackPressedListener(new BackPressedListener() {
            @Override
            public void onBackPressed(ScupDialog arg0) {
                finish();
            }
        });
		renderWeather();
	}
	@Override
	public void renderWeather() {
		if (data.getLocation() == null)	{
			showToast(activity.getString(R.string.place_not_found), ScupDialog.TOAST_DURATION_SHORT);
			return;
		}
		// Set new Data
		setTitle(data.getLocation());
		WeatherData d = data.getData();
		descr.setItemMainText(0, d.getDescription());
        StringBuilder builder = new StringBuilder();
        builder.append(d.getTemperature());
        builder.append("℃ ");
        builder.append(d.getHumidity());
        builder.append("% ");
        builder.append(d.getPressure());
        builder.append(" hPa");
		descr.setItemSubText(0, builder.toString());
		
		setIcon(d.getIcon());
		// Update Dialog for displaying a new weather data
		update();		
	}
	@Override
	public void setCurrentWeatherData(CurrentWeatherData data) {
		this.data = data;
	}
	private void setIcon(String icon) {
		Bitmap image = WeatherManager.getInstance().getWeatherIcon(icon);
		descr.setItemIcon(0, Bitmap.createScaledBitmap(image, 90 , 90, true));
	}
}
