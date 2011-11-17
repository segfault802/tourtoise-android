package com.iCompute.tour.backend;

import android.util.Log;
import android.util.Base64;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONObject;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.TimeZone;



public class JSONManager{
	
	private final String PUBLIC_KEY = "JMoEudX2ESn5ZNiUcMbFd25ynBErffCF7l4ezRWRe959PENv6XVYNckiImF7P34Q";
	private final String PRIVATE_KEY = "kT8ME0kDJaxhfjgjd2CAQvBSkBB7WzDYQSNgVQI491YeW0RLUT1gTB8WDOhgCvjR";
	private final String USER = "primehunter326@gmail.com";
	private final String PASS = "chupacabra";
	
	private final String BASE_URL = "https://www.eebsy.com/api/tour/";

	private JSONObject mObject;
	private ArrayList<NameValuePair> mHeaders;
	
	private final String AUTH_STRING = "JMoEudX2ESn5ZNiUcMbFd25ynBErffCF7l4ezRWRe959PENv6XVYNckiImF7P34Q:MDM1YjcwZDRmZTU1Y2FiNjk0MzJiNDllZjFiOTVkNGExYzQ3YTllYg==:bGlmZWlzbXVzaWM0MzRAZ21haWwuY29t:cGFzc3dvcmQ=";
	private String mDateString;
	
	public JSONManager(){
		mHeaders = new ArrayList<NameValuePair>();
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		mDateString  = new StringBuilder(format.format(now)).toString();
	}
	
	public void fetchToursList(){
		get(-1);
	}
	
	public void fetchTourById(int id) {
		get(id);
	}
	
	//modified from http://stackoverflow.com/questions/4457492/simple-http-example-in-android
	private void get(int id){
		String url,authString;
		HttpClient client = new DefaultHttpClient();
		if(id == -1){
			url = BASE_URL;
		}
		else{
			url = BASE_URL + Integer.toString(id);
		}	
		HttpGet getRequest = new HttpGet(url);
		
		//MessageDigest digester = MessageDigest.getInstance("MD5");

		
		try{
			String privateString = SHA1(PRIVATE_KEY + "\n" + mDateString);
			privateString = Base64.encodeToString(privateString.getBytes(),0);
			String user = Base64.encodeToString(USER.getBytes(),0);
			String pass = Base64.encodeToString(PASS.getBytes(),0);
			authString = PUBLIC_KEY + ":" + privateString + ":" + user + ":" + pass;
		}
		catch (Exception e){
			e.printStackTrace();
			Log.i("Oh noes","Threw an exception");
			authString = "foo";
		}

		
		//String privateString = 
		
		
		
		//add required headers
		Log.i("Key: ", authString);
		Log.i("Date: " ,mDateString);
		mHeaders.add(new BasicNameValuePair("Authorization", authString));
		mHeaders.add(new BasicNameValuePair("Date", mDateString));
		int i;
		for (i=0;i<mHeaders.size();i++){
			getRequest.addHeader(mHeaders.get(i).getName(), mHeaders.get(i).getValue());
		}
		//getRequest.setHeader("Date: ", mDateString);
		Log.i("Request: ",getRequest.toString());
		
		HttpResponse response;
	    try {
	        response = client.execute(getRequest);
	        // Examine the response status
	        Log.i("Response: ",response.getStatusLine().toString());

	        // Get hold of the response entity
	        HttpEntity entity = response.getEntity();
	        // If the response does not enclose an entity, there is no need
	        // to worry about connection release

	        if (entity != null) {

	            // A Simple JSON Response Read
	            InputStream instream = entity.getContent();
	            String result= convertStreamToString(instream);
	            Log.i("Result: ",result);
	            // now you have the string representation of the HTML request
	            instream.close();
	            mObject = new JSONObject(result);
	        }


	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	    // NoSuchAlgorithmException, UnsupportedEncodingException
	}
	
	
	//from http://stackoverflow.com/questions/4457492/simple-http-example-in-android
    private String convertStreamToString(InputStream is) {
	    /*
	     * To convert the InputStream to String we use the BufferedReader.readLine()
	     * method. We iterate until the BufferedReader return null which means
	     * there's no more data to read. Each line will appended to a StringBuilder
	     * and returned as String.
	     */
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } 
	    catch (IOException e) {
	        e.printStackTrace();
	    } 
	    finally {
	        try {
	            is.close();
	        } 
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
    
    //borrowed from http://stackoverflow.com/questions/5980658/how-to-sha1-hash-a-string-in-android
    public static String SHA1(String text) 
    throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
	    MessageDigest md;
	    md = MessageDigest.getInstance("SHA-1");
	    byte[] sha1hash = new byte[40];
	    md.update(text.getBytes("iso-8859-1"), 0, text.length());
	    sha1hash = md.digest();
	    return convertToHex(sha1hash);
    } 
    
    
    private static String convertToHex(byte[] data) { 
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    }



}
