package com.iCompute.tour;

import android.app.ListActivity;
import android.content.Context;
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
import android.widget.Toast;

public class ViewTourActivity extends ListActivity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_tour_layout);
		
		Gallery gallery = (Gallery) findViewById(R.id.imagesViewTourGallery);
	    gallery.setAdapter(new ImageAdapter(this));

	    gallery.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView parent, View v, int position, long id) {
	            Toast.makeText(ViewTourActivity.this, "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });
	    ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.temp_stop_names));
		getListView().setAdapter(adapter);
		((Button)findViewById(R.id.viewNextViewTourButton)).setOnClickListener(this);
		((Button)findViewById(R.id.viewStopsViewTourButton)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.viewStopsViewTourButton:
			Toast.makeText(ViewTourActivity.this, "Show all stops", Toast.LENGTH_SHORT).show();
			break;
		case R.id.viewNextViewTourButton:
			Toast.makeText(ViewTourActivity.this, "Show next stop", Toast.LENGTH_SHORT).show();
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
