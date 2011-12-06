package com.iCompute.tour.objects;

import java.util.Date;

import com.iCompute.tour.objects.Common.*;
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
	
	public JSONObject stopToJSON(){
		JSONObject j = new JSONObject();
		try{
			j.put("id", mID);
			j.put("title", mTitle);
			j.put("description", mDescription);
			j.put("accessible", mAccessible);
			j.put("admission", mAdmission);
			j.put("category", mCategory);
			j.put("ageAccess", mAgeAccess);
			j.put("startTime", mStartTime);
			j.put("endTime", mEndTime);
			j.put("tourID",mTourID);
		}
		catch(JSONException e){
			e.printStackTrace();		
		}
		return j;
	}
	
	
}