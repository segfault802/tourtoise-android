package com.iCompute.tour.objects;

import com.iCompute.tour.objects.Stop;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedList;

public class StopList extends LinkedList<Stop> {
	
	//need to make sure this and the field in Tour always match
	private int mCurrentStop;
	private int mStopCount=0;


	public StopList(){
		super();
		mStopCount = 0;
	}
	
	
	//need to override the built in size() because caling it when no stops have been
	//added breaks it
	@Override
	public int size(){
		return mStopCount;
	}
	
	public boolean add(Stop s){
		mStopCount++;
		return super.add(s);
	}
	
	
	public void add(int index, Stop s){
		mStopCount++;
		super.add(index,s);
	}
	
	@Override
	public Stop remove(int index){
		mStopCount--;
		return super.remove(index);
	}
	
	public ArrayList<CharSequence> getStopNames()
	{
		ArrayList<CharSequence> names=new ArrayList<CharSequence>();
		for(Stop stop:this)
		{
			names.add(stop.mTitle);
		}
		return names;
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


	public int getNextStop(int currentStop) {
		return this.get(currentStop).getStatus();
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
	
	public void stopListFromJSON(JSONArray j){
		Stop newStop;
		int i;
		try{
			for(i = 0; i < j.length(); i++){
				newStop = new Stop();
				newStop.stopFromJSON(j.getJSONObject(i));
				this.add(newStop);
			}
		}
		catch (JSONException e){
			e.printStackTrace();
		}
	}


	public Stop getStop(long stopID) {
		Stop stop=null;
		for(int i=0; i<this.size();i++)
		{
			if(this.get(i).getStopID()==stopID)
			{
				stop=get(i);
				break;
			}
		}
		
		return stop;
	}


	public void updateStop(Stop stop) {
		getStop(stop.getStopID()).updateStop(stop);
	}


}
