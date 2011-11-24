package com.iCompute.tour;

import com.iCompute.tour.Common.*;

public class Stop{
	
	private int mID;
	private String mTitle;
	private String mDescription;
	private boolean mAccessible;
	private double mAdmission;
	private int mCategory;
	private AgeAccess mAgeAccess;
	private String mStartTime;
	private String mEndTime;
	private int mTourID;
	
	public Stop(String name, String description, int category, AgeAccess a, boolean accessible){
		mTitle = name;
		mDescription = description;
		mCategory = category;
		mAgeAccess = a;
		mAccessible = accessible;
	}
	
	
}