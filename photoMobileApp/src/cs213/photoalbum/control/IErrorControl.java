package cs213.photoalbum.control;

/**
 * @author Conrado Uraga
 *         <p>
 *         Interface that will be implemented for both controllers. Set error
 *         messages and print that message stored in the admin controls global
 *         variable
 *         </p>
 */
public interface IErrorControl {
	/**
	 * Receives a string a method passes and sets it to a variable.
	 * 
	 * @param msg
	 *            will contain the error message the method passed.
	 */
	public void setErrorMessage(String msg);

	/**
	 * showError prints the error that will warn the user if it conflicts with
	 * an existing object or if it doesn't exist
	 */
	public void showError();
}