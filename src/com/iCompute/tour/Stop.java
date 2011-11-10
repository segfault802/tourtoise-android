package com.iCompute.tour;


public class Stop{
	
	private int mID;
	private String mTitle;
	private String mDescription;
	private boolean mAccessibility;
	private double mAdmission;
	private int mCategory;
	private AgeAccess mAgeAccess;
	private String mStartTime;
	private String mEndTime;
	private int mTourID;
	
	
	private enum AgeAccess {G,PG,PG13,R,NC17}
	
}