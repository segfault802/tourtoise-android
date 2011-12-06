package com.iCompute.tour;

import com.iCompute.tour.backend.ToursManager;
import com.iCompute.tour.objects.Media;
import com.iCompute.tour.objects.Stop;
import com.iCompute.tour.objects.Tour;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class ViewTourActivity extends ListActivity implements View.OnClickListener{

	ListView list;
	Button downloadBtn;
	Button stopListBtn;
	Button nextBtn;
	
	private boolean isLocal;
	private long tourID=-1;
	
	private ToursManager manager;
	private Tour mTour;
	private Media tourMedia;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_tour_layout);
		manager=((TourApplication)getApplication()).getToursManager();
		Intent intent=getIntent();
		tourID=intent.getLongExtra("tourID", -1);
		
		mTour=manager.getTour(tourID);
		//TODO apply media to adapter if time
		tourMedia=manager.getMediaForTour(tourID);
		Gallery gallery = (Gallery) findViewById(R.id.imagesViewTourGallery);
	    gallery.setAdapter(new ImageAdapter(this));
	    
	    //Stops list set up
	    list=getListView();
	    ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, manager.getTourStopNames(tourID));
		list.setAdapter(adapter);
		
		downloadBtn=(Button)findViewById(R.id.downloadTourViewTourButton);
		stopListBtn=(Button)findViewById(R.id.viewStopsViewTourButton);
		nextBtn=(Button)findViewById(R.id.viewNextViewTourButton);
		
		downloadBtn.setOnClickListener(this);
		stopListBtn.setOnClickListener(this);
		nextBtn.setOnClickListener(this);
		
		isLocal=intent.getBooleanExtra("isLocal", false);
		layoutIsLocalTour(isLocal);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.viewStopsViewTourButton:
			//Toast.makeText(ViewTourActivity.this, "Show all stops", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(this, ViewTourStopsListActivity.class));
			break;
		case R.id.viewNextViewTourButton:
			//Toast.makeText(ViewTourActivity.this, "Show next stop", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(this, ViewStopActivity.class));
			break;
		case R.id.downloadTourViewTourButton:
			downloadTour();
			break;
		}
		
	}
	private void viewStopsList()
	{
		Intent i=new Intent(this, ViewTourStopsListActivity.class);
		i.putExtra("tourID", tourID);
		startActivity(i);
	}
	
	private void viewNextStop()
	{
		long nextID=mTour.getNextStop().getStopID();
		Intent i=new Intent(this, ViewStopActivity.class);
		i.putExtra("stopID", nextID);
		i.putExtra("tourID", tourID);
		startActivity(i);
	}
	
	private void layoutIsLocalTour(boolean local)
	{
		if(local)
		{
			list.setVisibility(View.GONE);
			downloadBtn.setVisibility(View.GONE);
			stopListBtn.setVisibility(View.VISIBLE);
			nextBtn.setVisibility(View.VISIBLE);
		}
		else
		{
			list.setVisibility(View.VISIBLE);
			downloadBtn.setVisibility(View.VISIBLE);
			stopListBtn.setVisibility(View.GONE);
			nextBtn.setVisibility(View.GONE);
		}
	}
	
	
	private void downloadTour()
	{
		//TODO download tour functionality
		isLocal=true;
		layoutIsLocalTour(isLocal);
	}
	

	public class ImageAdapter extends BaseAdapter {
	    int mGalleryItemBackground;
	    private Context mContext;

	    private Integer[] mImageIds = {
	            R.drawable.icon
	    };

	    public ImageAdapter(Context c) {
	        mContext = c;
	        //TypedArray attr = mContext.obtainStyledAttributes(R.styleable.HelloGallery);
	        //mGalleryItemBackground = attr.getResourceId(
	        //        R.styleable.HelloGallery_android_galleryItemBackground, 0);
	        //attr.recycle();
	    }

	    public int getCount() {
	        return mImageIds.length;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView = new ImageView(mContext);

	        imageView.setImageResource(mImageIds[position]);
	        imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
	        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	        //imageView.setBackgroundResource(mGalleryItemBackground);

	        return imageView;
	    }
	}

	
}
