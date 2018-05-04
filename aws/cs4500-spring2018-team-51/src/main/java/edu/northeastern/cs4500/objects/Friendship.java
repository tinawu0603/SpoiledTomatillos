package edu.northeastern.cs4500.objects;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Class that represents a friendship between two users. Two users are represented with their emails 
 * @author emilytrinh
 *
 */
@Embeddable
public class Friendship implements Serializable {

	/**
	 * Email address of one of the users in the friendship. 
	 */
	@Column(name = "user1_email")
	private String user1Email;

	/**
	 * Email address of the other user involved in the friendship.
	 */
	@Column(name = "user2_email")
	private String user2Email;

	/**
	 * General constructor for a friendship. 
	 */
	public Friendship() {
	}

	/**
	 * Constructor for a friendship which contains two users who are friends. 
	 * @param user1 email of one of the users of the friendship
	 * @param user2 email of the other user of the friendship. 
	 */
	public Friendship(String user1, String user2) {
		this.user1Email = user1;
		this.user2Email = user2;
	}

	/**
	 * Method that returns the email address of user1. 
	 * @return string of the email address of user 1
	 */
	public String getUser1() {
		return user1Email;
	}

	/**
	 * Method that returns the email address of user2. 
	 * @return string of the email address of user 2
	 */
	public String getUser2() {
		return user2Email;
	}

	/**
	 * Method that updates the email address of user1 with the given email. 
	 * @param user1Email email that will be used to update the email of user1 
	 */
	public void setUser1(String user1Email) {
		this.user1Email = user1Email;
	}

	/**
	 * Method that updates the email address of user2 with the given email. 
	 * @param user2Email email that will be used to update the email of user2 
	 */
	public void setUser2(String user2Email) {
		this.user2Email = user2Email;
	}

	/**
	 * Equals method that checks to see if two friendships are equal, that is if a friendship already exists between
	 * user 1 and user 2. 
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Friendship)) return false;
		Friendship that = (Friendship) o;
		return (this.getUser1().equals(that.getUser1()) &&
				this.getUser2().equals(that.getUser2()));
	}
	
	
	/**
	 * Overrides HashCode
	 */
	 @Override
	    public int hashCode() {
	        final int PRIME = 31;
	        int result = 1;
	        result = PRIME * result
	                + ((this.user1Email == null) ? 0 : this.user1Email.hashCode()) +
	                ((this.user2Email == null) ? 0 : this.user2Email.hashCode());
	        return result;
	    }
}
