package com.iCompute.tour.objects;


import org.json.JSONObject;
import org.json.JSONException;
import com.iCompute.tour.objects.Common.Access;

public class Tour{
	
	private long mID=-1;
	public String mTitle;
	public String mDescription;
	public Access mAccess;
	private int mRating=0;
	public String mTags;
	private int mNumDownloads=0;
	private boolean isDownloaded=false;
	private StopList mStops;
	private int mHandicapStops=0;
	private int mCurrentStop;
	private boolean mFinished;
	
	
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
		return mStops.size();
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
	public boolean isDownloaded()
	{
		return isDownloaded;
	}
	public long getTourID()
	{
		return mID;
	}
	public int getHandicapStopCount()
	{
		return mHandicapStops;
	}
	
	public void addStop(String name, String description, int category, int access, boolean accessible){
		//this shouldn't work, add returns boolean...
		mStops.add(new Stop(name,description,category,access,accessible));
		if(accessible)
			mHandicapStops++;
	}
	
	public void removeStop(int pos){
		if(mStops.get(pos).mAccessible)
			mHandicapStops--;
		mStops.remove(pos);
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
		try{
			j.put("id",mID);
			j.put("title", mTitle);
			j.put("description", mDescription);
			//how to do Access?
			j.put("rating", mRating);
			j.put("tags", mTags);
			j.put("numDownloads", mNumDownloads);
			j.put("downloaded", isDownloaded);
			//stop list
			j.put("stops",mStops.stopListToJSON());
			j.put("handicapStops", mHandicapStops);
			j.put("currentStop",mCurrentStop);
			j.put("finished",mFinished);
		}
		catch (JSONException e){
			
		}
		return j;
	}
	
	//will return null if the last stop has been reached
	//whatever calls this will need to handle it somehow
	public Stop getNextStop() {
		if(!mFinished){
			if(mStops.getLast() == mStops.get(mCurrentStop)){
				mFinished = true;
			}
			return mStops.get(mCurrentStop++);
		}
		else{
			return null;
		}
	}
}