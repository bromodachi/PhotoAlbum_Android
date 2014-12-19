package cs213.photoalbum.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author
 *         <p>
 *         A sub-model for the user by which a single user can be modified.
 *         </p>
 */
public interface IUser extends Serializable, Comparable<String> {
	/**
	 * Gets the user's identifier.
	 * 
	 * @return Identifier associated with user.
	 */
	public String getUsername();
	
	/**
	 * @param username The username to change to.
	 */
	public void setUsername(String username);

	/**
	 * @return Returns the full name of the user.
	 */
	public String getFullname();
	
	/**
	 * @param fullname The full name to be associated with the user.
	 */
	public void setFullname(String fullname);
	
	/**
	 * @return Returns password associated with the user.
	 */
	public String getPassword();
	
	/**
	 * @param password The password to be associated with the user.
	 */
	public void setPassword(String password);
	
	/**
	 * @return Returns the path to the user's image.
	 */
//	public String getUserImgPath();
	
	/**
	 * @param imgPath The path to the user's image.
	 */
//	public void setUserImgPath(String imgPath);

	/**
	 * Gets a list of albums.
	 * 
	 * @return Collection of albums.
	 */
	public List<IAlbum> getAlbums();

	/*
	 * Operations
	 */
	/**
	 * Adds an album to the user's collection.
	 * 
	 * @param album
	 *            Album to be associated with this user's collection of albums.
	 */
	public void addAlbum(IAlbum album);

	/**
	 * Deletes an album from the user's collection.
	 * 
	 * @param albumId
	 *            Identifier of the album to be deleted from this user's
	 *            collection of albums.
	 */
	public void deleteAlbum(String albumId);
}
