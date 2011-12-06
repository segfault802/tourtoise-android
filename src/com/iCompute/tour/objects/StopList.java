package com.iCompute.tour.objects;

import com.iCompute.tour.objects.Stop;

import java.util.LinkedList;

public class StopList extends LinkedList<Stop> {


	public boolean add(Stop s){
		return super.add(s);
	}
	
	
	public void add(int index, Stop s){
		super.add(index,s);
	}


	public boolean changeOrder(int pos, Boolean mvUp) {
		return mvUp;
		// TODO Auto-generated method stub
		
	}


	public int getNextStop() {
		// TODO Auto-generated method stub
		//next as in next stop status
		return 0;
	}


}
