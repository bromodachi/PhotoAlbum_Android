package cs213.photoalbum.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Photo implements IPhoto {
	private static final long serialVersionUID = 1L;
	private String photoId;
	private String fileName;
	private String caption;
	private Date photoDate;
	private String locationTag;
	private ArrayList<String> peopleTags;
	private File photoFile;

	public Photo(String photoId, String fileName, File file) {
		this.photoId = photoId;
		this.fileName = fileName;
		this.locationTag = "test";
		this.peopleTags = new ArrayList<String>();
		this.photoFile=file;
	}

	public Photo() {
		super();
	}

	@Override
	public int compareTo(String o) {
		return this.fileName.compareTo(o);
	}

	@Override
	public String getPhotoID() {
		return this.photoId;
	}

	@Override
	public void setPhotoID(String photoId) {
		this.photoId = photoId;
	}

	@Override
	public String getFileName() {
		return this.fileName;
	}

	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String getCaption() {
		return this.caption;
	}

	@Override
	public void setCaption(String caption) {
		this.caption = caption;
	}

	@Override
	public Date getDate() {
		return this.photoDate;
	}

	@Override
	public void setDate(Date photoDate) {
		this.photoDate = photoDate;
	}

	@Override
	public String getLocationTag() {
		return this.locationTag;
	}

	@Override
	public void setLocationTag(String locationTag) {
		this.locationTag = locationTag;
	}

	@Override
	public List<String> getPeopleTags() {
		ArrayList<String> defensiveCopy = new ArrayList<String>();
		defensiveCopy.addAll(this.peopleTags);
		return defensiveCopy;
	}
	public File getFile(){
		return this.photoFile;
	}

	
	public void personTag(String personName) {
		Collections.sort(this.peopleTags);
		int index = Collections.binarySearch(this.peopleTags, personName);
		if (index < 0 || !this.peopleTags.get(index).equals(personName))
			this.peopleTags.add(personName);
	}

	@Override
	public boolean removePersonTag(String personName) {
		Collections.sort(this.peopleTags);
		int index = Collections.binarySearch(this.peopleTags, personName);
		if (index >= 0 && this.peopleTags.get(index).equals(personName)) {
			this.peopleTags.remove(index);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getDateString() {
		return new SimpleDateFormat("MM/dd/yyyy-HH:MM:SS")
				.format(this.photoDate);
	}

	@Override
	public String getFilePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFilePath(String filePath) {
		// TODO Auto-generated method stub
		
	}
}