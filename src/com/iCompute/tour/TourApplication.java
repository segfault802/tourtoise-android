package com.iCompute.tour;

import com.iCompute.tour.backend.ToursManager;
import com.iCompute.tour.objects.ToursList;

import android.app.Application;

public class TourApplication extends Application {
	
	//put all our persistent state and other data in here
	
	//the application's internal copy of the tours list
	public ToursList tours = new ToursList();
	private ToursManager toursManager;
	
	public ToursManager getToursManager() {
		return toursManager;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		toursManager = new ToursManager();
		toursManager.loadAllTourData(super.getApplicationContext());
	}
	
	@Override
	public void onTerminate(){
		super.onTerminate();
		toursManager.saveToursList(getApplicationContext());
		toursManager.saveTours(getApplicationContext());
	}
	

	
}
