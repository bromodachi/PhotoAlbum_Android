package cs213.photoalbum.control;

import java.util.List;

import com.example.photomobileapp.AlbumAdapter;

import android.app.Activity;
import cs213.photoalbum.model.IAlbum;

/**
 * @author Conrado Uraga
 * 
 *         <p>
 *         Is triggered when a user logins in the cmdview and thus updates it to
 *         allows the user to interactive with his own albums. The admin control
 *         will create an object of this and leave the rest of the
 *         responsibility to this until the user is done.
 *         </p>
 * 
 */
public interface IInteractiveControl extends IErrorControl {
	/**
	 * note: all errors like illegal parameters, invalid dates should be in this
	 * format Error: <description of error>
	 */
	/**
	 * Runs the photo model of the userid. Will do the communication between the
	 * model and the view passed in the parameters.
	 * 
	 * @param userid
	 *            user's album we wish to run.
	 */
	//public void run(String userid);
	/**
	 * creates the album that is in the parameter. if the album already exists,
	 * we pass an error to IErrorControl.java. If not, we add it to the user's
	 * list.
	 * 
	 * @param name
	 *            will be the name of the album to be added in the user's list
	 *            as long as it doesn't conflict with an exiting name
	 */
	public boolean createAlbum(String name, Activity test, AlbumAdapter adapter);

	/**
	 * deletes the album in the user's list. Not controller already knows the
	 * user id.
	 * 
	 * @param id
	 *            is the photo unique identification that is linked to his list.
	 *            If, for some reason, he/she passes an id that doesn't exist,
	 *            we should print an error stating the user doesn't exist
	 */
	/**
	 * No output for this method. Logs out the user.
	 */
}