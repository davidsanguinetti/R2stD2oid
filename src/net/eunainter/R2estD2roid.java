package net.eunainter;
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
import eu.gtinformatica.dador.LoginActivity;

public class R2stD2oid extends AsyncTask<String, Void, String> {
	LoginActivity _window;
	
	
	public FetchWebpage(LoginActivity window) {
		this._window = window;
	}
    @Override
    protected String doInBackground(String... urls) {
          
        // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
//        textView.setText(result);
    	System.out.println("El result: " + result);
    	_window.showMessage(result);
   }
    
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        String result ="";
        int len = 33000;
            
        try {
			String paramUsername = "david.sanguinetti@gtinformatica.eu";
			String paramPassword = "estaumlindodia";
			
			HttpPost httpPost = new HttpPost(myurl);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			BasicCookieStore cookieStore = new BasicCookieStore();
			httpClient.setCookieStore(cookieStore);
			
			String TAG = "R2estD2roid";
            
			JSONObject jsonObj = new JSONObject();

			try {
			    jsonObj.put("username", paramUsername);
			    jsonObj.put("password", paramPassword);
			} catch (JSONException e) {
			    Log.e(TAG, "JSONException: " + e);
			}

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
			
			
//            URL url = new URL(myurl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////            conn.setReadTimeout(10000 /* milliseconds */);
////            conn.setConnectTimeout(15000 /* milliseconds */);
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            
//            
//            conn.addRequestProperty("username", paramUsername);
//            conn.addRequestProperty("password", paramPassword);
//            
//            // Starts the query
//            conn.connect();
//            int response = conn.getResponseCode();
//            Log.d("DEBUG", "The response is: " + response);
//            is = conn.getInputStream();
//
//            // Convert the InputStream into a string
//            String contentAsString = readIt(is, len);
//            return contentAsString;
            
        // Makes sure that the InputStream is closed after the app is
        // finished using it.
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
