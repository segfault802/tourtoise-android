package com.iCompute.tour.backend;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.iCompute.tour.objects.Media;
import com.iCompute.tour.objects.Stop;
import com.iCompute.tour.objects.StopList;
import com.iCompute.tour.objects.Tour;
import com.iCompute.tour.objects.TourHeader;
import com.iCompute.tour.objects.ToursList;

public class ToursManager {

	private ToursList mTours;

	private ArrayList<TourHeader> tourHeaders;
	private Tour mTempTour;
	private Stop mTempStop;

	public ToursManager(){
		mTours = new ToursList();
	}
	
	public Tour getTour(long tourID) {
		return mTours.getTourByID(tourID);
	}
	
	public Tour getTourFromServer(long tourID)
	{
		//TODO get tour from server
		//DO NOT SAVE LOCALLY
		return null;
	}
	
	public boolean downloadTourFromServer(long tourID)
	{
		//TODO save tour locally
		return false;
	}

	public Stop getStop(long tourID, long stopID) {
		return mTours.getTourByID(tourID).getStops().getStop(stopID);
	}

	public StopList getTourStops(long tourID) {
		return mTours.getTourByID(tourID).getStops();
	}

	public Media getMediaForTour(long tourID) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<TourHeader> searchServerTours(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<TourHeader> getLocalTours() {
		return mTours.getTourHeaders();
	}
	
	public String[] getTourStopNames(long tourID) {
		return (String[]) mTours.getTourByID(tourID).getStops().getStopNames().toArray();
	}

	public Media getMediaForStop(long tourID, long stopID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Stop getTemporaryStop(long tourID) {
		mTempStop=new Stop(tourID);
		
		return mTempStop;
	}

	public Tour getTemporaryTour() {
		mTempTour = new Tour();
		
		return mTempTour;
	}
	

	public void updateTour(long tourID, String name, String description,
			String tags, boolean isWalk) {
		//TODO check if is temp
		getTour(tourID).update(name, description, tags, isWalk);		
	}

	public void addTour(Tour tour) {
		//TODO test if is tempTour
		mTours.add(tour);
		
	}
	public void addTourStop(long tourID, Stop stop) {
		//TODO test if is tempStop
		mTours.getTourByID(tourID).addStop(stop);
	}

	public void updateStop(long tourID, Stop stop) {
		//TODO check if is temp
		mTours.getTourByID(tourID).updateStop(stop);
	}

	public void discardTemporaryStop(long stopID) {
		mTempStop=null;
	}

	public void discardTemporaryTour(long tourID) {
		mTempTour=null;
		
	}

	public void updateTour(long tourID, Tour tour) {
		if(tourID==mTempTour.mID)
		{
			mTours.add(tour);
		}
		else
			mTours.getTourByID(tourID).update(tour);
	}

	
	public void saveTemporaryTour(long tourID, Tour mTour) {
		mTours.add(mTour);
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
	
	

	public void publishToServer(long tourID) {
		// TODO Auto-generated method stub
		
	}


	/*
	 * File IO stuff
	 */
	
	public void saveAllData(Context c)
	{
		saveToursList(c);
		saveTours(c);
	}
	
	public void saveToursList(Context c)
	{
		JSONArray array=new JSONArray();
		for(TourHeader header:tourHeaders)
		{
			array.put(header.toJSONObject());
		}
		saveFile(c, "toursList", array.toString());
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
		saveFile(c, "tour_"+tour.mID, data);
	}
	
	public void loadAllTourData(Context c)
	{
		loadToursList(c);
		for(TourHeader header :tourHeaders)
		{
			this.mTours.add(loadTour(c, header.mID));
		}
		
	}
	public void loadToursList(Context c)
	{
		tourHeaders=new ArrayList<TourHeader>();
		try {
			JSONArray array=new JSONArray(getFileString(c, "toursList"));
			for(int i=0; i<array.length();i++)
			{
				JSONObject obj=array.getJSONObject(i);
				tourHeaders.add(new TourHeader(obj));
			}
		} catch (JSONException e) 
		{
			tourHeaders=new ArrayList<TourHeader>();
		}
		
	}
	
	public Tour loadTour(Context c, long id)
	{
		StringBuilder sb=new StringBuilder();
		Tour tour=null;
		try {
			tour=new Tour(new JSONObject(getFileString(c, "tour+"+id)));
		} catch (JSONException e) {
			tour=null;
		}
		return tour;
	}
	
	/*
	 * file IO helped functions
	 */
	private String getFileString(Context c, String file)
	{
		StringBuilder sb=new StringBuilder();
		try
		{
			FileInputStream fin=c.openFileInput(file);
			int ch;
			while((ch=fin.read())!=-1)
			{
				sb.append((char)ch);
			}
		}
		catch(IOException e)
		{
			sb=new StringBuilder();
		}
		return sb.toString();
	}
	private boolean saveFile(Context c, String file, String data)
	{
		boolean result=true;
		FileOutputStream fos;
		try{
			fos = c.openFileOutput(file, Context.MODE_PRIVATE);
			fos.write(data.getBytes());
			fos.close();
		}
		catch (IOException e) {
			result=false;
		}
		return result;
	}
	
}
