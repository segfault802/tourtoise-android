package com.iCompute.tour;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EditTourStopActivity extends Activity {

	private Boolean optionsExpanded=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_stop_layout);
		View map = findViewById(R.id.mapViewHolder);
		Button addOptButton = (Button) findViewById(R.id.editStopAddOptButton);
		
		addOptButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				toggleOptions();
			}
		});
	}
	
	private void toggleOptions()
	{
		
	}
}
