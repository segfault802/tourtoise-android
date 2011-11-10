package com.iCompute.tour.backend;

/*
 * tour
 *      id int
 *      title text
 * 		desc text
 * 		access  enum
 * 		rating int
 * 		tags text
 * 		numdownloads int
 * 		stops list?
 * 		images path ->list
 * 
 * stop
 * 		id int
 * 		title str
 * 		desc str
 * 		accessibility int/bool
 * 		admission double
 * 		category  int
 * 		ageaccess enum{G,PG,PG-13,R,NC-17}
 *      startTime timestamp?
 *      startTime timestamp?
 *      tourid int
 *      images path -> list
 *      mp3 path
 * 
 * 
 * media
 * 		uniquefilename str
 * 		originalfilename str
 * 		tourid int
 * 		stopnumber int
 * 		medianumber int
 * 		type str
 * 		filesize int
 * 		hasuploaded int/bool
 * 
 * 
 * 
 */


//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;

public interface TourDBInterface{
	
	public void addTour(String title, String desc, int access,  String tags /*list*/);
	
	public void updateTour();
	
	public void deleteTour();
	
	public void addStop(String title, String desc, int accessibility, double admission, int category, int age /*starttime, endtime*/);
	
	public void updateStop();
	
	public void deleteStop();
	
	public void addMedia();
	
	public void updateMedia();
	
	public void deleteMedia();
}