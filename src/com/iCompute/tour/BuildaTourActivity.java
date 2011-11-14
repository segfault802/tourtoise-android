package com.iCompute.tour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.iCompute.tour.backend.JSONManager;

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
        Button b = (Button)findViewById(R.id.button2);
        b.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v) {
        		JSONManager m = new JSONManager();
        		m.getToursJSON();
        	}
        });
        
        Button viewTour = (Button) findViewById(R.id.button3);
        viewTour.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				viewTour();
			}
        });
    }
    
    private void createTour()
    {
    	startActivity(new Intent(getBaseContext(), EditTourActivity.class));
    }
    
    private void viewTour()
    {
    	startActivity(new Intent (getBaseContext(), ViewTourActivity.class));
    }
}