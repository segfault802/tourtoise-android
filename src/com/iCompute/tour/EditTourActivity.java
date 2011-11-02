package com.iCompute.tour;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EditTourActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_tour_layout);
		Button save = (Button) findViewById(R.id.saveTourButton);
		Button discard = (Button) findViewById(R.id.discardTourButton);
		Button add = (Button) findViewById(R.id.addTourStopButton);
		Button media = (Button) findViewById(R.id.addTourImagesButton);
		Button edit = (Button) findViewById(R.id.editTourStopsButton);
		
		save.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				showDialog(0);
			}
		});
		discard.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				showDialog(1);
			}
		});
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addTourStop();
			}
		});
		media.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addMedia();
			}
		});
		edit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editStops();
			}
		});
	}
	
	@Override
	protected Dialog onCreateDialog(int i)
	{
		Dialog dialog;
		
		switch(i){
		case 0:
			dialog = saveButtonClick();
			break;
		case 1:
			dialog = discardButtonClick();
			break;
		default:
			dialog = null;
			
		}
		return dialog;
	}
	
	private AlertDialog saveButtonClick()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Save Tour");
		builder.setMessage("Would you like to save locally or save locally and publish?");
		
		builder.setPositiveButton("Save Local", new Dialog.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				saveTourLocal();
				EditTourActivity.this.finish();
			}
		});
		builder.setNeutralButton("Publish", new Dialog.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				publishTour();
				EditTourActivity.this.finish();
			}
		});
		//builder.setCancelable(false);
		builder.setNegativeButton("Cancel", new Dialog.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		
		return builder.create();
	}

	
	private void editStops()
	{
		startActivity(new Intent(this, EditTourStopsListActivity.class));
	}
	
	private void addMedia()
	{
		startActivity(new Intent(this, ImageSelectorActivity.class));
	}
	
	private AlertDialog discardButtonClick()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Discard Changes?");
		
		builder.setPositiveButton("Discard", new Dialog.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				discardTourChanges();
				EditTourActivity.this.finish();
			}
		});
		builder.setNegativeButton("Cancel", new Dialog.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		
		return builder.create();
	}
	
	private void addTourStop()
	{
		//my location or on map dialog
		startActivity(new Intent(getBaseContext(), EditTourStopActivity.class));
	}
	
	private void saveTourLocal()
	{
		Toast.makeText(getBaseContext(), "Save Tour Locally", Toast.LENGTH_SHORT).show();
	}
	private void publishTour()
	{
		Toast.makeText(getBaseContext(), "Publish Tour", Toast.LENGTH_SHORT).show();
		saveTourLocal();
	}
	private void discardTourChanges()
	{
		Toast.makeText(getBaseContext(), "Discarded Changes", Toast.LENGTH_SHORT).show();
	}
}
