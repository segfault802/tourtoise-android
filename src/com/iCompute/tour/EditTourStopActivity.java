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

	private Boolean optionsExpanded=false;
	private Button addOptButton;
	private View optionsStub;
	private Spinner ageMin;
	private Spinner ageMax;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_stop_layout);
		View map = findViewById(R.id.mapViewHolder);
		addOptButton = (Button) findViewById(R.id.editStopAddOptButton);
		Button media = (Button) findViewById(R.id.editStopMediaButton);
		
		addOptButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				toggleOptions();
			}
		});
		media.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				addMedia();
			}
		});
	}
	
	private void addMedia()
	{
		startActivity(new Intent(this, ImageSelectorActivity.class));
	}
	
	private void toggleOptions()
	{
		if(!optionsExpanded)
		{
			if(optionsStub==null)
			{
				optionsStub = ((ViewStub)findViewById(R.id.additionalOptionStub)).inflate();
				
				ageMin = (Spinner) findViewById(R.id.editStopMinAgeSpinner);
				ageMax = (Spinner) findViewById(R.id.editStopMaxAgeSpinner);
				ArrayAdapter<CharSequence> ageMinAdapt = ArrayAdapter.createFromResource(this, R.array.age_range, android.R.layout.simple_spinner_item);
				ArrayAdapter<CharSequence> ageMaxAdapt = ArrayAdapter.createFromResource(this, R.array.age_range, android.R.layout.simple_spinner_item);
				
				ageMinAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    ageMin.setAdapter(ageMinAdapt);
			    ageMaxAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    ageMax.setAdapter(ageMaxAdapt);
				
			}
			else
			{
				optionsStub.setVisibility(View.VISIBLE);
			}
			optionsExpanded=true;
			addOptButton.setText("Hide Additional Options");
		}
		else
		{
			optionsStub.setVisibility(View.GONE);
			optionsExpanded=false;
			addOptButton.setText("Show Additional Options");
		}
	}
}
