package edu.northeastern.cs4500.objects;

/**
 * Class that represents a dummy rating, to be used for testing post requests. 
 * @author emilytrinh
 *
 */
public class RatingDummy {
	
	/**
	 * Local variables to be used in the dummy rating
	 */
	private String name;
	private String movieID;
	private double rating;
	private String review;
	
	/**
	 * General constructor for a dummy rating. 
	 */
	public RatingDummy() {
		
	}

	/**
	 * Constructor for a rating which has the name of user who gave the rating, the unique movie ID,
	 *  the rating, and the review associated with the rating. 
	 * @param name represents the user who gave the rating
	 * @param movieID the unique ID of the movie that rating was for
	 * @param rating the numerical value of the rating that was given
	 * @param review the review associated with the movie. 
	 */
	public RatingDummy(String name, String movieID, double rating, String review) {
		this.name = name;
		this.movieID = movieID;
		this.rating = rating;
		this.review = review;
	}
	
	/**
	 * Constructor for a rating which has the name of the user who gave the rating, the movie ID of the movie the
	 * rating was given to, and the numerical value of the rating
	 * @param name user who gave the rating
	 * @param movieID unique id of the movie the rating was given to
	 * @param rating numerical value of the rating that was given. 
	 */
	public RatingDummy(String name, String movieID, double rating) {
		this.name = name;
		this.movieID = movieID;
		this.rating = rating;
		this.review = "No Review";
	}

	/**
	 * Method that returns the name of the user who gave the rating. 
	 * @return String that represents the user who gave the rating
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method that returns the movie ID of the movie the rating was given for
	 * @return string that contains the movie ID of the movie that the rating was given for. 
	 */
	public String getMovieID() {
		return movieID;
	}

	/**
	 * Method that returns the numerical value of the rating that was given
	 * @return numerical value of the rating that was given. 
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * Method that returns the review associated with the rating that was given
	 * @return review associated with the rating that was given. 
	 */
	public String getReview() {
		return review;
	}

	/**
	 * Method that updates the name of the user who gave the rating with the given input
	 * @param name String that will be used to update the name of the user who gave the rating
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method that updates the movie ID for the movie that a rating was given for
	 * @param movieID movieID that will be used to update the current movie ID
	 */
	public void setMovieID(String movieID) {
		this.movieID = movieID;
	}

	/**
	 * Method that updates the numerical value of the rating that was given
	 * @param rating numerical value that will be used to update the current rating of the movie. 
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}

	/**
	 * Method that updates the review associated with the rating
	 * @param review string that will be used to update the current review
	 */
	public void setReview(String review) {
		this.review = review;
	}

}
