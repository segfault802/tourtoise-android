package com.iCompute.tour.objects;

import com.iCompute.tour.objects.Common.*;

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
	
	public Stop(String name, String description, int category, int access, boolean accessible){
		mTitle = name;
		mDescription = description;
		mCategory = category;
		
		switch(access){
		case 0:
			mAgeAccess = AgeAccess.G;
			break;
		case 1:
			mAgeAccess = AgeAccess.PG;
			break;
		case 2:
			mAgeAccess = AgeAccess.PG13;
			break;
		case 3:
			mAgeAccess = AgeAccess.R;
			break;
		}
		mAccessible = accessible;
	}
	
	
}