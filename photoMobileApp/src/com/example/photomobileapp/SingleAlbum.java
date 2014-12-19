package com.example.photomobileapp;


import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import cs213.photoalbum.control.InteractiveControl;
import cs213.photoalbum.model.Album;
import cs213.photoalbum.model.IAlbum;
import cs213.photoalbum.model.IPhoto;
import cs213.photoalbum.model.Photo;
import cs213.photoalbum.model.User;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
//com.example.photomobileapp.SingleAlbum
/*UPDATE THE MOVE METHOD LIKE THE DELETE METHOD */
public class SingleAlbum extends Activity{
	private IAlbum album;
	ImageAdapter test;
	GridView gridview;
	InteractiveControl control;
	int position;
	User user;
	boolean hideForDelete=false;
	boolean hideForMove=false;
	int albumPosition;
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.single_album);
	    Intent intent=getIntent();
	    album=(IAlbum) intent.getSerializableExtra("AlbumClassToEdit");
	    control=(InteractiveControl) intent.getParcelableExtra("theControl");
	    gridview = (GridView) findViewById(R.id.gridview);
	    user=(User) intent.getSerializableExtra("user");
	    position=intent.getIntExtra("position", 0);
	    user.updateAlbum(position, album);
	    albumPosition=position;
	    test=new ImageAdapter(this);
	    gridview.setAdapter(test);
	    gridview.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
	    if(!album.getPhotoList().isEmpty()){
	    	restorePhotos() ;
	    }	
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	Intent intent = new Intent(getApplicationContext(), FullPhoto.class);
	        	intent.putExtra("photo", control.getUser().getAlbums().get(albumPosition).getRealPhotoList().get(position));
	        	intent.putExtra("photoPos", position);
	        	startActivityForResult(intent, 50);
	            //user.getAlbums().get(position).get(position);
	        }
	    });
	    
	    
	}
	private void restorePhotos() {
		for(int i=0;i<album.getPhotoList().size();i++){
			test.addImage(album.getPhotoList().get(i).getFile());
		}
		test.notifyDataSetChanged();
		
	}
	@SuppressLint("NewApi")
	private void deleteCheckedItems() {
	/*	new AlertDialog.Builder(this)
	     .setIcon(R.drawable.ic_launcher)
	     .setTitle("["+test.getCount()+"]")
	     .setPositiveButton("OK", null).show();*/
		String beta="";
		ArrayList<File> toDelete=new ArrayList<File>();
		 Iterator iterator = test.check.checkedItemPositions.iterator();
         while (iterator.hasNext()){
        	 int i=(int) iterator.next();
        	 beta=beta+i+" ";
        	control.removePhoto(album.getAlbumName(),test.getItem(i).getName());
        	 toDelete.add(test.getItem(i));

         }
         for(int i=0; i<toDelete.size();i++){
        	 this.test.deletImage(toDelete.get(i));
         }
        /* new AlertDialog.Builder(this)
	     .setIcon(R.drawable.ic_launcher)
	     .setTitle("["+beta+"]")
	     .setPositiveButton("OK", null).show();*/
	}
	private ArrayList<String> getMovePhoto() {
		ArrayList<String> passMe=new ArrayList<String>(user.getAlbums().size());
		int j=user.getAlbums().size();
		for(int i=0; i<j;i++) {
			passMe.add(user.getAlbums().get(i).getAlbumName());
		}
        return passMe;
	}
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.single_album_menu, menu);
        if(hideForDelete==true){
        	MenuItem hideAdd= menu.findItem(R.id.addAlbumSingle);
        	hideAdd.setVisible(false);
        	MenuItem hideBack= menu.findItem(R.id.backSingle);
        	hideBack.setVisible(false);
        	MenuItem deletePhoto= menu.findItem(R.id.deletePhoto);
        	deletePhoto.setVisible(false);
        	MenuItem confirm= menu.findItem(R.id.ConfirmDelete);
        	confirm.setVisible(true);
        	MenuItem cancel= menu.findItem(R.id.CancelDelete);
        	cancel.setVisible(true);
        	MenuItem confirmMove= menu.findItem(R.id.ConfirmMove);
        	confirmMove.setVisible(false);
        	MenuItem movePhoto= menu.findItem(R.id.MovePhoto);
        	movePhoto.setVisible(false);
        	MenuItem cancelMove= menu.findItem(R.id.CancelMove);
        	cancelMove.setVisible(false);
        	
        }
        else if(hideForMove){
        	MenuItem hideAdd= menu.findItem(R.id.addAlbumSingle);
        	hideAdd.setVisible(false);
        	MenuItem hideBack= menu.findItem(R.id.backSingle);
        	hideBack.setVisible(false);
        	MenuItem deletePhoto= menu.findItem(R.id.deletePhoto);
        	deletePhoto.setVisible(false);
        	MenuItem confirm= menu.findItem(R.id.ConfirmDelete);
        	confirm.setVisible(false);
        	MenuItem cancel= menu.findItem(R.id.CancelDelete);
        	cancel.setVisible(false);
        	MenuItem movePhoto= menu.findItem(R.id.MovePhoto);
        	movePhoto.setVisible(false);
        	MenuItem confirmMove= menu.findItem(R.id.ConfirmMove);
        	confirmMove.setVisible(true);
        	MenuItem cancelMove= menu.findItem(R.id.CancelMove);
        	cancelMove.setVisible(true);
        	
        }
        else{
        	MenuItem hideAdd= menu.findItem(R.id.addAlbumSingle);
        	hideAdd.setVisible(true);
        	MenuItem hideBack= menu.findItem(R.id.backSingle);
        	hideBack.setVisible(true);
        	MenuItem deletePhoto= menu.findItem(R.id.deletePhoto);
        	deletePhoto.setVisible(true);
        	MenuItem confirm= menu.findItem(R.id.ConfirmDelete);
        	confirm.setVisible(false);
        	MenuItem cancel= menu.findItem(R.id.CancelDelete);
        	cancel.setVisible(false);
        	MenuItem cancelMove= menu.findItem(R.id.CancelMove);
        	cancelMove.setVisible(false);
        	MenuItem confirmMove= menu.findItem(R.id.ConfirmMove);
        	confirmMove.setVisible(false);
        }
 
        return super.onCreateOptionsMenu(menu);
    }
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.addAlbumSingle:
	            addPhoto();
	            return true;
	        case R.id.backSingle:
	        	backToAlbums();
	        	return true;
	        case R.id.deletePhoto:
	        	commenceDelete();
	        	return true;
	        case R.id.MovePhoto:
	        	movePhoto();
	        	return true;
	        case R.id.ConfirmMove:
	        	showDialog();
	        	return true;
	        case R.id.ConfirmDelete:
	        	dotheDelete();
	        	return true;
	        case R.id.CancelDelete:
	        	cancelDelete();
	        	return true;
	        case R.id.CancelMove:
	        	cancelDelete();
	        	return true;
	       
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	private void showDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(SingleAlbum.this.LAYOUT_INFLATER_SERVICE);
	    View layout_spinners = inflater.inflate(R.layout.spinner,null);
		Spinner moveSpin = (Spinner) layout_spinners.findViewById(R.id.spinner);
		builder.setView(layout_spinners);
	    builder.setCancelable(false);
	    builder.setTitle("[Move ]");
	    ArrayList<String> blah=getMovePhoto();
	    final ArrayAdapter<String> listOfAlbums = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, blah);
	    moveSpin.setAdapter(listOfAlbums);
	    String str;
	    moveSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	 //   final String moveAlbum="";
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				test.check.moveME=listOfAlbums.getItem(position);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {  

            		ArrayList<File> removeMeLater=new ArrayList<File>(test.getCount());
    				Iterator iterator = test.check.checkedItemPositions.iterator();
    		         while (iterator.hasNext()){
    		        	 int i=(int) iterator.next();
    		        	 control.movePhoto(album.getAlbumName(), test.check.moveME, 
    		        			 test.getItem(i).getName(), SingleAlbum.this, test, i, removeMeLater);
    		     //   	 album.removePhoto(i);
    		         }
    		         for(int i=0; i<removeMeLater.size();i++){
    		        	 test.deletImage(removeMeLater.get(i));
    		         }
    		         user=control.getUser();
    		         test.notifyDataSetChanged();
    		 		hideForDelete=false;
    		 		hideForMove=false;
    		 		test.check.isChecked=false;
    		 		test.check.checkedItemPositions.clear();
    		 		invalidateOptionsMenu();
    		 		//update the user
    		 		//need a better way to do this, duh
    			

            }});
	    builder.show();
	    test.notifyDataSetChanged();
		hideForDelete=false;
		test.check.isChecked=false;
		invalidateOptionsMenu();
	}
	@SuppressLint("NewApi")
	private void cancelDelete() {
		// TODO Auto-generated method stub
		test.check.checkedItemPositions.clear();
		hideForDelete=false;
		hideForMove=false;
		test.check.isChecked=false;
		test.notifyDataSetChanged();
		test.check.checkedItemPositions.clear();
		invalidateOptionsMenu();
		
	}
	@SuppressLint("NewApi")
	private void dotheDelete() {
		if(test.check.showDelete==false){
			new AlertDialog.Builder(this)
		     .setIcon(R.drawable.ic_launcher)
		     .setTitle("[You didn't select any images!!]")
		     .setPositiveButton("OK", null).show();
			
		}
		deleteCheckedItems();
		test.notifyDataSetChanged();
		hideForDelete=false;
		test.check.isChecked=false;
		test.check.checkedItemPositions.clear();
		invalidateOptionsMenu();
		
		
	}
	private void movePhoto(){
		test.check.isChecked=true;
		hideForMove=true;
		test.notifyDataSetChanged();
		invalidateOptionsMenu();
	}
	@SuppressLint("NewApi")
	private void commenceDelete() {
		hideForDelete=true;
		test.check.isChecked=true;
		test.notifyDataSetChanged();
		invalidateOptionsMenu();
		
	//	test.check.isChecked=true;
		
	}
	private void backToAlbums() {
		Intent returnIntent = new Intent();
	//      returnIntent.putExtra("albumToGiveBack",album);
	      returnIntent.putExtra("position",position);
	      returnIntent.putExtra("user", control.getUser());
	      returnIntent.putExtra("control", control);
	      setResult(RESULT_OK,returnIntent);        
	      finish();
		
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if(resultCode==50){
			Bundle bundle2 = intent.getExtras();
			int photoPos=bundle2.getInt("photoPos");
			IPhoto photothatWasEdited=(IPhoto) bundle2.getSerializable("photo");
			control.getUser().getRealAlbums().get(albumPosition).getRealPhotoList().set(photoPos, photothatWasEdited);
			return;
		}
		if (resultCode != RESULT_OK) {
			return;
		}
		Bundle bundle = intent.getExtras();
		if (bundle == null) {
			return;
		}
		Uri myUri = Uri.parse(bundle.getString("filePath"));
		File path=new File(myUri.getPath());
		addPhotoToAlbum(path);
	}
	
	public void addPhotoToAlbum(File fc){
		Photo re=new Photo(album.getAlbumName(), fc.getName(), fc);
		control.addPhoto(re, album.getAlbumName(), SingleAlbum.this, test, position, fc);
		//(Photo addMe, String albumId, Activity currActivity, 
		//	ImageAdapter test, int position)
	/*	boolean yes = album.addPhoto(re);
		if (yes == true) {
			re = (Photo) control.photoExistsInAlbum(re, album);
				System.out.println(re.getCaption());
			album.sortPhotoList();
			control.addPhoto(position, album);
			test.addImage(fc);
			test.notifyDataSetChanged();
			new AlertDialog.Builder(this)
		     .setIcon(R.drawable.ic_launcher)
		     .setTitle("[ added: " +re.getFileName() + "]")
		     .setPositiveButton("OK", null).show();
		} else {
			new AlertDialog.Builder(this)
		     .setIcon(R.drawable.ic_launcher)
		     .setTitle("[You already have this image!]")
		     .setPositiveButton("OK", null).show();
			
		}*/
	}
	public void onPause(){
		super.onPause();
		saveObject(control.getUser());
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
	public void addPhoto(){
		Intent intent = new Intent(SingleAlbum.this, FileChooser.class);
		startActivityForResult(intent, 1);
		
	}

}
