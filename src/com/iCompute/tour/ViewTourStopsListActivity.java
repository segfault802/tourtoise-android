package com.iCompute.tour;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ViewTourStopsListActivity extends ListActivity {

	ListView list;
	StopsListAdapter adapter;
	TextView counter;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_stops_list_layout);
		
		list=getListView();
		adapter=new StopsListAdapter(this, getResources().getStringArray(R.array.temp_stop_names));
		list.setAdapter(adapter);
		counter=(TextView)findViewById(R.id.remainingStopsViewStopsTextView);
		updateStopCounter();
	}
	
	private void updateStopCounter()
	{
		counter.setText("Stops remaining: "+adapter.getCount());
	}
	
	
	private class StopsListAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		private ArrayList<StopTemp> mStops;
		private int nextStop;
		private static final int PENDING=0;
		private static final int NEXT=1;
		private static final int VISITED=2;
		private static final int SKIPPED=3;
		
		public StopsListAdapter(Context context, String[] strings){
			mInflater= LayoutInflater.from(context);
			mStops=new ArrayList<StopTemp>();
			for(String str:strings)
			{
				StopTemp temp = new StopTemp();
				temp.stopName=str;
				temp.stopStatus=PENDING;
				mStops.add(temp);
			}
			nextStop=(mStops.size()==0?-1:0);
		}
		
		public void removeItem(int i)
		{
			if(i>=0&&i<mStops.size()-1)
			{
				mStops.remove(i);
				this.notifyDataSetChanged();
			}
		}
		
		public void stopPending(int i)
		{
			mStops.get(i).stopStatus=PENDING;
		}
		
		public void stopVisited(int i)
		{
			mStops.get(i).stopStatus=VISITED;
			if(i==nextStop)
			{
				setNextStop();
			}
		}
		
		public void stopNext(int i)
		{
			mStops.get(i).stopStatus=NEXT;
			mStops.get(nextStop).stopStatus=PENDING;
			nextStop=i;
		}
		
		public void stopSkipped(int i)
		{
			mStops.get(i).stopStatus=SKIPPED;
			if(i==nextStop)
			{
				setNextStop();
			}
		}
		
		private void setNextStop()
		{
			int i=nextStop;
			for(int j=1; j<mStops.size();j++)
			{
				if(mStops.get((i+j)%mStops.size()).stopStatus==PENDING)
				{
					nextStop=(i+j)%mStops.size();
					mStops.get((i+j)%mStops.size()).stopStatus=NEXT;
					j=mStops.size();
				}
			}
		}
		
		public ArrayList<StopTemp> getItems()
		{
			return mStops;
		}
		
		public int nonVisitedStops()
		{
			int count=0;
			for(StopTemp temp:mStops)
			{
				if(temp.stopStatus==PENDING||temp.stopStatus==NEXT)
					count++;
			}
			return count;
		}
		
		public void moveItem(int pos, Boolean mvUp)
		{
			StopTemp temp;
			if(mvUp&&pos!=0)
			{
				temp=mStops.get(pos);
				mStops.remove(pos);
				mStops.add(pos-1, temp);
				if(pos==nextStop)
					nextStop--;
				this.notifyDataSetChanged();
			}
			else if(!mvUp&&pos!=mStops.size()-1)
			{
				temp=mStops.get(pos);
				mStops.remove(pos);
				mStops.add(pos+1, temp);
				if(pos==nextStop)
					nextStop++;
				this.notifyDataSetChanged();
			}
		}
		
		@Override
		public int getCount() {
			return mStops.size();
		}

		@Override
		public StopTemp getItem(int i) {
			
			return mStops.get(i);
		}

		@Override
		public long getItemId(int i) {
			
			return i;
		}
		
		private void viewPending(View v)
		{
			
		}
		private void viewNext(View v)
		{
			
		}
		private void viewVisited(View v)
		{
			
		}
		private void viewSkipped(View v)
		{
			
		}

		@Override
		public View getView(int i, View v, ViewGroup group) {
			final ViewHolder holder;
			View view= v;
			if(view==null||view.getTag()==null){
				view=mInflater.inflate(R.layout.edit_stops_list_item, null);
				holder = new ViewHolder();
				holder.mTitle=(TextView)view.findViewById(R.id.editStopsListItemStopNameTextView);
				holder.mStopIcon=(ImageView)view.findViewById(R.id.editStopListCatImg);
				holder.mAudioIndicator=(ImageView)view.findViewById(R.id.editStopsListItemAudioIcon);
				holder.mPicIndicator=(ImageView)view.findViewById(R.id.editStopsListItemImageIcon);
				
				view.setTag(holder);
			}else{
				holder=(ViewHolder)view.getTag();
			}
			
			holder.mStop= getItem(i);
			holder.mTitle.setText(holder.mStop.stopName);
			//holder.mAudioIndicator.setVisibility(visibility); View.GONE or View.VISIBLE
			//holder.mPicIndicator.setVisibility(visibility); View.GONE or View.VISIBLE
			//holder.mStopIcon.setImageResource(resId); //set icon image from resource
			
			switch(holder.mStop.stopStatus)
			{
			case PENDING:
				viewPending(v);
				break;
			case NEXT:
				viewNext(v);
				break;
			case VISITED:
				viewVisited(v);
				break;
			case SKIPPED:
				viewSkipped(v);
				break;
			}
			view.setTag(holder);
			
			return view;
		}
		
		public class ViewHolder{
			StopTemp mStop;
			ImageView mStopIcon;
			TextView mTitle;
			ImageView mPicIndicator;
			ImageView mAudioIndicator;
			
		}
	}
	
	
	public class StopTemp
	{
		CharSequence stopName;
		int stopStatus;
	}
	
}
