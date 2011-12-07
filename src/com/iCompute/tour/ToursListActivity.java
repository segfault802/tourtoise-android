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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.iCompute.tour.backend.ToursManager;
import com.iCompute.tour.objects.Common.Access;
import com.iCompute.tour.objects.Tour;
import com.iCompute.tour.objects.TourHeader;
import com.iCompute.tour.objects.ToursList;

public class ToursListActivity extends ListActivity implements OnClickListener{ //OnItemClickListener{
	//TODO need to make it so that changes made when updating a tour are visible when returning to the tours list
	
	
	private ListView list;
	private TourListAdapter adapter;
	
	private ToursManager manager;
	private boolean isSearch;
	private int tourItemToDelete=-1;
	
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
			adapter=new TourListAdapter(this, manager.searchServerTours("search query"), isSearch);
		}
		else
		{
			((TextView)findViewById(R.id.searchTitleToursListTextView)).setText("My Tours");
			adapter=new TourListAdapter(this, manager.getLocalTours(), isSearch);
		}
		

		list=getListView();
		
		list.setAdapter(adapter);
		
	}
	
	
	
	//@Override
	/*public void onItemClick(AdapterView<?> parent, View v, int position, long id){
		switch(v.getId())
		{
		case R.id.delToursListItemImgButton:
			tourToDelete=id;
			showDialog(0);
			break;
		case R.id.editToursListItemButton:
			editTour();
			break;
		case R.id.tourInfoToursListItemLL:
			viewTour();
			break;
		}
	}*/
	
	@Override
	public void onClick(View v)
	{
		
		switch(v.getId())
		{
		case R.id.tourInfoToursListItemLL:
			viewTour(v);
			break;
		case R.id.editToursListItemButton:
			editTour(v);
			break;
		case R.id.delToursListItemImgButton:
			tourItemToDelete=list.getPositionForView((View) v.getParent());
			showDialog(0);
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
	
	private void viewTour(View v)
	{
		long tourID=((TourListAdapter.ViewHolder)((View)v.getParent()).getTag()).mTour.mID;
		Intent i=new Intent(ToursListActivity.this, ViewTourActivity.class);
		i.putExtra("isLocal", !isSearch);
		i.putExtra("tourID", tourID);
		startActivity(i);
	}
	
	private void editTour(View v)
	{
		long tourID=((TourListAdapter.ViewHolder)((View)v.getParent()).getTag()).mTour.mID;
		Intent i=new Intent(ToursListActivity.this, EditTourActivity.class);
		i.putExtra("tourID", tourID);
		startActivity(i);
	}
	
	private void confirmDeleteTour()
	{
		adapter.removeItem(tourItemToDelete);
		tourItemToDelete=-1;
	}
	private void cancelDeleteTour()
	{
		tourItemToDelete=-1;
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
	

	private class TourListAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		private ArrayList<TourHeader> mToursList;
		private boolean mIsSearch;
		
		public TourListAdapter(Context context, ArrayList<TourHeader> tours, boolean isSearch){
			mInflater= LayoutInflater.from(context);
			mToursList = tours;
			mIsSearch=isSearch;
		}
		
		public void removeItem(int i)
		{
			if(i>=0&&i < mToursList.size())
			{
				mToursList.remove(i);
				notifyDataSetChanged();
			}
		}
		
		@Override
		public int getCount() {
			return mToursList.size();
		}

		@Override
		public TourHeader getItem(int i) {
			
			return mToursList.get(i);
		}

		@Override //must be implemented from BaseAdapter
		public long getItemId(int i) {		
			return i;
		}
		
		
		@Override
		public View getView(int i, View v, ViewGroup group)
		{
			final ViewHolder holder;
			if(v==null||v.getTag()==null)
			{
				v=mInflater.inflate(R.layout.tour_list_item, null);
				//holder contains references to the widgets, this is to minimize calls to findViewById
				holder = new ViewHolder();
				holder.mTour=getItem(i);
				holder.mTitle=(TextView)v.findViewById(R.id.tourNameTourListItemTextView);
				holder.mStopCount = (TextView)v.findViewById(R.id.stopCountTourListItemTextView);
				holder.mHStopCount = (TextView)v.findViewById(R.id.handicapStopsTourListItemTextView); 
				holder.mTransport=(TextView)v.findViewById(R.id.transportTourListItemTextView);
				//add onClickListeners to each widget
				v.findViewById(R.id.tourInfoToursListItemLL).setOnClickListener(ToursListActivity.this);
				v.findViewById(R.id.editToursListItemButton).setOnClickListener(ToursListActivity.this);
				v.findViewById(R.id.delToursListItemImgButton).setOnClickListener(ToursListActivity.this);
				
				v.setTag(holder);
			}
			else
			{
				holder=(ViewHolder)v.getTag();
			}
			
			holder.mTitle.setText(holder.mTour.mTitle);
			holder.mStopCount.setText("Stops: "+holder.mTour.mStopCount);
			holder.mHStopCount.setText("Handicap Stops: "+holder.mTour.mHandicapStopCount);
			holder.mTransport.setText((holder.mTour.isDriving?"Driving":"Walking"));

			if(mIsSearch)
			{
				v.findViewById(R.id.delToursListItemImgButton).setVisibility(View.GONE);
				v.findViewById(R.id.editToursListItemButton).setVisibility(View.GONE);
			}
			else// if tour is downloaded
			{
				findViewById(R.id.editToursListItemButton).setVisibility(holder.mTour.isDownloaded?View.GONE:View.VISIBLE);	
			}
	
			
			return v;
		}
		
		public class ViewHolder{
			TourHeader mTour;
			TextView mTitle;
			TextView mStopCount;
			TextView mHStopCount;
			TextView mTransport;
		}
	}

}
