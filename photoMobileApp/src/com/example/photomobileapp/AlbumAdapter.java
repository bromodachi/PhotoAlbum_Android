package com.example.photomobileapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;



















import cs213.photoalbum.model.Album;
import cs213.photoalbum.model.IAlbum;
import android.app.Activity;
/*Android imports*/
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AlbumAdapter extends ArrayAdapter{

	private Activity content; //
	private int resource; //save custom layout
	private LayoutInflater theWorker; //create view for our layout]
	private ArrayList<IAlbum> albums;
	private View rowView;
	
	
	public AlbumAdapter(Activity cont, List<IAlbum> list) {
		super(cont, R.layout.list_row, list);
		this.content=cont;
		this.theWorker=LayoutInflater.from(cont);
		this.content=cont;
		this.albums= (ArrayList<IAlbum>) list;
		
		// TODO Auto-generated constructor stub
	}
	class AlbumCompare implements Comparator<IAlbum> {
		public int compare(IAlbum x, IAlbum y) {
			return x.getAlbumName().compareTo(y.getAlbumName());
		}

	}
	public View getView(int pos, View convertView, ViewGroup Parent)
	{
		theWorker=content.getLayoutInflater();
		rowView= theWorker.inflate(R.layout.list_row, null, true);
		EditText albumName1 = (EditText) rowView.findViewById(R.id.AlbumName);
		albumName1.setText(albums.get(pos).getAlbumName());
		albumName1.setEnabled(false);
		albumName1.setFocusable(false);
		albumName1.setClickable(false);
		albumName1.setFocusableInTouchMode(false);
		ImageView imageView;
		TextView updateNum=(TextView) rowView.findViewById(R.id.Description);
		updateNum.setText("Number of Photos: 0");
		Button btn=(Button)rowView.findViewById(R.id.Okay);
		btn.setVisibility(View.INVISIBLE);
		if(!albums.get(pos).getPhotoList().isEmpty()){
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 8;
			imageView = (ImageView) rowView.findViewById(R.id.AlbumPhoto);	
			Bitmap bm = BitmapFactory.decodeFile(albums.get(pos).getPhotoList().get(0).getFile().getAbsolutePath(),options);
			imageView.setImageBitmap(bm);
			imageView = (ImageView) rowView.findViewById(R.id.AlbumPhoto);	
			String numberOfPhotos="Number of Photos: "+albums.get(pos).getPhotoList().size();
			
			updateNum.setText(numberOfPhotos);
		//	imageView.setImageBitmap(BitmapFactory.decodeFile(albums.get(pos).getPhotoList().get(0).getFile().getAbsolutePath()));
		}
		else{
			updateNum.setText("Number of Photos: 0");
		}
		/*imageView = (ImageView) rowView.findViewById(R.id.AlbumPhoto);	
		Button btn=(Button)rowView.findViewById(R.id.Okay);
		btn.setVisibility(View.INVISIBLE);*/
		return rowView;
	}
	public void addData(IAlbum addMe){
		albums.add(addMe);
		AlbumAdapter.AlbumCompare comparePower=new AlbumAdapter.AlbumCompare();
		Collections.sort(albums, comparePower);
		this.notifyDataSetChanged();
	}
	public void updateImage(int position){
		 ImageView image = (ImageView) rowView.findViewById(R.id.AlbumPhoto);
 	    image.setImageBitmap(BitmapFactory.decodeFile(albums.get(position).getPhotoList().get(0).getFile().getAbsolutePath()));
		
	}
	public void deleteData(String id){
		
		AlbumAdapter.AlbumCompare comparePower=new AlbumAdapter.AlbumCompare();
		Collections.sort(albums, comparePower);
		int index = Collections.binarySearch(albums, id);
		IAlbum album = albums.get(index);
		if (album.getAlbumName().equals(id)){
			albums.remove(index);}
		this.notifyDataSetChanged();

	}
	public void updateAlbumNoImage(int position, IAlbum update){
		albums.set(position,update);
	}
	public void updateAlbum(int position, IAlbum update){
		albums.set(position,update);
		updateImage(position);
	}
	public void getItemEditable(){
		EditText passMe= (EditText) rowView.findViewById(R.id.AlbumName);
		makeEditable(true ,passMe);
	}
	private void makeEditable(boolean isEditable,EditText et){
	    if(isEditable){//You can store it in some variable and use it over here while making non editable.
	   //     et.setFocusable(true);
	        et.setEnabled(true);
	  //      et.setClickable(true);
	 //       et.setFocusableInTouchMode(true);
	        //You can store it in some variable and use it over here while making non editable.
	    }else{
	   //     et.setFocusable(false);
	    //    et.setClickable(false);
	     //   et.setFocusableInTouchMode(false);
	        et.setEnabled(false);
	     //   et.setKeyListener(null);
	    }
	}
		
	
	public void editData(String formal, String id) {
		AlbumAdapter.AlbumCompare comparePower=new AlbumAdapter.AlbumCompare();
		Collections.sort(albums, comparePower);
		int index = Collections.binarySearch(albums, id);
		IAlbum album = albums.get(index);
		if (album.getAlbumName().equals(id)){
			album.setAlbumName(id);}
		this.notifyDataSetChanged();
		
	}
	public IAlbum getItem(int i){
		return albums.get(i);
	}
	public View getView(){
		return this.rowView;
	}
	static class checker{
    	boolean isEdit=false;
    }

}

