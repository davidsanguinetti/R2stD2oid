package net.eunainter.r2std2oid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class ResponseR2D2 {

	private int mCode;
	private String mMessage;
	
	
	private int _id;
	private kJsonType jsonRespType = null;
	
	
	public static enum kJsonType {
		JSON_OBJECT,
		JSON_ARRAY
	}


	public final static int STATUS_REQUEST_ERROR = 900;
	public final static int STATUS_UNSUPPORTEDENCODE = 901;
	public final static int STATUS_CLIENTPROTOCOL = 902;
	public final static int STATUS_IOEXCEPTION = 903;
	public final static int STATUS_GENERAL_ERROR = 904;
	
	public final static int STATUS_OK = 200;
	public final static int STATUS_BAD_REQUEST = 400;
	public final static int STATUS_UNAUTHORIZED = 401;
	public final static int STATUS_FORBIDDEN = 403;
	public final static int STATUS_NOTFOUND = 404;
	public final static int UNKNOWNHOST = 405;
	


	public ResponseR2D2(int mCode, String mMessage) {
		this.mCode = mCode;
		this.mMessage = mMessage;
		
		this.jsonRespType = (getResponseType());
	}


	public int getCode() {
		return mCode;
	}
	public void setmCode(int mCode) {
		this.mCode = mCode;
	}
	public String getMessage() {
		
		

		String messageSend = mMessage;



		return mMessage;
	}
	
	public JSONObject getJSONObj() {
		JSONObject jsObj;
		
		try {
			jsObj= new JSONObject(mMessage);
		} catch (JSONException e) {
			jsObj = null;
		}
		
		return jsObj;
	}
	
	public JSONArray getJSONArray() {
		JSONArray jsarray;
		
		try {
			jsarray= new JSONArray(mMessage);
			Log.i("JSONAR", "msg: " + mMessage);
		} catch (JSONException e) {
			jsarray = null;
		}
		catch(Exception e) {
			jsarray = null;
		}
		
		return jsarray;
	}
	
	public kJsonType getResponseType(){
		Object json;
		try {
			json= new JSONTokener(mMessage).nextValue();
		}
		catch(JSONException e) {
			return null;
		}
		catch(Exception e) {
			return null;
		}
		if (json instanceof JSONObject)
			return kJsonType.JSON_OBJECT;
		else if (json instanceof JSONArray)
			return kJsonType.JSON_ARRAY;
		
		return null;
	}

	public static String errorMessage(int messageCode) {
		switch (messageCode) {
		case (STATUS_GENERAL_ERROR) : {
			return "Unknown error occurred while processing request to server";
		}
		case (STATUS_REQUEST_ERROR) : {
			return "General error occurred while processing request";
		}
		case (STATUS_UNSUPPORTEDENCODE) : {
			return "Unsupported Encode";
		}
		case (STATUS_CLIENTPROTOCOL) : {
			return "Client Protocol Unsupported";
		}
		case (STATUS_IOEXCEPTION) : {
			return "General IO Exception";
		}
		
		case (STATUS_BAD_REQUEST) : {
			return "Bad Request";
		}
		case (STATUS_UNAUTHORIZED) : {
			return "Unauthorized";
		}
		case (STATUS_FORBIDDEN) : {
			return "Forbidden";
		}
		case (STATUS_NOTFOUND) : {
			return "Not found";
		}
		case (UNKNOWNHOST) :{
			return "Unknown host";
		}
		default: {
			return "";
		}
		}
	}

	public void setmMessage(String mMessage) {
		this.mMessage = mMessage;
	}


	/*
	 * Id of the request
	 */
	public int getId() {
		return _id;
	}


	public void setId(int idRequest) {
		this._id = idRequest;
	}



}
