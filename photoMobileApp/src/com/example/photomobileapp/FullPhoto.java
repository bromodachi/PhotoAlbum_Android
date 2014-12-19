package com.example.photomobileapp;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import cs213.photoalbum.control.InteractiveControl;
import cs213.photoalbum.model.IAlbum;
import cs213.photoalbum.model.IPhoto;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FullPhoto extends Activity {
	int position;
	int albumPosition;
	IPhoto test;
	ArrayList<String> locationArray = new ArrayList<String>();
	ArrayList<String> peopleArray = new ArrayList<String>();
	List<String> tempArray= new ArrayList<String>();
	String mt;
	int personPosition=-1;
	int locationPosition=-1;
	int photoPos;
	ArrayAdapter<String> adapter;
	InteractiveControl control;
	ArrayAdapter<String> adapter2;
	Dialog dialog;

	public void onCreate(Bundle savedInstanceState) {
		System.out.println("djsdjasf");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_photo);
		Intent intent = getIntent();
		test = (IPhoto) intent.getSerializableExtra("photo");
		//control=(InteractiveControl) intent.getParcelableExtra("theControl");
		photoPos = intent.getExtras().getInt("photoPos");
		//albumPosition=intent.getExtras().getInt("albumPosition");
		ImageView image = (ImageView) findViewById(R.id.fullImage);
		image.setImageBitmap(BitmapFactory.decodeFile(test.getFile()
				.getAbsolutePath()));
		
		 dialog = new Dialog(this);
		dialog.setContentView(R.layout.tag_dialog);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, locationArray);

		final ListView locationList = (ListView) findViewById(R.id.listView1);
		locationList.setAdapter(adapter);
		locationList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, peopleArray);

		final ListView peopleList = (ListView) findViewById(R.id.listView2);
		peopleList.setAdapter(adapter2);
		peopleList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		locationArray.add(test.getLocationTag());
		tempArray=test.getPeopleTags();
		for(int i=0; i<tempArray.size();i++)
		peopleArray.add(tempArray.get(i));
		
		
		
		locationList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
				locationPosition = position;
			
			}
		});

		peopleList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				personPosition = position;
			
			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.full_photo_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.add_tag:
		
			addTag();
			return true;
		case R.id.backSingle:
			backToAlbums();
			return true;
		case R.id.delete_tag:
			
			deleteTag();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void deleteTag() {
		if(locationPosition!=-1)
		if (!locationArray.isEmpty()){
			locationArray.remove(locationPosition);
			locationPosition=-1;
			
		adapter.notifyDataSetChanged();
		}
		
		if(personPosition!=-1){
			peopleArray.remove(personPosition);
			personPosition=-1;
			adapter2.notifyDataSetChanged();
			
		}

	}

	private void backToAlbums() {
		Intent returnIntent = new Intent();
		returnIntent.putExtra("photo", test);
		returnIntent.putExtra("photoPos", photoPos);
		setResult(50, returnIntent);
		finish();

	}

	private void addTag() {

	
		

		dialog.setTitle("Add Tags");
	
		Button ok = (Button) dialog.findViewById(R.id.Ok);
		Button cancel = (Button) dialog.findViewById(R.id.Cancel);
		final EditText tag = (EditText) dialog.findViewById(R.id.TagText);
		final CheckBox location = (CheckBox) dialog.findViewById(R.id.Location);
		final CheckBox people = (CheckBox) dialog.findViewById(R.id.People);
		
		// if button is clicked, close the custom dialog
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean existFlag=false;
				ImageView image = (ImageView) findViewById(R.id.fullImage);
				image.setImageBitmap(BitmapFactory.decodeFile(test.getFile()
						.getAbsolutePath()));
				
				String tagInput = tag.getText().toString();
				if (location.isChecked()) {
					if (locationArray.size() == 0) {
						test.setLocationTag(tagInput);
						locationArray.add(tagInput);
					}

				}
				if (people.isChecked()) {
					for(int i=0; i<peopleArray.size();i++)
						if(peopleArray.get(i).equals(tagInput))
							existFlag=true;
							
					if(!existFlag){
					test.personTag(tagInput);
					peopleArray.add(tagInput);
					}
					
				}
				adapter.notifyDataSetChanged();
				adapter2.notifyDataSetChanged();
				dialog.dismiss();
			}
		});

		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();

	}
}