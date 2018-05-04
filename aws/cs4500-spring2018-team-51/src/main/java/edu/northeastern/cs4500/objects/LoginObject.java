package edu.northeastern.cs4500.objects;

/**
 * Login Object that contains the email and password of an end user that is logged in. 
 * @author emilytrinh
 *
 */
public class LoginObject {
	private String password;
	private String email;

	/**
	 * Constructor of a loginobject which contains the email and password of the end user. 
	 * @param email string of the end user's email
	 * @param password  string of the end user's password
	 */
	public LoginObject(String email, String password) {
		this.email = email;
		this.password = password;
	}

	/**
	 * General constructor for a LoginObject
	 */
	public LoginObject() {
	}

	/**
	 * Method that returns the password of the LoginObject
	 * @return password of the LoginObject
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Method that returns the email of the LoginObject
	 * @return email of the LoginObject
	 */
	public String getEmail() {
		return email;
	}


}
