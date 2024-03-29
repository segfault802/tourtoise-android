package com.iCompute.tour.objects;

import java.util.Date;
import org.json.JSONObject;
import org.json.JSONException;

public class Tour{
	
	public long mID=-1;
	public String mTitle;
	public String mDescription;
	public boolean isDriving;
	private int mRating=0;
	public String mTags;
	private int mNumDownloads=0;
	public boolean isDownloaded=false;
	private StopList mStops;
	public int mHandicapStops=0;
	private int mCurrentStop;
	private boolean mFinished;
	
	
	public Tour(){
		mID = (new Date().getTime()); 
	}
	
	public Tour(String name, String description, String tags, boolean driving, boolean downloaded){
		try{
			mTitle = name;
			mDescription = description;
			mTags = tags;
			isDriving = driving;
			mStops = new StopList();
			isDownloaded = downloaded;
			if(!isDownloaded){
				mID = (new Date().getTime());
			}
		}
		catch(Exception e){}
		
		//mStops = s;	
	}
	
	public Tour(JSONObject json) {
		tourFromJSON(json);
	}

	public StopList getStops(){
		return mStops;
	}
	
	public int getCurrentStop(){
		return mCurrentStop;
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
	
	public boolean isDriving(){
		return isDriving;
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
	
	public void update(String name, String description, String tags, boolean driving){
		mTitle = name;
		mTags=tags;
		mDescription = description;
		isDriving = driving;
	}
	
	public JSONObject tourToJSON(){
		JSONObject j = new JSONObject();
		JSONObject tour = new JSONObject();
		try{
			j.put("id",mID);
			j.put("title", mTitle);
			j.put("description", mDescription);
			j.put("access", isDriving?"Drive":"Walk");
			j.put("rating", mRating);
			j.put("tags", mTags);
			j.put("numdownloads", mNumDownloads);
			j.put("downloaded", isDownloaded);
			j.put("stops",mStops.stopListToJSON());
			j.put("handicapStops", mHandicapStops);
			j.put("currentStop",mCurrentStop);
			j.put("finished",mFinished);
			tour.put("tour", j);
		}
		catch (JSONException e){
			
		}
		return j;
	}
	
	//set the tour's fields from the supplied JSON object
	public void tourFromJSON(JSONObject tour){
		try{
			JSONObject j = tour.getJSONObject("tour");
			mID = j.getLong("id");
			mTitle = j.getString("title");
			mDescription = j.getString("description");
			//how to do Access?
			isDriving = (j.getString("access") == "Drive");
			mRating = j.getInt("rating");
			mTags = j.getString("tags");
			mNumDownloads = j.getInt("numdownloads");
			isDownloaded = j.getBoolean("downloaded");
			mStops = new StopList();
			mStops.stopListFromJSON(j.getJSONArray("stops"));
			mHandicapStops = j.getInt("handicapstops");
			mCurrentStop = j.getInt("currentstop");
			mFinished = j.getBoolean("finished");
		}
		catch (JSONException e){
			
		}
	}
	
	//will return null if the last stop has been reached
	//whatever calls this will need to handle it somehow
	public Stop getNextStop() {
		if(!mFinished){
			if(mStops.getLast() == mStops.get(mCurrentStop)){
				mFinished = true;
			}
			mStops.incrementStopIndex();
			return mStops.get(mCurrentStop++);
		}
		else{
			return null;
		}
	}
	
	public void saveTourToFile(){
		
	}

	public void addStop(Stop stop) {
		mStops.add(stop);
	}

	public void updateStop(Stop stop) {
		mStops.updateStop(stop);
	}

	public void update(Tour tour) {
		update(tour.mTitle, tour.mDescription, tour.mTags, tour.isDriving);
	}
}
