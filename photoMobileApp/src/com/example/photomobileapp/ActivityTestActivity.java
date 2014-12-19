package com.example.photomobileapp;

import java.io.File;
import java.io.FilenameFilter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityTestActivity extends Activity {
	final int ACTIVITY_CHOOSE_FILE = 1;

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.test_layout);

	    Button btn = (Button) this.findViewById(R.id.Button1);
	    btn.setOnClickListener(new OnClickListener() {
	      @Override
	      public void onClick(View v) {
	        Intent chooseFile;
	        Intent intent;
	        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
	        chooseFile.setType("file/*");
	        
	        intent = Intent.createChooser(chooseFile, "Choose a file");
	        intent.putExtra("file/*","/sdcard");
	        startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
	      }
	    });
	  }

	  @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    switch(requestCode) {
	      case ACTIVITY_CHOOSE_FILE: {
	        if (resultCode == RESULT_OK){
	          Uri uri = data.getData();
	          String filePath = uri.getPath();
	        }
	      }
	    }
	  }
	} 