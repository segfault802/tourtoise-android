package com.iCompute.tour;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
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

public class EditTourStopsListActivity extends ListActivity {

	private ListView list;
	private StopsListAdapter adapter;
	private int itemToDelete=-1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_tour_stops_list_layout);
		
		list=getListView();
		adapter = new StopsListAdapter(this, getResources().getStringArray(R.array.temp_stop_names));
		list.setAdapter(adapter);
		setListListeners();
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
	
	private void setListListeners()
	{
		ListView list = getListView();
		list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int id, long pos) {
				itemClicked(id);
			}
		});
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
	
	private void itemClicked(int id)
	{
		//start editStopActivity for result
	}
	
	private class StopsListAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		private ArrayList<String> mStops;
		
		public StopsListAdapter(Context context, String[] strings){
			mInflater= LayoutInflater.from(context);
			mStops=new ArrayList<String>();
			for(String str:strings)
			{
				mStops.add(str);
			}
		}
		
		public void removeItem(int i)
		{
			if(i>=0&&i<mStops.size()-1)
			{
				mStops.remove(i);
				this.notifyDataSetChanged();
			}
		}
		
		public ArrayList<String> getItems()
		{
			return mStops;
		}
		
		public void moveItem(int pos, Boolean mvUp)
		{
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
			}
		}
		
		@Override
		public int getCount() {
			return mStops.size();
		}

		@Override
		public CharSequence getItem(int i) {
			
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
				holder.mAudioIndicator=(ImageView)view.findViewById(R.id.editStopsListItemAudioIcon);
				holder.mPicIndicator=(ImageView)view.findViewById(R.id.editStopsListItemImageIcon);
				
				view.setTag(holder);
			}else{
				holder=(ViewHolder)view.getTag();
			}
			
			holder.mStop= getItem(i);
			holder.mTitle.setText(holder.mStop);
			//holder.mAudioIndicator.setVisibility(visibility); View.GONE or View.VISIBLE
			//holder.mPicIndicator.setVisibility(visibility); View.GONE or View.VISIBLE
			//holder.mStopIcon.setImageResource(resId); //set icon image from resource
			
			view.setTag(holder);
			
			return view;
		}
		
		public class ViewHolder{
			CharSequence mStop;
			ImageView mStopIcon;
			TextView mTitle;
			ImageView mPicIndicator;
			ImageView mAudioIndicator;
		}
	}
}
