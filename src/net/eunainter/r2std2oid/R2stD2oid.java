package net.eunainter.r2std2oid;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class R2stD2oid extends AsyncTask<RequestR2D2, Void, ResponseR2D2> {
	HandleObservers observers;

	final String TAG = "R2stD2oid";

	private Object mLock = new Object();

	private static DefaultHttpClient httpClient;
	private static CookieStore mCookie = null;
	private static HttpContext localContext;
	


	public R2stD2oid() {
		observers = new HandleObservers();
	}

	public void addObserver(RestObserver rObserver) {
		this.observers.addObserver(rObserver);
	}

	@Override
	protected ResponseR2D2 doInBackground(RequestR2D2... requests) {

		try {
			return downloadUrl(requests[0]);
		} catch (IOException e) {
			return  new ResponseR2D2(ResponseR2D2.STATUS_BAD_REQUEST, "Error processing request");
		}
	}

	// onPostExecute displays the results of the AsyncTask.
	@Override
	protected void onPostExecute(ResponseR2D2 result) {
		//        textView.setText(result);
//		System.out.println("El result: " + result);
		this.observers.notifyObservers(result);
	}

	public ResponseR2D2 downloadUrl(RequestR2D2 myRequest) throws IOException {
		observers.progressObservers();
		
		InputStream is = null;
		// Only display the first 500 characters of the retrieved
		// web page content.
		String result ="";
		int len = 33000;
		
		int status = 200;
		String messageSend = "";

		try {			
			HttpPost httpPost = new HttpPost(myRequest.getUrl());
			
			JSONObject jsonObj = myRequest.getJson();

			try {
				String contentType = "application/json";
				StringEntity stentt = new StringEntity(jsonObj.toString());
				stentt.setContentEncoding(HTTP.UTF_8);
				stentt.setContentType(contentType);
				httpPost.setEntity(stentt);

				httpPost.setHeader("Content-Type",contentType);
				httpPost.setHeader("Accept", contentType);
			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, "UnsupportedEncodingException: " + e);
				status = ResponseR2D2.STATUS_UNSUPPORTEDENCODE;
				messageSend = e.getLocalizedMessage();		
			}

			try {
				HttpResponse httpResponse = R2stD2oid.getHttpClient().execute(httpPost, localContext);
				HttpEntity httpEntity = httpResponse.getEntity();

				if (httpEntity != null) {
					InputStream ist = httpEntity.getContent();

					status = 200;
					messageSend = readIt(ist);
					
//					Log.i(TAG, "Result: " + result);
				}
			} catch (ClientProtocolException e) {
				Log.e(TAG, "ClientProtocolException: " + e);
				status = ResponseR2D2.STATUS_CLIENTPROTOCOL;
				messageSend = e.getLocalizedMessage();
			} catch (IOException e) {
				Log.e(TAG, "IOException: " + e);
				
				status = ResponseR2D2.STATUS_IOEXCEPTION;
				messageSend = e.getLocalizedMessage();
			}
			catch (Exception e) {
				Log.e(TAG, "General Exception: " + e);
				
				status = ResponseR2D2.STATUS_GENERAL_ERROR;
				messageSend = e.getLocalizedMessage();			
			}

			return  new ResponseR2D2(status, messageSend);

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
	
	public static DefaultHttpClient getHttpClient() {
		if (httpClient == null) {
			httpClient = new DefaultHttpClient();
			if (mCookie == null) {

				// Create local HTTP context
				localContext = new BasicHttpContext();
				// Bind custom cookie store to the local context
				localContext.setAttribute(ClientContext.COOKIE_STORE, mCookie);


				mCookie = httpClient.getCookieStore();

				httpClient.setCookieStore(mCookie);
			}
		}
		return httpClient;
	}
}
