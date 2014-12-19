package cs213.photoalbum.model;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Album implements IAlbum {
	private String albumName;
	private String image;
	private ArrayList<IPhoto> photoList;

	public Album(String albumName, String imageAdd) {
		super();
		this.albumName = albumName;
		this.image=imageAdd;
		this.photoList = new ArrayList<IPhoto>();
	}

public List<IPhoto> getRealPhotoList() {
	return this.photoList;
}

	@Override
	public String getAlbumName() {
		return this.albumName;
	}

	@Override
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	@Override
	public int getAlbumSize() {
		return this.photoList.size();
	}

	@Override
	public boolean addPhoto(IPhoto photo) {
		Collections.sort(this.photoList, new PhotoComparator());
		int index = Collections.binarySearch(this.photoList,
				photo.getFileName());
		if (index < 0) {
			//this.photoList.add(photo);
			return true;
		} else if (!this.photoList.get(index).getFileName()
				.equals(photo.getFileName())) {
		//	this.photoList.add(photo);
			return true;
		}
		return false;
	}
	public void addPhotoReal(Photo photo){
		this.photoList.add(photo);
	}
	public void addIPhotoReal(IPhoto photo){
		this.photoList.add(photo);
	}
	public void removePhoto(int i){
		this.photoList.remove(i);
	}
	@Override
	public void deletePhoto(String fileName) {
		Collections.sort(this.photoList, new PhotoComparator());
		int index = Collections.binarySearch(this.photoList, fileName);
		if (index >= 0
				&& this.photoList.get(index).getFileName().equals(fileName))
			this.photoList.remove(index);
	}

	@Override
	public void recaptionPhoto(String id, String caption) {
		Collections.sort(this.photoList, new PhotoComparator());
		int index = Collections.binarySearch(this.photoList, id);
		if (index >= 0 && this.photoList.get(index).getFileName().equals(id))
			this.photoList.get(index).setCaption(caption);
	}
	public void sortPhotoList(){
		Collections.sort(this.photoList, new PhotoComparator());
	}
	@Override
	public int compareTo(String o) {
		return this.albumName.compareTo(o);
	}

	@Override
	public List<IPhoto> getPhotoList() {
		ArrayList<IPhoto> defensiveCopy = new ArrayList<IPhoto>();
		defensiveCopy.addAll(this.photoList);
		return defensiveCopy;
	}

	private class PhotoComparator implements Comparator<IPhoto> {
		@Override
		public int compare(IPhoto o1, IPhoto o2) {
			return o1.getFileName().compareTo(o2.getFileName());
		}
	}


	@Override
	public void setIcon(Photo setMe) {

	}

	public void setPhotoList(List<IPhoto> update){
		this.photoList=(ArrayList<IPhoto>) update;
	}

	@Override
	public void setPic(Photo setMe) {
		// TODO Auto-generated method stub
		
	}
	public String getImage(){
		return image;
	}
	public void setImage(String imageage){
		this.image=imageage;
	}



	@Override
	public void setDateRange(Date begin, Date endz) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void setOldestPhoto(Date endz) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void updateNumOfPhotos(int numOfPhotos) {
		// TODO Auto-generated method stub
		
	}




}