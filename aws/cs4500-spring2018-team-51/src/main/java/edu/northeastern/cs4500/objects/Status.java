package edu.northeastern.cs4500.objects;

import edu.northeastern.cs4500.log4j2.Log;

/**
 * Status represents a class that relates to the various statuses a friend object can have. A friend object can either be
 * a friend, not friend, pending, or blocked.
 * @author emilytrinh
 *
 */
public enum Status {
	FRIEND, NOTFRIEND, PENDING, BLOCKED;


	/**
	 * Method that returns the status of the friendobject as a string
	 * @return string representation of the status. 
	 */
	public String getStatus() {
		switch (this) {
		case FRIEND:
			return "Friend";
		case BLOCKED:
			return "Blocked";
		case PENDING:
			return "Pending";
		default: 
			return "Not_Friend";
		}
	}

	/**
	 * Method that updates the status of a friend object based on the given status. 
	 * @param status the input status that the friend object will be updated to 
	 * @return the status that the friendobject is updated to
	 */
	public Status setStatus(String status) {
		switch (status.toLowerCase()) {
		case "friend": 
			return FRIEND;
		case "not_friend": 
			return NOTFRIEND;
		case "pending": 
			return PENDING;
		case "blocked": 
			return BLOCKED;
		default: 
			Log.reportError("Not a valid status type.");
			throw new IllegalArgumentException("Not a valid status type.");
		}
	}

}
