package cs213.photoalbum.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.example.photomobileapp.AlbumAdapter;
import com.example.photomobileapp.ImageAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Adapter;
import cs213.photoalbum.model.Album;
import cs213.photoalbum.model.IAlbum;
import cs213.photoalbum.model.IPhoto;
import cs213.photoalbum.model.IPhotoModel;
import cs213.photoalbum.model.IUser;
import cs213.photoalbum.model.Photo;
import cs213.photoalbum.model.User;

/**
 * @author Conrado Uraga
 * 
 *         <p>
 *         Control for general users and their associated albums and photos.
 *         </p>
 */

public class InteractiveControl extends Activity implements IInteractiveControl, Parcelable{
	private User user;

	public InteractiveControl(User user) {
		this.user = user;
	}

	class PhotoCompare implements Comparator<IPhoto> {
		public int compare(IPhoto x, IPhoto y) {
			return x.getDate().compareTo(y.getDate());
		}
	}

	class PhotoCompareForNames implements Comparator<IPhoto> {
		public int compare(IPhoto x, IPhoto y) {
			return x.getFileName().compareTo(y.getFileName());
		}

	}

	class AlbumCompare implements Comparator<IAlbum> {
		public int compare(IAlbum x, IAlbum y) {
			return x.getAlbumName().compareTo(y.getAlbumName());
		}

	}

	@Override
	public void setErrorMessage(String msg) {
		//Do nothing.

	}

	@Override
	public void showError() {
		//Do nothing.
	}



