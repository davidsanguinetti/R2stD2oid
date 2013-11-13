package net.eunainter.r2std2oid;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class RequestR2D2 {
	
	private int			_id;

	private String 		url;
	private byte		publishMethod;
	private ArrayList<NameValuePair> params;
	private JSONObject 	_json;
	private String	mStringEntity;
	
	public static final byte POST	= 0;
	public static final byte GET 	= 1;
	
	public RequestR2D2(String uri, JSONObject jobject, byte pMethod) {
		this.url = uri.trim();
		
		params = new ArrayList<NameValuePair>();
		
		if (jobject == null)
			_json = new JSONObject();
		else
			_json = jobject;
		
		this.publishMethod = pMethod;
	}
	
	public RequestR2D2() {
		// TODO Auto-generated constructor stub
	}

	public boolean addParValue(String parameter, String value) {
		return params.add(new BasicNameValuePair(parameter.trim(), value.trim()));
	}
	
/*	public boolean addParValue(String parameter, JSONObject value) {
		try {
		    this._json.put(parameter.trim(), value);
		} catch (JSONException e) {
		    Log.e("R2STD2OID", "JSONException: " + e);
		    return false;
		}
		
		return true;
	}*/

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public JSONObject getJson() {
		return this._json;
	}

	public JSONObject createJson() {
		JSONObject 	json = new JSONObject();
		try {
			for (NameValuePair nvp : params)
				json.put(nvp.getName(), nvp.getValue());
		} catch (JSONException e) {
		    Log.e("R2STD2OID", "JSONException: " + e);
		    return null;
		}
		
		return json;
	}
	
	public List<NameValuePair> getNameValueJson() {
		
		return this.params;
	}

	public void setJson(JSONObject json) {
		this._json = json;
	}

	public byte getPublishMethod() {
		return publishMethod;
	}

	public void setPublishMethod(byte publishMethod) {
		this.publishMethod = publishMethod;
	}

	public int getId() {
		return _id;
	}

	public void setId(int _id) {
		this._id = _id;
	}

	public String getStringEntity() {
		return mStringEntity;
	}

	public void setStringEntity(String mStringEntity) {
		this.mStringEntity = mStringEntity;
	}
	
}
