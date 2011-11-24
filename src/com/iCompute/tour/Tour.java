package com.iCompute.tour;

import com.iCompute.tour.Common.*;
import com.iCompute.tour.StopList;

public class Tour{
	
	private int mID;
	private String mTitle;
	private String mDescription;
	private Access mAccess;
	private int mRating;
	private String mTags;
	private int mNumDownloads;
	private StopList mStops;
	
	public Tour(String name, String description, String tags, boolean isWalkable){
		try{
			mTitle = name;
			mDescription = description;
			
			if(isWalkable){
				mAccess = Access.Walk;
			}
			else{
				mAccess = Access.Drive;
			}
		}
		catch(Exception e){}
		
		//mStops = s;	
	}
	
	
	public String getTitle(){
		return mTitle;
	}
	
	public String getDescription(){
		return mDescription;
	}
	
	
	public void addStop(Stop s){
		mStops.add(s);
	}
	
	public void update(String name, String description, String tags, Access a){
		mTitle = name;
		mDescription = description;
		mAccess = a;
	}
}