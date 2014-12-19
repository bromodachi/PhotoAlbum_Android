package com.example.photomobileapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import cs213.photoalbum.control.InteractiveControl;
import cs213.photoalbum.model.Album;
import cs213.photoalbum.model.IAlbum;
import cs213.photoalbum.model.IPhoto;
import cs213.photoalbum.model.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private View rowView;
    private LayoutInflater theWorker;
    private ArrayList<File> mThumbIds=new ArrayList<File>();
    checker check;
    private class FileSorter implements Comparator<File> {
		@Override
		public int compare(File o1, File o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}
    public ImageAdapter(Context c) {
        mContext = c;
        check=new checker();
    }

    public int getCount() {
        return mThumbIds.size();
    }
    public void removeItem(int position){
    	mThumbIds.remove(position);
    }

    public File getItem(int position) {
        return mThumbIds.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }
    @Override
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	rowView=convertView;
    	ImageView imageView;
    	final CheckBox checkbox;
    	if(rowView==null){
    	theWorker=((Activity) mContext).getLayoutInflater();
    	rowView= theWorker.inflate(R.layout.row_grid, null, true);
		imageView = (ImageView) rowView.findViewById(R.id.PhotoToDisplay);
		checkbox = (CheckBox) rowView.findViewById(R.id.toDelete);
    	}
		else{
			imageView = (ImageView) rowView.findViewById(R.id.PhotoToDisplay);
			checkbox = (CheckBox) rowView.findViewById(R.id.toDelete);
		}
    	final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8;
		Bitmap bm = BitmapFactory.decodeFile(mThumbIds.get(position).getAbsolutePath(),options);
    	 imageView.setImageBitmap(bm);
		if(check.isChecked==false){
			checkbox.setVisibility(View.INVISIBLE);
    	 	if(checkbox.isChecked()){
    	 		checkbox.setChecked(false);
    	 	}
		}
		else{
			checkbox.setVisibility(View.VISIBLE);
		}
		final int pos=position;
		checkbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    // TODO Auto-generated method stub
                if(checkbox.isChecked())
                {
                	
                	 check.checkedItemPositions.add(pos);
                	 if(!(check.checkedItemPositions.size()==0)){
                 		check.showDelete=true;
                 	}
                }
                else
                {
                	 check.checkedItemPositions.remove(pos);
                	 if(check.checkedItemPositions.size()==0){
                 		check.showDelete=false;
                 	}
                }
            }
        });
		return rowView;
    	
    	/*   ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(10, 10, 10, 10);
        } else {
            imageView = (ImageView) convertView;
        }
   //     imageView.setImageBitmap(Bitmap.decode(images[position].getAbsoluteFile()));
        imageView.setImageBitmap(BitmapFactory.decodeFile(mThumbIds.get(position).getAbsolutePath()));
        return imageView;*/
    }
    public void restore(List<IPhoto> list){
    	for(int i=0;i<list.size();i++){
			mThumbIds.add(list.get(i).getFile());
		}
    	this.notifyDataSetChanged();
    	
    }
    
    public void showChecks(){
    	for(int i=0;i<mThumbIds.size();i++){
    		CheckBox checkbox = (CheckBox) rowView.findViewById(R.id.toDelete);
    		checkbox.setVisibility(View.VISIBLE);
		}
    }
    public void deletImage(File deleteMe){
    	
    	FileSorter power=new FileSorter();
    	Collections.sort(this.mThumbIds, power) ;
    	int index = Collections.binarySearch(this.mThumbIds, deleteMe);
    	this.mThumbIds.remove(index);
    	this.notifyDataSetChanged();
    }
    public void addImage(File addMe){
    	this.mThumbIds.add(addMe);
    	FileSorter power=new FileSorter();
    	Collections.sort(this.mThumbIds, power) ;
    	this.notifyDataSetChanged();
    	
    }
    static class checker{
    	boolean isChecked=false;
    	HashSet <Integer> checkedItemPositions=new HashSet<Integer>();
    	boolean showDelete=false;
    	String moveME="";
    }

    // references to our images

}
