package net.eunainter.r2std2oid;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class R2stD2oid extends AsyncTask<RequestR2D2, Void, String> {
	HandleObservers observers;

	final String TAG = "R2stD2oid";


	public R2stD2oid() {
		observers = new HandleObservers();
	}

	public void addObserver(RestObserver rObserver) {
		this.observers.addObserver(rObserver);
	}

	@Override
	protected String doInBackground(RequestR2D2... requests) {

		try {
			return downloadUrl(requests[0]);
		} catch (IOException e) {
			return "Error processing request";
		}
	}

	// onPostExecute displays the results of the AsyncTask.
	@Override
	protected void onPostExecute(String result) {
		//        textView.setText(result);
		System.out.println("El result: " + result);
		//    	_window.showMessage(result);
		this.observers.notifyObservers(result);
	}

	private String downloadUrl(RequestR2D2 myRequest) throws IOException {
		InputStream is = null;
		// Only display the first 500 characters of the retrieved
		// web page content.
		String result ="";
		int len = 33000;

		try {			
			HttpPost httpPost = new HttpPost(myRequest.getUrl());
			DefaultHttpClient httpClient = new DefaultHttpClient();
			BasicCookieStore cookieStore = new BasicCookieStore();
			httpClient.setCookieStore(cookieStore);



			JSONObject jsonObj = myRequest.getJson();

			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			postParams.add(new BasicNameValuePair("json", jsonObj.toString()));
			HttpGet httpGet = null;
			try {
				String contentType = "application/json";

				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);
				entity.setContentEncoding(HTTP.UTF_8);
				entity.setContentType(contentType);
				httpPost.setEntity(entity);

				httpPost.setHeader("Content-Type",contentType);
				httpPost.setHeader("Accept", contentType);
			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, "UnsupportedEncodingException: " + e);
			}

			try {
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();

				if (httpEntity != null) {
					InputStream ist = httpEntity.getContent();
					result = readIt(ist);
					Log.i(TAG, "Result: " + result);
				}
			} catch (ClientProtocolException e) {
				Log.e(TAG, "ClientProtocolException: " + e);
			} catch (IOException e) {
				Log.e(TAG, "IOException: " + e);
			}

			return result;

		} finally {
			if (is != null) {
				is.close();
			} 
		}
	}

	// Reads an InputStream and converts it to a String.
	public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");        
		char[] buffer = new char[33000];
		reader.read(buffer);
		return new String(buffer);
	}
}
