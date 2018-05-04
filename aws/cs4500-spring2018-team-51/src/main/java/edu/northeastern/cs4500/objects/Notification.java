package edu.northeastern.cs4500.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Class that represents that notifications that are sent by and received by users. Notifications are sent when users
 * add friends, receive movie recommendations, and more. 
 * @author emilytrinh
 *
 */
@Entity(name="notification_table")
public class Notification {

	/**
	 * Unique id associated with each notification. 
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique=true)
	private int id;

	/**
	 * User who receives the notification. 
	 */
	@Column(name = "receiver", nullable = false)
	private String receiver;

	/**
	 * User who sends the notification. 
	 */
	@Column(name = "sender", nullable = false)
	private String sender;

	/**
	 * Message that is assocaited with the notification. 
	 */
	@Column(name = "message", nullable = false)
	private String message;

	/**
	 * Boolean that is "true" if the notification has been viewed and "false" if the notification is still unread. 
	 */
	@Column(name = "viewed", nullable = false)
	private Boolean viewed;
	
	/**
	 * Movie id that is associated with the movie that is involved in the notification. 
	 */
	@Column(name = "movie_id", nullable = true)
	private String movieId;

	/**
	 * Constructor of a notification that does not involve movies (i.e sending a friend request). Thus, the movie_id field is
	 * null. 
	 * @param receiver user who receives the notification
	 * @param sender user who sends the notification 
	 * @param message message that is associated with the notification. 
	 */
	public Notification(String receiver, String sender, String message) {
		this.receiver = receiver;
		this.sender = sender;
		this.message = message;
		this.id = 0;
		this.movieId = null;
		this.viewed = false;
	}
	
	/**
	 * Constructor of a notification that involves a movie (i.e recommending a movie to a friend). Thus, the movie_id field 
	 * is not null
	 * @param receiver user who receives the notification
	 * @param sender user who sends the notification
	 * @param message message that is associated with the notification
	 * @param movie_id movie that is associated with the notification. 
	 */
	public Notification(String receiver, String sender, String message, String movieId) {
		this.receiver = receiver;
		this.sender = sender;
		this.message = message;
		this.id = 0;
		this.movieId = movieId;
		this.viewed = false;
	}


	/**
	 * General notification constructor that does not contain anything. 
	 */
	public Notification() {

	}

	/**
	 * Method that gets the unique ID associated with this notification
	 * @return id that is unique to this notification
	 */
	public int getId() {
		return id;
	}

	/**
	 * Method that gets user who is receiving the notification
	 * @return string that represents the user who is receiving the notification 
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * Method that gets user who is sending the notification.
	 * @return string that represents the user who is sending the notification. 
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Method that gets the message associated with this notification. 
	 * @return string that represents the message associated with this notification
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Method that returns the status of if the notification was viewed or not, true represents that the notification
	 * was viewed whereas false represents that the notification has not been viewed. 
	 * @return boolean that represents if the notification has been viewed (true) or not (False)
	 */
	public Boolean getViewed() {
		return viewed;
	}

	/**
	 * Method that updates the unique id associated with this notification with the given input
	 * @param id input that will be used to update the id of this notification
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Method that returns the movie id associated with this notification
	 * @return string that has the movie id of the movie associated with this notification
	 */
	public String getMovieId() {
		return movieId;
	}

	/**
	 * Method that updates the receiver of this notification with the given input 
	 * @param receiver string that represents the receiver that this notification should be updated with 
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * Method that updates the sender of this notification with the given input 
	 * @param sender string that represents the sender that this notification should be updated with 
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * Method that updates the message of this notification with the given input 
	 * @param message string that represents the message that this notification should be updated with 
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Method that updates the boolean if the notification has been viewed
	 * @param viewed boolean input that represents if the notification has been viewed (true) or not (false)
	 */
	public void setViewed(Boolean viewed) {
		this.viewed = viewed;
	}
}
