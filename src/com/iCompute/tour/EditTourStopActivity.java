package com.iCompute.tour;

import com.iCompute.tour.backend.ToursManager;
import com.iCompute.tour.objects.Stop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditTourStopActivity extends Activity implements View.OnClickListener{

	private static final int DISCARD_DIALOG = 0;
	private static final int INVALID_STOP_DIALOG = 1;
	
	private ToursManager manager;
	private long tourID=-1;
	private long stopID=-1;
	private boolean isNew=true;
	private Stop mStop;
	
	private Spinner ageSpinner;
	private Spinner catSpinner;
	private EditText stopTitle;
	private EditText stopDescription;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_stop_layout);
		View map = findViewById(R.id.mapEditStopViewHolder);
		manager=((TourApplication)getApplication()).getToursManager();
		
		Intent intent=getIntent();
		tourID=intent.getLongExtra("tourID", -1);
		stopID=intent.getLongExtra("stopID", -1);
		isNew=(stopID==-1);
		
		findViewById(R.id.mediadEditStopButton).setOnClickListener(this);
		findViewById(R.id.saveEditStopButton).setOnClickListener(this);
		findViewById(R.id.discardEditStopButton).setOnClickListener(this);
		stopTitle=(EditText)findViewById(R.id.nameEditStopEditText);
		stopDescription=(EditText)findViewById(R.id.descriptionEditStopEditText);
		
		//age spinner setup
		ageSpinner = (Spinner) findViewById(R.id.ageEditStopSpinner);
		ArrayAdapter<CharSequence> ageMinAdapt = ArrayAdapter.createFromResource(this, R.array.age_range, android.R.layout.simple_spinner_item);
		ageMinAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ageSpinner.setAdapter(ageMinAdapt);	
	    
	    //category spinner setup
	    catSpinner=(Spinner)findViewById(R.id.categoryEditStopSpinner);
	    ArrayAdapter<CharSequence> catAdapter = ArrayAdapter.createFromResource(this, R.array.stop_categories_array, android.R.layout.simple_spinner_item);
	    catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    catSpinner.setAdapter(catAdapter);
	    
	    
	    if(!isNew)
		{
			loadStopToEdit(manager.getStop(tourID, stopID));
		}
	    else
	    {
	    	mStop=manager.getTemporaryStop(tourID);
	    	stopID=mStop.getStopID();
	    }
	    
	}
	
	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.mediadEditStopButton:
			addMedia();
			break;
		case R.id.saveEditStopButton:
			saveTourStop();
			break;
		case R.id.discardEditStopButton:
			showDialog(DISCARD_DIALOG);
			break;
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int i)
	{
		Dialog d;
		
		switch(i)
		{
		case DISCARD_DIALOG:
			d=discardChangesDialog();
			break;
		case INVALID_STOP_DIALOG:
			d=invalidStopStateDialog();
			break;
		default:
			d=null;
			break;
		}
		
		return d;
	}
	
	private void loadStopToEdit(Stop stop)
	{
		mStop=stop;
		//TODO set map location
		stopTitle.setText(stop.mTitle);
		stopDescription.setText(stop.mDescription);
		catSpinner.setSelection(stop.mCategory, false);
		ageSpinner.setSelection(stop.mAgeAccess, false);
		((CheckBox)findViewById(R.id.handicapEditStopCheckBox)).setChecked(stop.mAccessible);
	}
	
	private void saveTourStop()
	{
		if(validateStop())
		{
			if(isNew)
			{
				manager.addTourStop(tourID, mStop);
			}
			else
			{
				manager.updateStop(tourID, mStop);
			}
		}
		else
		{
			showDialog(INVALID_STOP_DIALOG);
		}
		
		
	}
	
	private boolean validateStop()
	{
		//TODO stuff for location
		String title=stopTitle.getText().toString();
		String description=stopDescription.getText().toString();
		if(title==null||title==""||description==null||description=="")
		{
			return false;
		}
		else
		{
			mStop.mTitle=title;
			mStop.mDescription=description;
			mStop.mAccessible=((CheckBox)findViewById(R.id.handicapEditStopCheckBox)).isChecked();
			mStop.mAgeAccess=ageSpinner.getSelectedItemPosition();
			mStop.mCategory=catSpinner.getSelectedItemPosition();
			
			return true;
		}
	}
	
	private void discardTourStop()
	{
		Toast.makeText(this, "Stop changes discarded", Toast.LENGTH_LONG).show();
		if(isNew)
			manager.discardTemporaryStop(stopID);
		finish();
	}
	
	private void addMedia()
	{
		Intent intent=new Intent(this, ImageSelectorActivity.class);
		intent.putExtra("tourID", tourID);
		intent.putExtra("stopID", stopID);
		startActivity(intent);
	}
	
	private AlertDialog discardChangesDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Discard Changes?");
		
		builder.setPositiveButton("Discard", new Dialog.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				discardTourStop();
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
	
	private AlertDialog invalidStopStateDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Invalid Stop");
		builder.setMessage("Make sure that there is a stop name and description");
		
		builder.setNeutralButton("OK", new Dialog.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		return builder.create();
	}
}
