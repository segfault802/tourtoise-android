package com.iCompute.tour;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class EditTourStopsListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_tour_stops_list_layout);
		//ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.temp_stop_names, R.layout.edit_stops_list_item);
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, R.layout.edit_stops_list_item, R.id.editStopsListItemStopNameTextView);
		
		CharSequence[] stops = getResources().getStringArray(R.array.temp_stop_names);
		for(CharSequence str:stops)
		{
			adapter.add(str);
		}
		getListView().setAdapter(adapter);
		setListListeners();
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
	
	private void itemClicked(int id)
	{
		
	}
}
