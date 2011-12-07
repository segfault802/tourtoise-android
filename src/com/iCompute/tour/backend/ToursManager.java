package com.iCompute.tour.backend;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.iCompute.tour.objects.Media;
import com.iCompute.tour.objects.Stop;
import com.iCompute.tour.objects.StopList;
import com.iCompute.tour.objects.Tour;
import com.iCompute.tour.objects.ToursList;

public class ToursManager {

	ToursList mTours;
	Tour mSelectedTour;
	Tour mTempTour;

	private ArrayList<Long> tourIDs;
	
	public Tour getTour(long tourID) {
		return mTours.getTourByID(tourID);
	}

	public void updateTour(long tourID, String name, String description,
			String tags, boolean isWalk) {
		Tour t = getTour(tourID);
		t.update(name, description, tags, isWalk);		
	}

	public void addTour(Tour tour) {
		mTours.add(tour);
	}

	public void publishToServer(long tourID) {
		// TODO Auto-generated method stub
		
	}

	public StopList getTourStops(long tourID) {
		return mTours.getTourByID(tourID).getStops();
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
		return mTours.getTourByID(tourID).getStops().getStop(stopID);
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

//	public Tour getTemporaryTour() {
//		mTempTour = new Tour();
		
//	}

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
	
	public void saveToursList(Context c)
	{
		
	}
	public void saveTours(Context c)
	{
		for(Tour tour:mTours)
		{
			saveTour(c, tour);
		}
	}
	public void saveTour(Context c, long id)
	{
		saveTour(c, mTours.getTourByID(id));
	}
	public void saveTour(Context c, Tour tour)
	{
		String data=tour.tourToJSON().toString();
		FileOutputStream fos;
		try{
			fos = c.openFileOutput("tour_"+tour.getTourID(), Context.MODE_PRIVATE);
			fos.write(data.getBytes());
			fos.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadAllTourData(Context c)
	{
		loadToursList(c);
		for(long id:tourIDs)
		{
			
		}
		
	}
	public void loadToursList(Context c)
	{
		
	}
	public Tour loadTour(Context c, long id)
	{
		StringBuilder sb=new StringBuilder();
		Tour tour=null;
		try
		{
			FileInputStream fin=c.openFileInput("tour_"+id);
			int ch;
			while((ch=fin.read())!=-1)
			{
				sb.append((char)ch);
			}
			tour=new Tour(new JSONObject(sb.toString()));
		}
		catch(IOException e)
		{
			
		} catch (JSONException e) {
			
		}
		return tour;
	}
	
}
