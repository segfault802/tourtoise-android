package com.iCompute.tour.objects;

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
	

}
