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

public class EditTourActivity extends Activity implements View.OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_tour_layout);
		
		((Button) findViewById(R.id.saveTourButton)).setOnClickListener(this);
		((Button) findViewById(R.id.discardTourButton)).setOnClickListener(this);
		((Button) findViewById(R.id.addTourStopButton)).setOnClickListener(this);
		((Button) findViewById(R.id.addTourImagesButton)).setOnClickListener(this);
		((Button) findViewById(R.id.editTourStopsButton)).setOnClickListener(this);
		
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
	
	public void onClick(View v)
	{
		switch(v.getId()){
		case R.id.saveTourButton:
			showDialog(0);
			break;
		case R.id.discardTourButton:
			showDialog(1);
			break;
		case R.id.addTourStopButton:
			addTourStop();
			break;
		case R.id.addTourImagesButton:
			addMedia();
			break;
		case R.id.editTourStopsButton:
			editStops();
			break;
		default:
			break;
		
		}
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
