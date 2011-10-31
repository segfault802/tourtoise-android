package com.iCompute.tour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BuildaTourActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button create= (Button)findViewById(R.id.createTourButton);
        
        create.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				createTour();
			}
        	
        });
    }
    
    private void createTour()
    {
    	startActivity(new Intent(getBaseContext(), EditTourActivity.class));
    }
}