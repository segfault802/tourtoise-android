package com.iCompute.tour.objects;

import com.iCompute.tour.objects.Stop;
import org.json.JSONArray;

import java.util.LinkedList;

public class StopList extends LinkedList<Stop> {
	
	//need to make sure this and the field in Tour always match
	private int mCurrentStop;


	public boolean add(Stop s){
		return super.add(s);
	}
	
	
	public void add(int index, Stop s){
		super.add(index,s);
	}

	//swap the stop at poistion pos with the one before or after it based on the vaue of mvUp
	//need to handle edge cases where the 1st stop might get moved
	//up or the last stop might get moved down off the list
	//use the boolean to tell whether the swap was successful?
	public boolean changeOrder(int pos, Boolean mvUp) {
		Stop stop = this.get(pos);
		Stop swapWith, tmp;
		//I'm assuming stops are in ascending order by index
		if(mvUp){
			swapWith = this.get(pos-1);
		}
		else{
			swapWith = this.get(pos+1);
		}
		tmp = stop;
		stop = swapWith;
		swapWith = tmp;
		return true; //assume it's always successful for now
	}


	public int getNextStop() {
		return this.get(mCurrentStop).getStatus();
	}
	
	public void incrementStopIndex(){
		mCurrentStop++;
	}
	
	public JSONArray stopListToJSON(){
		int size = this.size();
		JSONArray j = new JSONArray();
		for(int i = 0; i < size; i++){
			j.put(this.get(i).stopToJSON());
		}
		return j;
	}


}
