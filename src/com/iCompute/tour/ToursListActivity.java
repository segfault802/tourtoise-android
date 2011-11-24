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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.iCompute.tour.ToursList;

public class ToursListActivity extends ListActivity implements View.OnClickListener{
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
	private class TourListAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		private ToursList mTours;
		private boolean mIsSearch;
		public TourListAdapter(Context context, ToursList tours, boolean isSearch){
			mInflater= LayoutInflater.from(context);
			mTours = tours;
			mIsSearch=isSearch;
		}
		
		public void removeItem(int i)
		{
			if(i>=0&&i<mTours.size()-1)
			{
				mTours.remove(i);
				this.notifyDataSetChanged();
			}
		}
		
				
		public ToursList getItems()
		{
			return mTours;
		}
		
		@Override
		public int getCount() {
			return mTours.size();
		}

		@Override
		public Tour getItem(int i) {
			
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
			
			if(mIsSearch)
			{
				view.findViewById(R.id.delToursListItemImgButton).setVisibility(View.GONE);
				view.findViewById(R.id.editToursListItemButton).setVisibility(View.GONE);
			}
			else// if tour is downloaded
			{
				//findViewById(R.id.editToursListItemButton).setVisibility(View.GONE);
			}
			
			//setting click listeners
			view.findViewById(R.id.delToursListItemImgButton).setOnClickListener(ToursListActivity.this);
			view.findViewById(R.id.editToursListItemButton).setOnClickListener(ToursListActivity.this);
			view.findViewById(R.id.tourInfoToursListItemLL).setOnClickListener(ToursListActivity.this);
			
			holder.mStop= getItem(i);
			holder.mTitle.setText(holder.mStop);
			//holder.mAudioIndicator.setVisibility(visibility); View.GONE or View.VISIBLE
			//holder.mPicIndicator.setVisibility(visibility); View.GONE or View.VISIBLE
			//holder.mStopIcon.setImageResource(resId); //set icon image from resource
			
			return view;
		}
		
		public class ViewHolder{
			String mName;
			int mNumStops;
			TextView mTitle;
			
		}
	}

}
