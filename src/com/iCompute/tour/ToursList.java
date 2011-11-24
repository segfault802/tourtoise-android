package com.iCompute.tour;

import com.iCompute.tour.Tour;
import java.util.LinkedList;;

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
	
	public boolean add(Tour t){
		return super.add(t);		
	}
	
	public void add(int index, Tour t){
		super.add(index,t);
	}
	
	
	
	
}
