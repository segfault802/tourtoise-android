package com.iCompute.tour.objects;

import java.util.Date;

import org.json.JSONObject;
import org.json.JSONException;

public class Stop{
	
	private long mID=-1;
	public String mTitle;
	public String mDescription;
	public boolean mAccessible=false;
	public double mAdmission=0.00;
	public int mCategory=-0;
	public int mAgeAccess=0;
	//0 G
	//1 PG
	//2 PG-13
	//3 R
	public String mStartTime;
	public String mEndTime;
	public long mTourID=-1;
	
	public int stopStatus=0;//pending, visited, skipped, next
	//TODO media pointers
	
	public Stop()
	{
		mID=new Date().getTime();
	}
	public Stop(long tourID)
	{
		mTourID=tourID;
		mID=new Date().getTime();
	}
	
	public Stop(String name, String description, int category, int access, boolean accessible){
		mTitle = name;
		mDescription = description;
		mCategory = category;
		mAgeAccess=access;
		mAccessible = accessible;
	}
	
	public long getStopID()
	{
		return mID;
	}
	
	public int getStatus(){
		return stopStatus;
	}
	
	
	public JSONObject stopToJSON(){
		JSONObject j = new JSONObject();
		JSONObject stop = new JSONObject();
		try{
			String age;
			j.put("id", mID);
			j.put("title", mTitle);
			j.put("description", mDescription);
			j.put("accessibility", mAccessible?1:0);
			j.put("admission", mAdmission);
			j.put("category", mCategory);
			switch(mAgeAccess){
			case 0:
				age = "G";
				break;
			case 1:
				age = "PG";
				break;
			case 2:
				age = "PG-13";
			case 3:
				age = "R";
				break;
			default:
				age = "G";
				break;
			}
			j.put("ageaccess", age);
			j.put("starttime", mStartTime);
			j.put("endtime", mEndTime);
			j.put("tourId",mTourID);
			stop.put("stop", j);
		}
		catch(JSONException e){
			e.printStackTrace();		
		}
		return j;
	}
	
	public void stopFromJSON(JSONObject j){
		try{
			JSONObject stop = j.getJSONObject("stop");
			mID = stop.getLong("id");
			mTitle = stop.getString("title");
			mDescription = stop.getString("description");
			mAccessible = stop.getInt("accessibility")==1?true:false;
			mAdmission = stop.getDouble("admission");
			mCategory = stop.getInt("category");
			String age = stop.getString("ageaccess");
			if(age.equals("G")){
				mAgeAccess = 0;
			}
			else if(age.equals("PG")){
				mAgeAccess = 1;
			}
			else if(age.equals("PG-13")){
				mAgeAccess = 2;
			}
			else if(age.equals("R")){
				mAgeAccess = 3;
			}
			else{
				mAgeAccess = 0;
			}
			mStartTime = stop.getString("starttime");
			mEndTime = stop.getString("endtime");
			mTourID = stop.getLong("tourId");
		}
		catch(JSONException e){
			e.printStackTrace();		
		}
	}
	public void updateStop(Stop stop) {
		//TODO update stop
	}
	
}
