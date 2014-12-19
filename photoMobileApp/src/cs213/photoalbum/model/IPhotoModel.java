package cs213.photoalbum.model;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author 
 *         <p>
 *         Used by administrator when initiated by the CmdView class at
 *         application startup and user is not logged in.
 *         </p>
 * 
 *         <p>
 *         IPhotoModel is the interactive counterpart to this interface. It is
 *         separated to make a distinction between a model operating on a single
 *         user. This is because during interactive control operations, the
 *         operations perform in the context of a single user; the administrator
 *         does not. Since the administrator model interface can access a list
 *         of users (with getUsers), using this for the interactive model would
 *         mean the interactive controller would have access to other users with
 *         requiring user input. This violates the user-by-user specification.
 *         </p>
 */
public interface IPhotoModel {
	public static final String defaultUserImgPath = "bin" + File.separator
			+ "data" + File.separator + "app" + File.separator + "default.png";
	public static final String defaultAlbumImgPath = "bin" + File.separator
			+ "data" + File.separator + "app" + File.separator + "album.png";
	public static final String defaultNoPhotoImgPath = "bin" + File.separator
			+ "data" + File.separator + "app" + File.separator + "album.png";
	public static final String adminUser = "admin";

	/**
	 * A utility function to help the control figure out whether the user exists
	 * or not, before trying to create him/her.
	 * 
	 * @param username
	 *            The id of the user to be checked.
	 * @return True, if the user exists. False, otherwise.
	 */
	public boolean userExists(String username);

	/**
	 * This function attempts to add the user, if he/she doesn't already exist
	 * inside the database. Otherwise, it will return the user, if he/she
	 * already exists. If you'd like to check whether a user already has been
	 * made, use the userExists(String userId) method.
	 * 
	 * @param username
	 *            The id of the user you'd like to create.
	 * @param fullName
	 *            The full name of the user to be created.
	 * @param password
	 *            The password to be associated with this user.
	 * @param userimg
	 *            The file path to the user's image.
	 * @return The user that has just been newly created or the user that
	 *         already exists in the database.
	 */
	public IUser addUser(String username, String fullName, String password,
			String userimg);

	/**
	 * This reads users from storage into memory. If the user does not exist,
	 * this should do nothing.
	 * 
	 * @param username
	 *            Identifier of the desired user.
	 */
	public IUser getUser(String username);

	/**
	 * <p>
	 * The function will check for existence on the old username and new
	 * username.
	 * </p>
	 * 
	 * @param oldUsername
	 *            Username of the user to be altered.
	 * @param newUsername
	 *            Username the user's name is to be changed to.
	 * @return True, if the username was successfully changed. False, if
	 *         newUsername already exists or user does not exist.
	 */
	public boolean changeUsername(String oldUsername, String newUsername);

	/**
	 * This deletes a user in storage. If current user is in memory, this should
	 * delete the user from memory and from storage. If the user does not exist,
	 * this should do nothing.
	 * 
	 * @param username
	 *            Identifier of the desired user.
	 */
	public void deleteUser(String username);

	/**
	 * @return Collection of user names.
	 */
	public List<String> getUsernames();

	/**
	 * @return Returns a defensive copy of all the user objects in the model.
	 */
	public List<IUser> getCopyOfUsers();

	/**
	 * Loads the users from previous session.
	 */
	public void loadPreviousSession();

	/**
	 * Saves the users in the current session.
	 */
	public void saveCurrentSession();

	/**
	 * @param username
	 *            This is the id of the user you'd like to retrieve the previous
	 *            session for.
	 * @return Returns the user object populated with all photos and albums from
	 *         the previous session.
	 */
	//public IUser loadPreviousUserSession(String username);

	/**
	 * @param user
	 *            This is the user you'd like to save to storage.
	 */
	//public void saveCurrentUserSession(IUser user);

	/**
	 * @param fileName
	 *            Name of the photo to be checked.
	 * @return True, if the file exists. False, otherwise.
	 */
	public boolean photoExists(String fileName);

	/**
	 * @param fileName
	 *            Name of the photo file to retrieve the last modification date
	 *            of.
	 * @return Last modification date of the photo file or null if it doesn't
	 *         exist.
	 */
	public Date photoFileDate(String fileName);

	/**
	 * @param fileName
	 *            Name of the file to retrieve the date of.
	 * @return A string that represents the date.
	 */
	public String photoFileDateString(String fileName);

	/**
	 * @return Returns the file path to the applications default avatar for the
	 *         program.
	 */
	public String getDefaultUserImgPath();

	/**
	 * Sorts the current userlist.
	 * 
	 * @return Sorted defensive copy of the current list of users available in
	 *         the model.
	 */
	public List<IUser> sortedUsers();

	IUser getUser();
}