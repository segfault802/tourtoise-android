package com.iCompute.tour.backend;

import java.util.ArrayList;
import java.util.LinkedList;

import com.iCompute.tour.objects.Media;
import com.iCompute.tour.objects.Stop;
import com.iCompute.tour.objects.StopList;
import com.iCompute.tour.objects.Tour;

public class ToursManager {

	public Tour getTour(long tourID) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateTour(long tourID, String name, String description,
			String tags, boolean isWalk) {
		// TODO Auto-generated method stub
		
	}

	public void addTour(Tour tour) {
		// TODO Auto-generated method stub
	}

	public void publishToServer(long tourID) {
		// TODO Auto-generated method stub
		
	}

	public StopList getTourStops(long tourID) {
		// TODO Auto-generated method stub
		return null;
	}

	public void cacheTour(long tourID) {
		//to prevent a tour from being released from memory when activities are using it
		//have an int counter for each tour stored in memory and add 1
		// TODO Auto-generated method stub
		
	}

	public void releaseTour(long tourID) {
		//to prevent a tour from being released from memory when activities are using it
		//have an int counter for each tour stored in memory and subtract 1
		// TODO Auto-generated method stub
		
	}

	public Stop getStop(long tourID, long stopID) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addTourStop(long tourID, Stop mStop) {
		// TODO Auto-generated method stub
		
	}

	public void updateStop(long tourID, Stop mStop) {
		// TODO Auto-generated method stub
		
	}

	public void discardTemporaryStop(long stopID) {
		// TODO Auto-generated method stub
		
	}

	public Stop getTemporaryStop(long tourID) {
		/* TODO Auto-generated method stub
		* make a new stop but don't add to tour yet.
		* media can be added to the new stop but if the stop is discarded the associated media will also be discarded.
		*/ 
		return null;
	}

	public Tour getTemporaryTour() {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateTour(long tourID, Tour mTour) {
		// TODO Auto-generated method stub
		
	}

	public void saveTemporaryTour(long tourID, Tour mTour) {
		// TODO Auto-generated method stub
		
	}

	public void discardTemporaryTour(long tourID) {
		// TODO Auto-generated method stub
		
	}

	public Media getMediaForTour(long tourID) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Tour> searchServerTours(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Tour> getLocalTours() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getTourStopNames(long tourID) {
		// TODO Auto-generated method stub
		return null;
	}

	public Media getMediaForStop(long tourID, long stopID) {
		// TODO Auto-generated method stub
		return null;
	}

}
