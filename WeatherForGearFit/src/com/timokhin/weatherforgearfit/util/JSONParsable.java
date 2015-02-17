package com.timokhin.weatherforgearfit.util;

import org.json.JSONException;
import org.json.JSONObject;

public interface JSONParsable {
	public void parse(JSONObject json) throws JSONException;
}
