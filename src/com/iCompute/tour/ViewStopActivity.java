package com.iCompute.tour;


import com.iCompute.tour.backend.ToursManager;
import com.iCompute.tour.objects.Media;
import com.iCompute.tour.objects.Stop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ViewStopActivity extends Activity  implements View.OnClickListener{

	private long tourID=-1;
	private long stopID=-1;
	private Stop mStop;
	private ToursManager manager;
	private Media media;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_stop_layout);
		manager=((TourApplication)getApplication()).getToursManager();
		Intent intent=getIntent();
		tourID=intent.getLongExtra("tourID", -1);
		stopID=intent.getLongExtra("stopID", -1);
		
		mStop=manager.getStop(tourID, stopID);
		media=manager.getMediaForStop(tourID, stopID);
		
		//TODO apply media to layout
		Gallery gallery = (Gallery) findViewById(R.id.imagesViewStopGallery);
	    gallery.setAdapter(new ImageAdapter(this));
	
	    findViewById(R.id.backViewStopImgButton).setOnClickListener(this);
	    findViewById(R.id.mapViewStopImgButton).setOnClickListener(this);
	    findViewById(R.id.visitedViewStopImgButton).setOnClickListener(this);
	    findViewById(R.id.skipViewStopImgButton).setOnClickListener(this);
	    
	}
	
	private void setUpLayout()
	{
		//TODO apply stop data to layout
	}
	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.backViewStopImgButton:
			finish();
			break;
		case R.id.mapViewStopImgButton:
			break;
		case R.id.visitedViewStopImgButton:
			break;
		case R.id.skipViewStopImgButton:
			break;
		}
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
