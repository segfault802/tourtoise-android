package com.iCompute.tour;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ToursListActivity extends ListActivity {
	
	ListView list;
	TourListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tours_list_layout);
		list=getListView();
		adapter=new TourListAdapter(this, getResources().getStringArray(R.array.temp_tour_names));
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				startActivity(new Intent(ToursListActivity.this, ViewTourActivity.class));
			}
		});
	}
	
	
	
	private class TourListAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		private ArrayList<String> mTours;
		
		public TourListAdapter(Context context, String[] strings){
			mInflater= LayoutInflater.from(context);
			mTours=new ArrayList<String>();
			for(String str:strings)
			{
				
				mTours.add(str);
			}
		}
		
		public void removeItem(int i)
		{
			if(i>=0&&i<mTours.size()-1)
			{
				mTours.remove(i);
				this.notifyDataSetChanged();
			}
		}
		
				
		public ArrayList<String> getItems()
		{
			return mTours;
		}
		
		/*
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
		*/
		@Override
		public int getCount() {
			return mTours.size();
		}

		@Override
		public String getItem(int i) {
			
			return mTours.get(i);
		}

		@Override
		public long getItemId(int i) {
			
			return i;
		}
		
		
		@Override
		public View getView(int i, View v, ViewGroup group) {
			final ViewHolder holder;
			View view= v;
			if(view==null||view.getTag()==null){
				view=mInflater.inflate(R.layout.tour_list_item, null);
				holder = new ViewHolder();
				holder.mTitle=(TextView)view.findViewById(R.id.tourNameTourListItemTextView);
				
				view.setTag(holder);
			}else{
				holder=(ViewHolder)view.getTag();
			}
			
			holder.mStop= getItem(i);
			holder.mTitle.setText(holder.mStop);
			//holder.mAudioIndicator.setVisibility(visibility); View.GONE or View.VISIBLE
			//holder.mPicIndicator.setVisibility(visibility); View.GONE or View.VISIBLE
			//holder.mStopIcon.setImageResource(resId); //set icon image from resource
			
			return view;
		}
		
		public class ViewHolder{
			String mStop;
			TextView mTitle;
			
		}
	}

}
