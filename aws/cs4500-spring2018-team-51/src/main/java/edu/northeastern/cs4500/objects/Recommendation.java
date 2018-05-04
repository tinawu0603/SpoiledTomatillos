package edu.northeastern.cs4500.objects;

import edu.northeastern.cs4500.objects.Notification;
import edu.northeastern.cs4500.objects.Movie;

/**
 * Recommendation represents the class that is associated with recommending movies to an end user. 
 * @author emilytrinh
 *
 */
public class Recommendation {
	/**
	 * Local variables that are used in a recommendation, specifically a notification and a movie. 
	 */
	private Notification n;
	private Movie m;
	
	/**
	 * Constructor for a recommendation
	 * @param n represents the notification associated with the recommendation
	 * @param m represents the movie associated with the recommendation
	 */
	public Recommendation(Notification n, Movie m) {
		this.n = n;
		this.m = m;
	}

	/**
	 * Method that returns the notification associated with this recommendation
	 * @return notification that is associated with this recommendation
	 */
	public Notification getN() {
		return n;
	}

	/**
	 * Method that returns the movie associated with this recommendation
	 * @return movie that is associated with this recommendation
	 */
	public Movie getM() {
		return m;
	}
}
