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

import com.iCompute.tour.backend.ToursManager;
import com.iCompute.tour.objects.Common.Access;
import com.iCompute.tour.objects.Tour;
import com.iCompute.tour.objects.ToursList;

//TODO figure out how to distinguish between an add and an update
//TODO When we're updating, pass in the ID of the tour via the intent

public class EditTourActivity extends Activity implements View.OnClickListener{
	
	private static final int SAVE_DIALOG = 0;
	private static final int DISCARD_DIALOG = 1;
	private static final int INVALID_TOUR_DIALOG = 2;
	
	private long tourID;
	private ToursManager manager;
	private Tour mTour;
	private boolean updating;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_tour_layout);
		//get a reference to the tours list manager
		manager = ((TourApplication)getApplication()).getToursManager();
		
		/*
		 * if we're updating, check the id of the tour passed via the intent,
		 * pull the relevant tour from the list by its id and auto populate the fields
		 * also pass in a flag so we can call the appropriate function when we hit 'save'
		 */
		Intent i = getIntent();
		tourID = i.getLongExtra("tourID", -1);
		if(tourID!=-1){ //updating
			updating = true;
			mTour = manager.getTour(tourID);
			((EditText)findViewById(R.id.nameEditTourEditText)).setText(mTour.getTitle().toString());
			((EditText)findViewById(R.id.descriptionEditTourEditText)).setText(mTour.getDescription().toString());
			((EditText)findViewById(R.id.tagsEditTourEditText)).setText(mTour.getTags().toString());
			if(!mTour.isDriving){
				((RadioButton)findViewById(R.id.walkingEditTourRadioButton)).setChecked(true);
			}
			else{
				((RadioButton)findViewById(R.id.drivingEditTourRadioButton)).setChecked(true);
			}
		}
		else
		{  //not updating
			//mTour=manager.getTemporaryTour();
			//tourID=mTour.getTourID(); //not necessary when not updating, this is why it's crashing
		}
		
		
		((Button) findViewById(R.id.saveEditTourButton)).setOnClickListener(this);
		((Button) findViewById(R.id.discardEditTourButton)).setOnClickListener(this);
		((Button) findViewById(R.id.addStopEditTourButton)).setOnClickListener(this);
		((Button) findViewById(R.id.imagesEditTourButton)).setVisibility(View.INVISIBLE);//.setOnClickListener(this);
		((Button) findViewById(R.id.editStopsEditTourButton)).setOnClickListener(this);
		
	}
	
	@Override
	protected Dialog onCreateDialog(int i)
	{
		Dialog dialog;
		
		switch(i){
		case SAVE_DIALOG:
			dialog = saveButtonClick();
			break;
		case DISCARD_DIALOG:
			dialog = discardButtonClick();
			break;
		case INVALID_TOUR_DIALOG:
			dialog=invalidTourDialog();
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
			if(validateTour())
				showDialog(SAVE_DIALOG);
			else
				showDialog(INVALID_TOUR_DIALOG);
			break;
		case R.id.discardEditTourButton:
			showDialog(DISCARD_DIALOG);
			break;
		case R.id.addStopEditTourButton:
			//TODO my location or on map location dialog
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
	
	private void addTourStop()
	{
		Intent intent=new Intent(getBaseContext(), EditTourStopActivity.class);
		intent.putExtra("tourID", tourID);
		startActivity(intent);
	}
	
	private void editStops()
	{
		Intent intent=new Intent(this, EditTourStopsListActivity.class);
		intent.putExtra("tourID", tourID);
		startActivity(intent);
	}
	
	private void addMedia()
	{
		Intent intent=new Intent(this, ImageSelectorActivity.class);
		intent.putExtra("tourID", tourID);
		startActivity(intent);
	}
	
	private AlertDialog saveButtonClick()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Save Tour");
		builder.setMessage("Would you like to save locally or save locally and publish?");
		
		builder.setPositiveButton("Save Local", new Dialog.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				saveTour(false);
			}
		});
		builder.setNeutralButton("Publish", new Dialog.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				saveTour(true);
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
	
	private AlertDialog discardButtonClick()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Discard Changes?");
		
		builder.setPositiveButton("Discard", new Dialog.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				discardTourChanges();
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
	
	private AlertDialog invalidTourDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Invalid Tour");
		builder.setMessage("Make sure that there is a tour name and description");
		
		builder.setNeutralButton("OK", new Dialog.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		return builder.create();
	}
	
	private boolean validateTour()
	{
		String name = ((EditText)findViewById(R.id.nameEditTourEditText)).getText().toString();
		String description = ((EditText)findViewById(R.id.descriptionEditTourEditText)).getText().toString();
		if(name==null||name==""||description==null||description=="")
		{
			return false;
		}
		else
		{
			String tags = ((EditText)findViewById(R.id.tagsEditTourEditText)).getText().toString();
			boolean isWalk = ((RadioButton)findViewById(R.id.walkingEditTourRadioButton)).isChecked();
			mTour = new Tour();
			mTour.mTitle=name;
			mTour.mDescription=description;
			mTour.mTags=tags;
			mTour.isDriving=(isWalk?false:true);
			
			return true;
		}
	}
	
	private void saveTour(boolean publish){
		
		if(updating){
			manager.updateTour(tourID, mTour);
		}
		else
		{
			manager.saveTemporaryTour(tourID, mTour);
		}
		
		if(publish)
		{
			manager.publishToServer(tourID);
			Toast.makeText(getBaseContext(), "Publishing tour to server", Toast.LENGTH_SHORT).show();
		}
		finish();
		
	}
	
	private void discardTourChanges()
	{
		manager.discardTemporaryTour(tourID);
		Toast.makeText(getBaseContext(), "Discarded Changes", Toast.LENGTH_SHORT).show();
		finish();
	}
}
