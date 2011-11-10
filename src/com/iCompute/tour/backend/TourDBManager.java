package com.iCompute.tour.backend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TourDBManager {
	
	
	private static final String CREATE_TABLE_STRING = "CREATE TABLE ";
	private static final String TABLE_TOUR = "tour";
	private static final String TABLE_SITE = "site";
	
	
	public void addTour(){
	}
	
	
	
	
	private class TourDBHelper extends SQLiteOpenHelper{
		
		public TourDBHelper(Context context) {
			super(context, "foo", null, 2);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(CREATE_TABLE_STRING);

		}
		
		//not implemented
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		}
	}

}
