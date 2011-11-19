package com.iCompute.tour;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchToursActivity extends Activity {

	EditText categories;
	boolean[] clickedCategories;
	String[] categoryStrings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_layout);
		Button searchBtn=(Button)findViewById(R.id.searchSearchButton);
		searchBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				searchTours();
			}
		});
		categoryStrings=getResources().getStringArray(R.array.stop_categories_array);
		clickedCategories=new boolean[categoryStrings.length];
		categories=(EditText)findViewById(R.id.categoriesSearchEditText);
		categories.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				showDialog(0);
			}
		});
		categories.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					showDialog(0);
			}
		});
			
	}
	
	@Override
	public Dialog onCreateDialog(int i)
	{
		Dialog d=createDialog();
		
		return d;
	}
	
	
	private Dialog createDialog()
	{
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("Select Categories");
		builder.setMultiChoiceItems(categoryStrings, clickedCategories, new OnMultiChoiceClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				clickedCategories[which]=isChecked;
			}
		});
		builder.setPositiveButton("OK", new Dialog.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setCategories();
				dialog.dismiss();
			}
		});
		
		return builder.create();
	}
	
	private void setCategories()
	{
		StringBuilder sb=new StringBuilder();
		for(int i=0; i<clickedCategories.length;i++)
		{
			if(clickedCategories[i])
			{
				sb.append((sb.length()==0?"":", ")+categoryStrings[i]);
			}
		}
		categories.setText(sb.toString());
	}
	
	private void searchTours()
	{
		Intent i=new Intent(this, ToursListActivity.class);
		i.putExtra("isLocal", false);
		startActivity(i);
	}
	
}
