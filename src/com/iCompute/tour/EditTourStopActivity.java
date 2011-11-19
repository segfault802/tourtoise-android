package com.iCompute.tour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class EditTourStopActivity extends Activity {

	
	private Spinner ageSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_stop_layout);
		View map = findViewById(R.id.mapEditStopViewHolder);
		
		Button media = (Button) findViewById(R.id.mediadEditStopButton);
		media.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				addMedia();
			}
		});
		
		//age spinner setup
		ageSpinner = (Spinner) findViewById(R.id.ageEditStopSpinner);
		ArrayAdapter<CharSequence> ageMinAdapt = ArrayAdapter.createFromResource(this, R.array.age_range, android.R.layout.simple_spinner_item);
		ageMinAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ageSpinner.setAdapter(ageMinAdapt);	
	    
	    //category spinner setup
	    ArrayAdapter<CharSequence> catAdapter = ArrayAdapter.createFromResource(this, R.array.stop_categories_array, android.R.layout.simple_spinner_item);
	    catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ((Spinner)findViewById(R.id.categoryEditStopSpinner)).setAdapter(catAdapter);
	    
	    
	}
	
	private void addMedia()
	{
		startActivity(new Intent(this, ImageSelectorActivity.class));
	}
	
	
}
