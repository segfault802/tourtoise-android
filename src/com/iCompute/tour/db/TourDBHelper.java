package com.iCompute.tour.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class TourDBHelper extends SQLiteOpenHelper {

	private static final String CREATE_TABLE_STRING = "foo";
	
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


/*
public class DictionaryOpenHelper extends SQLiteOpenHelper {

private static final int DATABASE_VERSION = 2;
private static final String DICTIONARY_TABLE_NAME = "dictionary";
private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
            KEY_WORD + " TEXT, " +
            KEY_DEFINITION + " TEXT);";

DictionaryOpenHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
}

@Override
public void onCreate(SQLiteDatabase db) {
    db.execSQL(DICTIONARY_TABLE_CREATE);
}
}
*/