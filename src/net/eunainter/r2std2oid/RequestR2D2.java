package net.eunainter.r2std2oid;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class RequestR2D2 {

	private String 		url;
	private JSONObject 	json;
	private byte		publishMethod;
	
	public static final byte POST	= 0;
	public static final byte GET 	= 1;
	
	public RequestR2D2(String uri, JSONObject jobject, byte pMethod) {
		this.url = uri;
		if (jobject == null)
			json = new JSONObject();
		else
			json = jobject;
		
		this.publishMethod = publishMethod;
	}
	
	public boolean addParValue(String parameter, String value) {
		try {
		    this.json.put(parameter, value);
		} catch (JSONException e) {
		    Log.e("R2STD2OID", "JSONException: " + e);
		    return false;
		}
		
		return true;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public byte getPublishMethod() {
		return publishMethod;
	}

	public void setPublishMethod(byte publishMethod) {
		this.publishMethod = publishMethod;
	}
	
}
