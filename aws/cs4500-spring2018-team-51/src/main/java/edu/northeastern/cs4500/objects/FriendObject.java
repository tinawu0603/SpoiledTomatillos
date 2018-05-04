package edu.northeastern.cs4500.objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


/**
 * A FriendObject is a class that reprsents a friendship between two end users of Spolled Tomatillos. FriendObject also
 * corresponds to a mySQL table in the data base. 
 * @author emilytrinh
 *
 */
@Entity(name="friend_table")

public class FriendObject {


	/**
	 * Local variable that reprsents a friendship.
	 */
	@EmbeddedId
	private Friendship friendship;

	/**
	 * The status of a friend object can be friend, blocked, or pending.
	 */
	@Enumerated(EnumType.STRING)
	@Column (name = "status", nullable = false)
	private Status status;

	/**
	 * Action user of a friend object is the end user who sent the friend request. 
	 */
	@Column (name = "action_user", nullable = false)
	private String actionUser;

	/**
	 * General super constructor for a friend object
	 */
	public FriendObject() {
		super();
	}

	/**
	 * FriendObject represents a friendship between two users. 
	 * @param user1Email the email of one of the users involved in the friendship
	 * @param user2Email the email of the other user involved in the friendship
	 * @param stringStatus the status of the friendship between the users. The status can be friend, blocked, or pending. 
	 * @param actionUser the user who initiated the friendship. 
	 */
	public FriendObject(String user1Email, String user2Email, String stringStatus, String actionUser) {
		this.friendship = new Friendship(user1Email, user2Email);
		this.status = Status.NOTFRIEND;
		this.status = this.setStatus(stringStatus);
		this.actionUser = actionUser;
	}

	/**
	 * The email address of user1 of the friend object. 
	 * @return the email address of user1
	 */
	public String getUser1Email() {
		return friendship.getUser1();
	}

	/**
	 * The email address of user2 of the friend object. 
	 * @return the email address of user2
	 */
	public String getUser2Email() {
		return friendship.getUser2();
	}

	/**
	 * The status of the friend object between user1 and user2. The status is either not friend, friend, pending, or blocked. 
	 * @return the status of the friend object
	 */
	public String getStatus() {
		return status.getStatus();
	}

	/**
	 * The user who initiated the friend object. 
	 * @return the end user who initiated the friend object. 
	 */
	public String getActionUser() {
		return actionUser;
	}

	/**
	 * Updates the email of user1 with the given input
	 * @param user1Email the email that will be set as the first user's email
	 */
	public void setUser1Email(String user1Email) {
		this.friendship.setUser1(user1Email);
	}

	/**
	 * Updates the email of user2 with the given input
	 * @param user1Email the email that will be set as the second user's email
	 */
	public void setUser2Email(String user2Email) {
		this.friendship.setUser2(user2Email);
	}
	
	/**
	 * Updates the status of the friend object between user 1 and user 2
	 * @param update the new status of the friend object
	 * @return the new status of the friend object
	 */
	public Status setStatus(String update) {
		this.status = status.setStatus(update);
		return this.status;
	}

	/**
	 * Updates the action user of the friend object. 
	 * @param actionUser the new action user of the friend object. 
	 */
	public void setActionUser(String actionUser) {
		this.actionUser = actionUser;
	}

}
