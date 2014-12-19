package com.example.photomobileapp;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;;



public class FileChooser extends ListActivity {
	 
	 private List<String> item = null;
	 private List<String> path = null;
	 private String root;
	 private TextView myPath;
	 
	 public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.file_chooser_layout);
		 myPath=(TextView) findViewById(R.id.path);
		 root=Environment.getExternalStorageDirectory().getPath();
		 getDir(root);
	 }
	 
	 private void getDir(String dirPath){
		 myPath.setText("SD's path: "+dirPath);
		 item =new ArrayList<String>();
		 path=new ArrayList<String>();
		 final File f=new File(dirPath);
		 final String[] extension = {".jpg", ".gif", ".png"};
		 FilenameFilter filter = new FilenameFilter() {
			 @Override
				public boolean accept(File dir, String filename) {
					 for (String ext : extension) {
				            if (filename.toLowerCase().endsWith(ext)) {
				                return true;
				            }
				        }
				 return false;
			//	 return filename.endsWith(".png")||filename.endsWith(".jpg")||filename.endsWith(".png");
			    }    
				
		 };
		 File[] files=f.listFiles(filter);
		 if(!dirPath.equals(root)){
			 item.add(root);
			 path.add(root);
			 item.add("../");
			 path.add(f.getParent());
		 }
		 for(int i=0; i<files.length; i++){
			 File file=files[i];
			 if(!file.isHidden()&&file.canRead()){
				 path.add(file.getPath());
				 if(file.isDirectory()){
					 item.add(file.getName()+"/");
					 
				 }else{
					 item.add(file.getName());
				 }
			 }
			 else{
				 new AlertDialog.Builder(this)
			     .setIcon(R.drawable.ic_launcher)
			     .setTitle("[Unable to read]")
			     .setPositiveButton("OK", null).show();

			    }
			 }
		 ArrayAdapter<String> fileList=new ArrayAdapter<String>(this, R.layout.row,item);
		 setListAdapter(fileList);
		 }

	 @Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
	  // TODO Auto-generated method stub
	  File file = new File(path.get(position));
	  
	  if (file.isDirectory())
	  {
	   if(file.canRead()){
	    getDir(path.get(position));
	   }else{
	    new AlertDialog.Builder(this)
	     .setIcon(R.drawable.ic_launcher)
	     .setTitle("[" + file.getName() + "] folder can't be read!")
	     .setPositiveButton("OK", null).show(); 
	   } 
	  }else {
		  Bundle bundle = new Bundle();
		 bundle.putString("filePath", path.get(position));
		 bundle.putString("fileName", file.getName());
		 Intent intent = new Intent();
		intent.putExtras(bundle);
		 setResult(RESULT_OK, intent);
		 finish();
	  
	 /* new AlertDialog.Builder(this)
	     .setIcon(R.drawable.ic_launcher)
	     .setTitle("[" +path.get(position)+ file.getName() + "]")
	     .setPositiveButton("OK", null).show();*/

	    }
	 }
	 public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.file_chooser_menu, menu);
	 
	        return super.onCreateOptionsMenu(menu);
	    }
		public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle presses on the action bar items
		    switch (item.getItemId()) {
		        case R.id.back:
		            backToAlbums();
		            return true;
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}

		private void backToAlbums() {
			finish();
			
		}

	}