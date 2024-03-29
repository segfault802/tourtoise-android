package com.iCompute.tour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.iCompute.tour.backend.JSONManager;

public class BuildaTourActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        
        
        Button b = (Button)findViewById(R.id.jsonMainButton);
        b.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v) {
        		JSONManager m = new JSONManager();
        		m.fetchToursList();
        		//m.fetchTourById(1);
        	}
        });
        
        //temp buttons for testing only
        findViewById(R.id.createTourButton).setOnClickListener(this);
        findViewById(R.id.createTourButton).setOnClickListener(this);
        findViewById(R.id.viewtourmainButton).setOnClickListener(this);
        
        //actual buttons
        findViewById(R.id.addTourMainImgButton).setOnClickListener(this);
        findViewById(R.id.searchMainImgButton).setOnClickListener(this);
        findViewById(R.id.myToursMainImgButton).setOnClickListener(this);
        findViewById(R.id.settingsMainImgButton).setOnClickListener(this);
    }
    
    
    @Override
    public void onClick(View v)
    {
    	switch(v.getId()){
    	case R.id.viewtourmainButton://button for testing
    		viewTour();
    		break;
    	case R.id.settingsMainImgButton:
    		openSettings();
    		break;
    	case R.id.myToursMainImgButton:
    		myTours();
    		break;
    	case R.id.searchMainImgButton:
    		searchTours();
    		break;
    	case R.id.createTourButton: //button for testing
    	case R.id.addTourMainImgButton:
    		createTour();
    		break;
    		
    	}
    }
    
    private void createTour()
    {
    	startActivity(new Intent(getBaseContext(), EditTourActivity.class));
    }
    
    private void viewTour()
    {
    	Intent i=new Intent (getBaseContext(), ViewTourActivity.class);
    	i.putExtra("isLocal", true);
    	startActivity(i);
    }
    private void searchTours()
    {
    	startActivity(new Intent(getBaseContext(), SearchToursActivity.class));
    }
    private void myTours()
    {
    	Intent i=new Intent(getBaseContext(), ToursListActivity.class);
    	i.putExtra("myTours", true);
    	startActivity(i);
    }
    private void openSettings()
    {
    	Toast.makeText(this, "This Button will open settings(english vs metric)",Toast.LENGTH_LONG).show(); 
    }
}