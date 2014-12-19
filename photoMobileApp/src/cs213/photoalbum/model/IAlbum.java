package cs213.photoalbum.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 
 *         <p>
 *         A sub-model for the collection of albums.
 *         </p>
 */
public interface IAlbum extends Serializable, Comparable<String> {
	/*
	 * Required
	 */

	/*
	 * Getters and Setters
	 */
	/**
	 * Gets the album's name.
	 * 
	 * @return Album name.
	 */
	public String getAlbumName();

	/**
	 * Sets the album's name.
	 * 
	 * @param name
	 *            Album name.
	 */
	public void setAlbumName(String name);

	/**
	 * Gets the number of photos contained in the album.
	 * 
	 * @return Number of photos in album.
	 */
	public int getAlbumSize();

	/*
	 * Operations
	 */
	/**
	 * Adds a photo to the album, if it does not already exists. Otherwise, lets
	 * user know it already exists and does nothing.
	 * 
	 * This should also keep track of the size of the album by incrementing a
	 * counter every time a photo is added.
	 * 
	 * @param photo
	 *            Photo to be added.
	 * @return
	 */
	public boolean addPhoto(IPhoto photo);

	/**
	 * Deletes a photo in the album, if it exists. Otherwise, lets user know it
	 * already exists and does nothing.
	 * 
	 * This should also keep track of the size of the album by decrementing a
	 * counter every time a photo is removed.
	 * 
	 * @param id
	 *            Photo identifier.
	 */
	public void deletePhoto(String id);

	/**
	 * This is a list of photos contained in the album.
	 * 
	 * @return A full list of photos contained in this album.
	 */
	public List<IPhoto> getPhotoList();

	/**
	 * Recaptions a photo in the album, if it exists. Otherwise, lets user know
	 * it already exists and does nothing.
	 * 
	 * @param photoId
	 *            Photo identifier.
	 * @param caption
	 *            New caption.
	 */
	public void recaptionPhoto(String photoId, String caption);

	public void setIcon(Photo setMe);

	public void setPic(Photo setMe);

	public void setDateRange(Date begin, Date endz);

	public void setOldestPhoto(Date endz);

	public void updateNumOfPhotos(int numOfPhotos);

	public void sortPhotoList();

	public void removePhoto(int i);

	public void setPhotoList(List<IPhoto> thePhotos2);

	public void addPhotoReal(Photo addMe);

	public void addIPhotoReal(IPhoto moveMe);
	public List<IPhoto> getRealPhotoList() ;

}