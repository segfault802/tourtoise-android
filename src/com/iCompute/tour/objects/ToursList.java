package com.iCompute.tour.objects;

import com.iCompute.tour.objects.Tour;
import com.iCompute.tour.objects.TourHeader;

import java.util.LinkedList;
import java.util.ArrayList;
import org.json.JSONObject;

public class ToursList extends LinkedList<Tour>{

	//private LinkedList<Tour> mTours;
	//private int mNumTours;
	

	
	public String[] getNames(){
		int i;
		int count = this.size();
		String[] s = new String[count];
		for(i=0;i<count;i++){
			s[i] = this.get(i).getTitle().toString();
		}
		return s;
	}
	
	public Tour getTourByID(long id){
		for(int i = 0; i < this.size(); i++){
			if(this.get(i).getTourID() == id){
				return this.get(i);
			}
		}
		return null;
	}

	public ArrayList<TourHeader> getTourHeaders(){
		TourHeader header;
		ArrayList<TourHeader> list = new ArrayList<TourHeader>();
		int i;
		for (i = 0; i < this.size(); i++){
			Tour t = this.get(i);
			header = new TourHeader(t.mID,t.mTitle,t.mDescription,t.getStops().size(),t.mHandicapStops,t.isDriving,t.isDownloaded);
			list.add(header);			
		}
		return list;
	}
	
	public boolean add(Tour t){
		return super.add(t);		
	}
	
	public void add(int index, Tour t){
		super.add(index,t);
	}
	
	//TODO: make JSON object with tours list
	public JSONObject tourInfoToJSON(){
		return new JSONObject();
	}
	
	
	
}
