package com.iCompute.tour;

import com.iCompute.tour.Common.*;
import com.iCompute.tour.StopList;
import org.json.JSONObject;

public class Tour{
	
	private int mID;
	private int mLocalID;
	private String mTitle;
	private String mDescription;
	private Access mAccess;
	private int mRating;
	private String mTags;
	private int mNumDownloads;
	private boolean isDownloaded;
	private StopList mStops;
	private int numStops = 0;
	
	
	public Tour(String name, String description, String tags, boolean isWalkable){
		try{
			mTitle = name;
			mDescription = description;
			mTags = tags;
			if(isWalkable){
				mAccess = Access.Walk;
			}
			else{
				mAccess = Access.Drive;
			}
			mStops = new StopList();
		}
		catch(Exception e){}
		
		//mStops = s;	
	}
	
	public int getStopCount(){
		return numStops;
	}
	
	public String getTitle(){
		return mTitle;
	}
	
	public String getDescription(){
		return mDescription;
	}
	
	public String getTags(){
		return mTags;
	}
	
	public boolean isWalkable(){
		return mAccess == Access.Walk;
	}
	
	public void addStop(String name, String description, int category, int access, boolean accessible){
		//this shouldn't work, add returns boolean...
		mStops.add(new Stop(name,description,category,access,accessible));
		numStops++;
	}
	
	public void update(String name, String description, String tags, boolean isWalkable){
		mTitle = name;
		mDescription = description;
		if(isWalkable){
			mAccess = Access.Walk;
		}
		else{
			mAccess = Access.Drive;
		}
	}
	
	public JSONObject tourToJSON(){
		JSONObject j = new JSONObject();
		j.put(", value)
	}
}