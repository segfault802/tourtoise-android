package com.iCompute.tour;

import com.iCompute.tour.backend.ToursManager;
import com.iCompute.tour.objects.Stop;
import com.iCompute.tour.objects.StopList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ViewTourStopsListActivity extends ListActivity{

	private ListView list;
	private StopsListAdapter adapter;
	private TextView counter;
	
	private ToursManager manager;
	private long tourID=-1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_stops_list_layout);
		manager=((TourApplication)getApplication()).getToursManager();
		Intent intent = getIntent();
		tourID=intent.getLongExtra("tourID", -1);
		
		list=getListView();
		adapter=new StopsListAdapter(this, manager.getTourStops(tourID));
		manager.cacheTour(tourID);
		list.setAdapter(adapter);
		counter=(TextView)findViewById(R.id.remainingStopsViewStopsTextView);
		updateStopCounter();
	}
	
	private void updateStopCounter()
	{
		counter.setText("Stops remaining: "+adapter.nonVisitedStops());
	}
	
	//only called editStop because the layout is reused from the edit stops layout
	public void editStopClicked(View v)
	{
		Stop stop=((StopsListAdapter.ViewHolder)((View)v.getParent()).getTag()).mStop;
		Intent intent=new Intent(this, ViewStopActivity.class);
		intent.putExtra("tourID", stop.mTourID);
		intent.putExtra("stopID", stop.getStopID());
		startActivity(intent);
		
	}
	public void deleteButtonClicked(View v)
	{
		int pos=list.getPositionForView((View) v.getParent().getParent());
		adapter.stopSkipped(pos);
	}
	
	public void moveItem(View v)
	{
		if(v.getId()==R.id.mvDownEditTourStopsListItemImgButton)
		{
			adapter.moveItem(list.getPositionForView((View) v.getParent().getParent()),
					false);
		}
		else if(v.getId()==R.id.mvUpEditTourStopsListItemImgButton)
		{
			adapter.moveItem(list.getPositionForView((View) v.getParent().getParent()),
					true);
		}
	}
	
	
	private class StopsListAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		private StopList mStops;
		private int nextStop;
		private static final int PENDING=0;
		private static final int NEXT=1;
		private static final int VISITED=2;
		private static final int SKIPPED=3;
		
		public StopsListAdapter(Context context, StopList stops){
			mInflater= LayoutInflater.from(context);
			mStops=stops;
			nextStop=mStops.getNextStop();
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
		
		public int nonVisitedStops()
		{
			int count=0;
			for(Stop temp:mStops)
			{
				if(temp.stopStatus==PENDING||temp.stopStatus==NEXT)
					count++;
			}
			return count;
		}
		
		public void moveItem(int pos, Boolean mvUp)
		{
			if(mStops.changeOrder(pos, mvUp))
			{
				notifyDataSetChanged();
				if(nextStop==pos)
					pos+=(mvUp?-1:1);
			}
		}
		
		@Override
		public int getCount() {
			return mStops.size();
		}

		@Override
		public Stop getItem(int i) {
			
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
				holder.mAudioIndicator=(ImageView)view.findViewById(R.id.audioEditStopsListItemIcon);
				holder.mPicIndicator=(ImageView)view.findViewById(R.id.imageEditStopsListItemIcon);
				
				view.setTag(holder);
			}else{
				holder=(ViewHolder)view.getTag();
			}
			
			
			holder.mStop= getItem(i);
			holder.mTitle.setText(holder.mStop.mTitle);
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
			Stop mStop;
			ImageView mStopIcon;
			TextView mTitle;
			ImageView mPicIndicator;
			ImageView mAudioIndicator;
			
		}
	}
	
}
