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


}