	@Override
	public boolean createAlbum(String name, Activity test, AlbumAdapter apdater) {
		List<IAlbum> album = user.getAlbums();
		InteractiveControl.AlbumCompare comparePower = new InteractiveControl.AlbumCompare();
		Collections.sort(album, comparePower);
		int index = Collections.binarySearch(album, name);
		if (!(index < 0)) {
			String error = "album exists for you";
			openAddDialog("Error creating album", error, test);
			return false;

		}
		/* create new album, add it to the user's album list */
		IAlbum addMe = new Album(name, name);
		user.addAlbum(addMe);
		apdater.addData(addMe);
		user.sortAlbum();
		openAddDialog("Success", "Album was added successfully", test);
		return true;
	}
	public void deleteAlbum(String id, Activity test, AlbumAdapter apdater) {
		List<IAlbum> album = user.getAlbums();
		InteractiveControl.AlbumCompare comparePower = new InteractiveControl.AlbumCompare();
		Collections.sort(album, comparePower);
		int index = Collections.binarySearch(album, id);
		if (index < 0) {
			String error = "album does not exist for you"
					+ id + "";
			openAddDialog("Error!", error, test);
			return;
		}
		user.deleteAlbum(id);
	//	user.sortAlbum();
		apdater.deleteData(id);
		String msg = "deleted album "+ id + " from user";
		openAddDialog("Success", msg, test);
		
		return;

	}
	public boolean renameAlbum(String formal, String id, Activity test, AlbumAdapter apdater) {
		List<IAlbum> album = user.getAlbums();
		InteractiveControl.AlbumCompare comparePower = new InteractiveControl.AlbumCompare();
		Collections.sort(album, comparePower);
		int index = Collections.binarySearch(album, formal);
		if(formal.equals(id)){
			return true;
		}
		if (index < 0) {
			String error = "album does not exist for user you";
			setErrorMessage(error);
			//	System.out.println(error);
			showError();
			openAddDialog("Error!", error, test);
			return false;
		}
		int checker = Collections.binarySearch(album, id);
		if (checker >= 0) {
			String error = "album already exist for you";
			openAddDialog("Error!", error, test);
			return false;
		}
		//	System.out.println("test in control");
		IAlbum editAlbum = album.get(index);
		editAlbum.setAlbumName(id);
		openAddDialog("Error!", formal+" + "+ id, test);
		//	int deleteIndex=view.getIndex();
		apdater.editData(formal, id);
		Collections.sort(user.getAlbums(), comparePower);
		return true;
	}
	public void openAddDialog(String title, String message, Activity that) {
		new AlertDialog.Builder(that)
		  .setTitle(title)
		  .setMessage(message)
		//  .setView(add)
		  .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
		      
		      return;
		    }
		  })
		  .show(); 
	}
	public User getUser(){
		return this.user;
	}
	public IPhoto photoExistsInAlbum(Photo addMe, IAlbum curralbum) {
		List<IAlbum> album1 = user.getAlbums();
		IPhoto getMe = null;
		for (int i = 0; i < user.getAlbums().size(); i++) {
			IAlbum temp2 = album1.get(i);
			List<IPhoto> photoList2 = temp2.getPhotoList();
			if (curralbum.getAlbumName().equals(temp2.getAlbumName())) {
				continue;
			}
			InteractiveControl.PhotoCompareForNames comparePower2 = new InteractiveControl.PhotoCompareForNames();
			Collections.sort(photoList2, comparePower2);
			int index3 = Collections.binarySearch(photoList2,
					addMe.getFileName());
			if (index3 < 0) {
				continue;
			}
			getMe = photoList2.get(index3);
			if (getMe != null) {
				//we found the photo we want. Let's break;
				break;
			}
		}
		if (getMe != null) {
			//		System.out.println("do I really work here"); //TODO Remove aux.
			addMe.setDate(getMe.getDate());
			addMe.setCaption(getMe.getCaption());
			addMe.setLocationTag(getMe.getLocationTag());
			for (int i = 0; i < getMe.getPeopleTags().size(); i++) {
				//			System.out.println(i);
				//addMe.getPeopleTags().addAll(getMe.getPeopleTags());
				addMe.personTag(getMe.getPeopleTags().get(i));
			}
			return addMe;
		} else {
			//		System.out.println("adfasdf here"); //TODO Handle error on GUI.
			return addMe;
		}
	}
	public void addPhoto(Photo addMe, String albumId, Activity currActivity, 
			ImageAdapter test, int position, File fc){
		//user.updateAlbum(pos, addMe);
		List<IAlbum> album1 = user.getAlbums();
		InteractiveControl.AlbumCompare comparePower = new InteractiveControl.AlbumCompare();
		Collections.sort(album1, comparePower);
		//	System.out.println(albumId);
		int index = Collections.binarySearch(album1, albumId);
		if (index < 0) {
			String error = "album does not exist for you";
			setErrorMessage(error);
			showError();
			//		System.out.println("here");
			return;
		}
		IAlbum source = album1.get(index);
		if(source.addPhoto(addMe)){
			addMe = (Photo) photoExistsInAlbum(addMe, source);
			source.addPhotoReal(addMe);
			source.sortPhotoList();
			test.addImage(fc);
			test.notifyDataSetChanged();
		}
		else{
			openAddDialog("[You already have this image!]","", currActivity);
		}
		return;
	}
	public void movePhoto(String albumIdSrc, String albumIdDest, String photoId, Activity currActivity, 
			ImageAdapter test, int position, ArrayList<File> editMeLater) {
		List<IAlbum> album1 = user.getAlbums();
		InteractiveControl.AlbumCompare comparePower = new InteractiveControl.AlbumCompare();
		Collections.sort(album1, comparePower);
		int index = Collections.binarySearch(album1, albumIdSrc);
		if (index < 0) {
			String error = "album does not exist for you"
					+ albumIdSrc + "";
			openAddDialog("test", error, currActivity);
			showError();
			return;
		}
		IAlbum source = album1.get(index);
		index = Collections.binarySearch(album1, albumIdDest);
		if (index < 0) {
			String error = "album does not exist for user you"
					+ albumIdDest + "";
			//	System.out.println("here");
			openAddDialog("test", error, currActivity);
			showError();
			return;

		}
		IAlbum destination = album1.get(index);
		/* PhotoCompareForNames */
		int forLater=index;
		InteractiveControl.PhotoCompareForNames comparePower2 = new InteractiveControl.PhotoCompareForNames();
		List<IPhoto> thePhotos = source.getPhotoList();
		Collections.sort(thePhotos, comparePower2);
		index = Collections.binarySearch(thePhotos, photoId);
		if (index < 0) {
			String blah="";
			if(thePhotos.isEmpty()){
				blah="photos is empty for some reason";
			}
			String error = "File " + photoId + " does not exist in "
					+ albumIdSrc + blah;
			openAddDialog("test", error, currActivity);
			showError();
			//	System.out.println("here? why here?");
			return ;
		}
		/* Once moved, the photo gets removed from the source */
		if (source.getAlbumName().equals(destination.getAlbumName())) {
			return ;
		}
		/*
		 * if the destination album already contains that photo, it should be an
		 * invalid move
		 */
		List<IPhoto> thePhotos2 = destination.getPhotoList();
		Collections.sort(thePhotos2, comparePower2);
		int index2 = Collections.binarySearch(thePhotos2, photoId);
		if (index2 >= 0) {
			String error = "File " + photoId + " already exists in "
					+ albumIdSrc + "";
			return ;
		}
		IPhoto moveMe = thePhotos.get(index);
		destination.addIPhotoReal(moveMe);
		if (thePhotos2.size() == 0) {

			destination.setPic((Photo) moveMe);
		}

		source.deletePhoto(photoId);
		/*
		 * update the album that now contains the photo that has been moved:
		 */
		int counter = 0;
		thePhotos2 = destination.getPhotoList();
		/* counts all photos in O(N) */
		for (int i = 0; i < thePhotos2.size(); i++) {
			counter++;
		}
		destination.setPhotoList(thePhotos2);
		destination.updateNumOfPhotos(counter);
		user.updateAlbum(forLater, destination);
	//	singlealbumview.deleteElementFromVector(singlealbumview.getIndex());
		String success = "Moved photo " + photoId + ":\n" + photoId
				+ " - From album " + albumIdSrc + " to album " + albumIdDest
				+ " "+counter;

		openAddDialog("test", success, currActivity);
		editMeLater.add(test.getItem(position));
		return;
	}
	public void removePhoto(String albumId, String photoId) {
		List<IAlbum> album1 = user.getAlbums();
		InteractiveControl.AlbumCompare comparePower = new InteractiveControl.AlbumCompare();
		Collections.sort(album1, comparePower);
		//	System.out.println(albumId);
		int index = Collections.binarySearch(album1, albumId);
		if (index < 0) {
			String error = "album does not exist for you";
			setErrorMessage(error);
			showError();
			//		System.out.println("here");
			return;
		}
		IAlbum source = album1.get(index);
		//	System.out.println(source.getAlbumName());
		InteractiveControl.PhotoCompareForNames comparePower2 = new InteractiveControl.PhotoCompareForNames();
		List<IPhoto> thePhotos = source.getPhotoList();
		Collections.sort(thePhotos, comparePower2);
		index = Collections.binarySearch(thePhotos, photoId);
		if (index < 0) {
			String error = "Photo " + photoId + " does not exist in " + albumId
					+ "";
			setErrorMessage(error);
			showError();
			//	System.out.println("here 2\n" + photoId);
			return;
		}
		/* I wonder if I can even do this.. */
		source.deletePhoto(photoId);
		String success = "Removed photo:\n" + photoId + " - From album "
				+ albumId + "";
		setErrorMessage(success);
		showError();
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(user);
		
	}
	private InteractiveControl(Parcel in) {
        user=(User) in.readSerializable();
    }
	public static final Parcelable.Creator<cs213.photoalbum.control.InteractiveControl> CREATOR = new Parcelable.Creator<cs213.photoalbum.control.InteractiveControl>() {

        public cs213.photoalbum.control.InteractiveControl[] newArray(int size) {
            return new cs213.photoalbum.control.InteractiveControl[size];
        }

		@Override
		public cs213.photoalbum.control.InteractiveControl createFromParcel(
				Parcel source) {
			// TODO Auto-generated method stub
			return new cs213.photoalbum.control.InteractiveControl(source);
		}
    };



	/* AlbumUI is instantiated here. */

}