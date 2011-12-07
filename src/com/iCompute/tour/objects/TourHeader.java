package com.iCompute.tour.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class TourHeader {
	
	
	public long mID=-1;
	public String mTitle;
	public String mDescription;
	public int mStopCount;
	public int mHandicapStopCount;
	public boolean isDriving;
	public boolean isDownloaded;
	
	public TourHeader(long id, String title, 
					String description, int stopCount, 
					int handicapStopCount,boolean driving, boolean downloaded){
		
		mID = id;
		mTitle = title;
		mDescription = description;
		mStopCount = stopCount;
		mHandicapStopCount = handicapStopCount;
		isDriving = driving;
		isDownloaded = downloaded;
	}
	public TourHeader(JSONObject json) throws JSONException
	{
			mID=json.getLong("id");
			mTitle=json.getString("name");
			mDescription=json.getString("description");
			mStopCount=json.getInt("stopCount");
			mHandicapStopCount=json.getInt("handicapStops");
			isDriving=json.getBoolean("isDriving");
			isDownloaded=json.getBoolean("isDownloaded");
	}
	
	public JSONObject toJSONObject()
	{
		JSONObject json=new JSONObject();
		try {
			json.put("id", mID);
			json.put("name", mTitle);
			json.put("description", mDescription);
			json.put("stopCount", mStopCount);
			json.put("handicapStops", mHandicapStopCount);
			json.put("isDriving", isDriving);
			json.put("isDownloaded", isDownloaded);
			
		} catch (JSONException e) {
			json=null;
		}
		
		return json;
	}
	

}
