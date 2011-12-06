package com.iCompute.tour;

import java.util.ArrayList;
import java.util.LinkedList;

import com.iCompute.tour.backend.ToursManager;
import com.iCompute.tour.objects.Stop;
import com.iCompute.tour.objects.StopList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EditTourStopsListActivity extends ListActivity implements View.OnClickListener{

	private static final int EDIT_STOP = 1000;
	private static final int ADD_STOP = 1001;
	private ListView list;
	private StopsListAdapter adapter;
	
	private long tourID=-1;
	private int itemToDelete=-1;
	private ToursManager manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_tour_stops_list_layout);
		manager=((TourApplication)getApplication()).getToursManager();
		
		Intent intent=getIntent();
		tourID=intent.getLongExtra("tourID", -1);
		if(tourID==-1)
		{
			Toast.makeText(this, "No tour ID given", Toast.LENGTH_LONG).show();
			finish();
		}
		
		list=getListView();
		adapter = new StopsListAdapter(this, manager.getTourStops(tourID));
		manager.cacheTour(tourID);
		list.setAdapter(adapter);
		
		findViewById(R.id.myLocEditStopsListButton).setOnClickListener(this);
		findViewById(R.id.onMapEditStopsListButton).setOnClickListener(this);
	}
	
	@Override
	protected void onDestroy()
	{
		manager.releaseTour(tourID);
		super.onDestroy();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode==RESULT_OK)
		{
			adapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.myLocEditStopsListButton:
			addStop(true);
			break;
		case R.id.onMapEditStopsListButton:
			addStop(false);
			break;
		}
	}
	
	private void addStop(boolean myLoc)
	{
		Intent intent=new Intent(this, EditTourStopActivity.class);
		intent.putExtra("tourID", tourID);
		intent.putExtra("myLocation", myLoc);
		startActivityForResult(intent, ADD_STOP);
	}
	
	@Override
	protected Dialog onCreateDialog(int i)
	{
		Dialog d;
		switch(i)
		{
		case 0:
			d=buildDeleteConfirmationDialog();
			break;
		default:
			d=null;
			break;
		}
		return d;
	}
	
	private Dialog buildDeleteConfirmationDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Delete Item");
		builder.setMessage("Are you sure you would like to delete the item?");
		builder.setPositiveButton("OK", new Dialog.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				confirmDeleteStopItem();
			}
		});
		builder.setNegativeButton("Cancel", new Dialog.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				cancelDeleteStopItem();
			}
		});
		builder.setOnCancelListener(new Dialog.OnCancelListener(){
			@Override
			public void onCancel(DialogInterface arg0) {
				cancelDeleteStopItem();
			}
		});
		
		return builder.create();
	}
	
	private void confirmDeleteStopItem()
	{
		adapter.removeItem(itemToDelete);
		itemToDelete=-1;
	}
	
	private void cancelDeleteStopItem()
	{
		itemToDelete=1;
	}
	/*
	 * Click listeners for list items declared in list item xml layout
	 */
	public void editStopClicked(View v)
	{
		long stopID=((StopsListAdapter.ViewHolder)((View) v.getParent()).getTag()).mStop.getStopID();
		Intent intent=new Intent(this, EditTourStopActivity.class);
		intent.putExtra("stopID", stopID);
		intent.putExtra("tourID", tourID);
		
		startActivityForResult(intent, EDIT_STOP);
	}
	
	public void deleteButtonClicked(View v)
	{
		itemToDelete=list.getPositionForView((View) v.getParent());
		showDialog(0);
	}
	
	public void moveItem(View v)
	{
		int pos=list.getPositionForView((View) v.getParent());
		if(v.getId()==R.id.mvUpEditTourStopsListItemImgButton)
			adapter.moveItem(pos, true);
		else if(v.getId()==R.id.mvDownEditTourStopsListItemImgButton)
			adapter.moveItem(pos, false);
	}
	
	private class StopsListAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		private StopList mStops;
		
		public StopsListAdapter(Context context, StopList stops){
			mInflater= LayoutInflater.from(context);
			mStops=stops;
		}
		
		public void removeItem(int i)
		{
			if(i>=0&&i<mStops.size()-1)
			{
				mStops.remove(i);
				this.notifyDataSetChanged();
			}
		}
		
		public void moveItem(int pos, Boolean mvUp)
		{
			if(mStops.changeOrder(pos, mvUp))
			{
				notifyDataSetChanged();
			}
			
			/*
			String temp;
			if(mvUp&&pos!=0)
			{
				temp=mStops.get(pos);
				mStops.remove(pos);
				mStops.add(pos-1, temp);
				this.notifyDataSetChanged();
			}
			else if(!mvUp&&pos!=mStops.size()-1)
			{
				temp=mStops.get(pos);
				mStops.remove(pos);
				mStops.add(pos+1, temp);
				this.notifyDataSetChanged();
			}*/
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
			
			holder.mStop=getItem(i);
			holder.mTitle.setText(holder.mStop.mTitle);
			holder.mAudioIndicator.setVisibility(View.GONE); //View.GONE or View.VISIBLE
			holder.mPicIndicator.setVisibility(View.GONE); //View.GONE or View.VISIBLE
			//holder.mStopIcon.setImageResource(resId); //set icon image from resource
			
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
