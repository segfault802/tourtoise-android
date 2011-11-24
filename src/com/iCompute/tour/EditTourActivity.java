package com.iCompute.tour;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class EditTourActivity extends Activity implements View.OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_tour_layout);
		
		((Button) findViewById(R.id.saveEditTourButton)).setOnClickListener(this);
		((Button) findViewById(R.id.discardEditTourButton)).setOnClickListener(this);
		((Button) findViewById(R.id.addStopEditTourButton)).setOnClickListener(this);
		((Button) findViewById(R.id.imagesEditTourButton)).setOnClickListener(this);
		((Button) findViewById(R.id.editStopsEditTourButton)).setOnClickListener(this);
		
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
		case R.id.saveEditTourButton:
			//For now, we'll add the tour into the global list here
			//TODO figure out how to distinguish between an add and an update
			String name = ((EditText)findViewById(R.id.nameEditTourEditText)).getText().toString();
			String description = ((EditText)findViewById(R.id.descriptionEditTourEditText)).getText().toString();
			String tags = ((EditText)findViewById(R.id.tagsEditTourEditText)).getText().toString();
			boolean isWalk = ((RadioButton)findViewById(R.id.walkingEditTourRadioButton)).isChecked();
			boolean added = ((TourApplication)getApplication()).tours.add(new Tour(name,description,tags,isWalk));
			
			//showDialog(0);
			break;
		case R.id.discardEditTourButton:
			showDialog(1);
			break;
		case R.id.addStopEditTourButton:
			addTourStop();
			break;
		case R.id.imagesEditTourButton:
			addMedia();
			break;
		case R.id.editStopsEditTourButton:
			editStops();
			break;
		default:
			break;
		
		}
	}
	
	private void saveTour(){
		
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
		//TODO send the id of the tour we're editing in the intent
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
		//TODO save the tour locally somehow
		Toast.makeText(getBaseContext(), "Save Tour Locally", Toast.LENGTH_SHORT).show();
	}
	private void publishTour()
	{
		//TODO push the tour to the server
		Toast.makeText(getBaseContext(), "Publish Tour", Toast.LENGTH_SHORT).show();
		saveTourLocal();
	}
	private void discardTourChanges()
	{
		Toast.makeText(getBaseContext(), "Discarded Changes", Toast.LENGTH_SHORT).show();
	}
}
