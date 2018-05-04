package edu.northeastern.cs4500.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import edu.northeastern.cs4500.log4j2.Log;

/**
 * End user class represents an end user of Spoilled Tomatillos. Each end user has a first name, last name, email address,
 * password, and unique id. Once the end user is logged in, a boolean is set to "true" in the mySQL database. If the user
 * is authorized to be an admin user (must be done by another admin user), a boolean is set to "true" to show that they are authorized.
 * @author emilytrinh
 *
 */
@Entity(name="user")
public class EndUser {

	/**
	 * Simple example of CRU services on an object.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique=true)
	private int id;
	private String firstName;
	private String lastName;

	/**
	 * Creates the mySQL users table to include an email address, password, boolean to represent if an end user is logged in,
	 * and boolean to represent if an end user is an Admin. 
	 */
	@Column(name = "email", unique=true)
	private String email;
	private String password;
	private Boolean loggedIn;
	private boolean authorized;
	
	/**
	 * Constructor for an end user. 
	 * @param id unique integer that identifies an end user in the data base
	 * @param firstName first name of an end user
	 * @param lastName last name of an end user
	 * @param email email address of an end user, cannot have multiple end users with the same email
	 * @param password password for an end user
	 * @param authorized boolean that is set to "true" if the end user is an Admin. Otherwise, boolean is set to false. 
	 */
	public EndUser(int id, String firstName, String lastName, String email, String password, boolean authorized) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.authorized = authorized;
		this.loggedIn = false;
	}

	/**
	 * Create an EndUser with an initialized value
	 */
	public EndUser(int id, String firstName, String lastName, String email, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		authorized = false;
		this.password = password;
		this.loggedIn = false;
	}

	/**
	 * Create an EndUser with an initialized value
	 */
	public EndUser(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		authorized = false;
		this.password = password;
		this.loggedIn = false;
	}

	/**
	 * Creates an end user with a default first name, default last name, and default email address.
	 */
	public EndUser() {
		firstName = "default first";
		lastName = "default last";
		email = "defualt email";
		this.loggedIn = false;
	}

	/**
	 * Method that gets the ID of an end user. 
	 * @return integer id for that specific end user. 
	 */
	public int getId() {
		return id;
	}

	/**
	 * Method that updates the ID of an end user
	 * @param ID that the user's ID will be updated to. 
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Method that returns the first name of an end user
	 * @return first name of an end user
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Method that returns the last name of an end user
	 * @return last name of an end user
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Method that returns the first and last name of an end user as one string.
	 * @return first and last name of an end user as one string with a space in between.
	 */
	public String getFullName() {
		return firstName + " " + lastName;
	}

	/**
	 * Method that returns the email of an end user.
	 * @return email of an end user
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Method that returns the boolean "true" if the end user is an admin, returns false if the end
	 * user is not authorized.
	 * @return boolean of if the end user is an admin (true) or not (false)
	 */
	public boolean isAuthorized() {
		return authorized;
	}

	/**
	 * Method that returns the boolean "true" if the end user is logged in, returns false if the end user is logged out.
	 * @return boolean of if the end user is logged in (true) or not (false)
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * Method that returns the unique ID, first name, last name, and email of an enduser as one string.
	 */
	@Override
	public String toString() {
		return Integer.toString(id) + "\n" + firstName + "\n" + lastName + "\n" + email;
	}

	/**
	 * Method that updates the logIn boolean to "True" because the user is now logged in. 
	 * @return "True" because the user is now logged in.
	 */
	public boolean logIn() {
		this.loggedIn = true;
		return this.loggedIn;
	}
	
	/**
	 * Method that updates the logIn boolean to "False" because the user has now logged out.
	 * @return "False" because the user is now logged out.
	 */
	public boolean logOut() {
		this.loggedIn = false;
		return this.loggedIn;
	}

	/**
	 * Method that sets the email of the end user using the given string. 
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Method that updates the password of an end user's account with a new given password
	 * @param oldPassword the end user's old password
	 * @param newPassword the end user's new password
	 * @throws SpoiledTomatillosException 
	 */
	public void changePassword(String oldPassword, String newPassword) throws SpoiledTomatillosException {
		if (oldPassword == this.password) {
			this.password = newPassword;
		}
		else { 
			Log.reportError("Password entered does not match current password.");
			throw new SpoiledTomatillosException("password entered does not match current password."); 
		}
	}

	/**
	 * Method that returns the password of the end user. 
	 * @return String of the end user's password
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Method that sets the authorized boolean to "true" because the end user is now an Admin.
	 */
	public void setAdmin() {
		this.authorized = true;
	}
	
	/**
	 * Method that sets the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Checks if two end users are equal, specifically checks if the unique IDs of two end users are the same. 
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof EndUser)) {
			return false;
		}
		EndUser u = (EndUser)o;

		return(u.getId() == this.getId());
	}
	
	/**
	 * Overrides hash code.
	 */
	 @Override
	    public int hashCode() {
	        final int PRIME = 31;
	        int result = 1;
	        result = PRIME * result
	                + ((this.email == null) ? 0 : this.email.hashCode());
	        return result;
	    }
}

