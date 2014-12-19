package com.example.photomobileapp;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle.Control;

import cs213.photoalbum.control.InteractiveControl;
import cs213.photoalbum.model.Album;
import cs213.photoalbum.model.IAlbum;
import cs213.photoalbum.model.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity{
	ListView list;
	User loneUser;
	InteractiveControl theWorker;
	AlbumAdapter adapter;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Resources res=getResources();
		loneUser=(User) loadSerializedObject(new File("/sdcard/save_object.bin"));
		if(loneUser==null){
			loneUser=new User("test", "test","test");
	//		IAlbum test=new Album("test", "hiroshi");
	//		loneUser.addAlbum(test);
		}
		theWorker=new InteractiveControl(loneUser);
		adapter=new AlbumAdapter(MainActivity.this, loneUser.getAlbums());
		list=(ListView)findViewById(R.id.list_row);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            	final int pos=position;
       //     	System.out.println(loneUser.getAlbums().get(position).getAlbumName());
                Toast.makeText(MainActivity.this, "You Clicked at " +loneUser.getAlbums().get(+ position).getAlbumName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SingleAlbum.class);
                intent.putExtra("AlbumClassToEdit", theWorker.getUser().getAlbums().get(position));  
                intent.putExtra("theControl", theWorker); 
                intent.putExtra("position", position);
                intent.putExtra("user", loneUser);
                
                startActivityForResult(intent, 1);
           
            }
        });
	}
	public void sendMessage(View view) {
	    Intent intent = new Intent(this, ActivityTestActivity.class);
	    startActivityForResult(intent, 1);
	}
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.add:
	            openAddDialog();
	            return true;
	        case R.id.delete:
	        	
	        	openDeleteDialog();
	        	return true;
	        case R.id.edit:
	        	performEdit();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	public void onPause(){
		super.onPause();
		saveObject(theWorker.getUser());
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode != RESULT_OK) {
			return;
		}

		Bundle bundle = intent.getExtras();
		if (bundle == null) {
			return;
		}
	//	IAlbum update=(IAlbum) bundle.getSerializable("albumToGiveBack");
		int position=bundle.getInt("position", 0);
		theWorker=bundle.getParcelable("control");
		loneUser=theWorker.getUser();
	//	loneUser.updateAlbum(position,update);
		/* if(!loneUser.getAlbums().get(position).getPhotoList().isEmpty()){
			 adapter.updateAlbum(position, update);
			 adapter.notifyDataSetChanged();
    		  }*/
		 for(int i=0;i<loneUser.getAlbums().size();i++){
			 if(!loneUser.getAlbums().get(i).getPhotoList().isEmpty()){
				 adapter.updateAlbum(i, loneUser.getAlbums().get(i));
	    	 }
			 else{
				 adapter.updateAlbumNoImage(i, loneUser.getAlbums().get(i));
			 }
		 }
		 adapter.notifyDataSetChanged();
	}
	private void performEdit() {
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            	final EditText passMe= (EditText) view.findViewById(R.id.AlbumName);
        		final String formal=passMe.getText().toString();
            	makeEditable(true ,passMe);
        		final Button btn=(Button)view.findViewById(R.id.Okay);
        		btn.setVisibility(View.VISIBLE);
        		btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                    	boolean success=theWorker.renameAlbum(formal, passMe.getText().toString(), MainActivity.this, adapter);
                    	
                    	if(success){
                    		makeEditable(false,passMe);
                    		btn.setVisibility(View.INVISIBLE);
                    		returnListernerBack();
                    		loneUser=theWorker.getUser();
                    	}
                    }
                });
            }
        });
		
	}
	public void openDeleteDialog(){
		final EditText deleteAlbum = new EditText(this);
		deleteAlbum.setHint("");

		new AlertDialog.Builder(this)
		  .setTitle("Delete Album")
		  .setMessage("Please enter the name of the album you wish to delete")
		  .setView(deleteAlbum)
		  .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
		      String addMe = deleteAlbum.getText().toString();
		      theWorker.deleteAlbum(addMe, MainActivity.this, adapter);
		      adapter.notifyDataSetChanged();
		      loneUser=theWorker.getUser();
	//	    	  list.setAdapter(new AlbumAdapter(MainActivity.this, loneUser.getAlbums()));
	//	    	  list.setAdapter(adapter);
		      
		      
		    }
		  })
		  .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
		    }
		  })
		  .show(); 
	}
	
	public void returnListernerBack(){
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "You Clicked at " +loneUser.getAlbums().get(+ position).getAlbumName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SingleAlbum.class);
                intent.putExtra("AlbumClassToEdit", theWorker.getUser().getAlbums().get(position));  
                intent.putExtra("theControl", theWorker); 
                intent.putExtra("position", position);
                intent.putExtra("user", loneUser);
                
                
                startActivityForResult(intent, 1);
            }
        });
	}
	public void openAddDialog() {
		// TODO Auto-generated method stub
		final EditText addAlbum = new EditText(this);
		addAlbum.setHint("");

		new AlertDialog.Builder(this)
		  .setTitle("Add Album")
		  .setMessage("Please enter the name of the album you wish to add")
		  .setView(addAlbum)
		  .setPositiveButton("Add", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
		      String addMe = addAlbum.getText().toString();
		      boolean test=theWorker.createAlbum(addMe, MainActivity.this, adapter);
		      if(test){
		    	  loneUser=theWorker.getUser();
		    	  adapter.notifyDataSetChanged();
	//	    	  list.setAdapter(new AlbumAdapter(MainActivity.this, loneUser.getAlbums()));
	//	    	  list.setAdapter(adapter);
		      }
		      
		    }
		  })
		  .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
		    }
		  })
		  .show(); 
		
	}
	private void makeEditable(boolean isEditable,EditText et){
	    if(isEditable){//You can store it in some variable and use it over here while making non editable.
	    	 et.setFocusable(true);
	         et.setEnabled(true);

	         et.setFocusableInTouchMode(true);
	         et.requestFocus();
	        //You can store it in some variable and use it over here while making non editable.
	    }else{
	    	et.setFocusable(false);
	        et.setClickable(true);
	        et.clearFocus();
	    }
	}
	    public void saveObject(User p){
	         try
	         {
	            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/sdcard/save_object.bin"))); //Select where you wish to save the file...
	            oos.writeObject(p); // write the class as an 'object'
	            oos.flush(); // flush the stream to insure all of the information was written to 'save_object.bin'
	            oos.close();// close the stream
	         }
	         catch(Exception ex)
	         {
	            Log.v("Serialization Save Error : ",ex.getMessage());
	            ex.printStackTrace();
	         }
	    
	}
	    public Object loadSerializedObject(File f)
	    {
	        try
	        {
	            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
	            Object o = ois.readObject();
	            return o;
	        }
	        catch(Exception ex)
	        {
	        Log.v("Serialization Read Error : ",ex.getMessage());
	            ex.printStackTrace();
	        }
	        return null;
	    }
	

}
