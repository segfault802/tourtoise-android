package com.iCompute.tour;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.iCompute.tour.ToursList;

public class ToursListActivity extends ListActivity implements OnClickListener, OnItemClickListener{
	//TODO modify this to pass in the tours list to the TourListAdapter so we can build the list properly
	
	
	private ListView list;
	private TourListAdapter adapter;
	private boolean isSearch;
	private int tourToDelete=-1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tours_list_layout);
		Intent intent=getIntent();
		isSearch=!intent.getBooleanExtra("myTours", false);
		if(isSearch)
		{
			//TODO search query from intent
			CharSequence title="Search Query";
			((TextView)findViewById(R.id.searchTitleToursListTextView)).setText(title);
		}
		else
		{
			((TextView)findViewById(R.id.searchTitleToursListTextView)).setText("My Tours");
		}
		

		list=getListView();
		adapter=new TourListAdapter(this, ((TourApplication)getApplication()).tours, isSearch);
		list.setAdapter(adapter);
		
	}
	
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id){
		
	}
	
	
	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.delToursListItemImgButton:
			tourToDelete=list.getPositionForView((View) v.getParent());
			showDialog(0);
			break;
		case R.id.editToursListItemButton:
			editTour();
			break;
		case R.id.tourInfoToursListItemLL:
			viewTour();
			break;
		}
	}
	
	@Override
	public Dialog onCreateDialog(int i)
	{
		Dialog d;
		switch(i)
		{
		case 0:
			d=createDeleteConfirmDialog();
			break;
		default:
			d=null;
			break;
		}
		return d;
	}
	
	private void editTour()
	{
		Intent i=new Intent(ToursListActivity.this, EditTourActivity.class);
		startActivity(i);
	}
	
	private void viewTour()
	{
		Intent i=new Intent(ToursListActivity.this, ViewTourActivity.class);
		i.putExtra("isLocal", !isSearch);
		startActivity(i);
	}
	
	private void confirmDeleteTour()
	{
		adapter.removeItem(tourToDelete);
		tourToDelete=-1;
	}
	private void cancelDeleteTour()
	{
		tourToDelete=-1;
	}
	
	private Dialog createDeleteConfirmDialog()
	{
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("Delete Tour");
		builder.setMessage("Are you sure you would like to delete this tour from your device?");
		builder.setPositiveButton("Delete", new Dialog.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				confirmDeleteTour();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("Cancel", new Dialog.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				cancelDeleteTour();
				dialog.dismiss();
			}
		});
		return builder.create();
	}
	
	//TODO modify this to display the list and bind the buttons correctly
	private class TourListAdapter extends BaseAdapter implements ListAdapter{

		private LayoutInflater mInflater;
		private ToursList mToursList;
		private boolean mIsSearch;
		public TourListAdapter(Context context, ToursList tours, boolean isSearch){
			mInflater= LayoutInflater.from(context);
			mToursList = tours;
			mIsSearch=isSearch;
		}
		
		public void removeItem(int i)
		{
			if(i>=0&&i<mToursList.size()-1)
			{
				mToursList.remove(i);
				this.notifyDataSetChanged();
			}
		}
		
				
		public ToursList getItems()
		{
			return mToursList;
		}
		
		@Override
		public int getCount() {
			return mToursList.size();
		}

		@Override
		public Tour getItem(int i) {
			
			return mToursList.get(i);
		}

		@Override //not sure what purpose this serves
		public long getItemId(int i) {		
			return i;
		}
		
		
		@Override
		public View getView(int i, View v, ViewGroup group) {
			//final ViewHolder holder; //create a new view holder
			//View view = v;
			if(v==null||v.getTag()==null){ //if view is null
				v=mInflater.inflate(R.layout.tour_list_item, null);
				//holder = new ViewHolder();
				//holder.mTitle=(TextView)v.findViewById(R.id.tourNameTourListItemTextView);
				
				//v.setTag(holder);
			}else{
				//holder=(ViewHolder)v.getTag();
			}
			
			/*if(mIsSearch)
			{
				view.findViewById(R.id.delToursListItemImgButton).setVisibility(View.GONE);
				view.findViewById(R.id.editToursListItemButton).setVisibility(View.GONE);
			}*/
			//else// if tour is downloaded
			//{
				//findViewById(R.id.editToursListItemButton).setVisibility(View.GONE);
			//}
			
			//setting click listeners
			v.findViewById(R.id.delToursListItemImgButton).setOnClickListener(ToursListActivity.this);
			v.findViewById(R.id.editToursListItemButton).setOnClickListener(ToursListActivity.this);
			v.findViewById(R.id.tourInfoToursListItemLL).setOnClickListener(ToursListActivity.this);
			
			(TextView)v.findViewById(R.id.tourNameTourListItemTextView).setText(getItem())
			
			//holder.mTitle= ;
			//holder.mTitle.setText(holder.mStop);
			//holder.mAudioIndicator.setVisibility(visibility); View.GONE or View.VISIBLE
			//holder.mPicIndicator.setVisibility(visibility); View.GONE or View.VISIBLE
			//holder.mStopIcon.setImageResource(resId); //set icon image from resource
			
			return v;
		}
		
		/*public class ViewHolder{
			String mName;
			int mNumStops;
			TextView mTitle;
			
		}*/
	}

}
