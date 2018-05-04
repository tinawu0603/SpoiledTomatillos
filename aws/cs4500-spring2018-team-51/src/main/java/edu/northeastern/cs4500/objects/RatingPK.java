package edu.northeastern.cs4500.objects;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Rating PK which associates a user with a movieID
 * @author emilytrinh
 *
 */
@Embeddable
public class RatingPK implements Serializable {

	/**
	 * Represents the user of a RatingPK
	 */
	@Column(name = "user", nullable = false)
	private String user;

	/**
	 * Represents the movieID of a RatingPK
	 */
	@Column(name = "movie_ID", nullable = false)
	private String movieId;

	/**
	 * General constructor for a rating PK
	 */
	public RatingPK() {
		super();
	}

	/**
	 * Constructor for a rating PK, takes in a user and movie ID
	 * @param user end user who is associated with this ratingPK
	 * @param movie_ID unique movieID associated with this rating PK
	 */
	public RatingPK(String user, String movieId) {
		this.user = user;
		this.movieId = movieId;
	}

	/**
	 * Method that returns the user associated with this ratingPK
	 * @return end user who is associated with this ratingPK
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Method that returns the movie ID associated with this ratingPK
	 * @return movieID that is associated with this ratingPK
	 */
	public String getMovieId() {
		return movieId;
	}

	/**
	 * Method that updates the end user associated with this ratingPK
	 * @param user end user that will be used to update the user associated with this ratingPK
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Method that updates the movieID associated with this ratingPK
	 * @param movieID id that will be used to update the movieID associated with this ratingPK
	 */
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
}
