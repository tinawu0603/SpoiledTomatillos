package edu.northeastern.cs4500.objects;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import edu.northeastern.cs4500.objects.Genre;

/**
 * Movie class that represents a movie for Spoilled Tomatillos. Movies are composed of information that is retrieved
 * from the TMDB database. 
 * @author emilytrinh
 *
 */
@Entity(name="movie_table")
public class Movie {

	/**
	 * Integer that represents a unique movie ID for each movie. 
	 */
	@Id
	@Column(name = "movie_ID", unique=true, nullable = false)
	private String movieId;

	/**
	 * Title of a movie. 
	 */
	@Column(name = "title", nullable = false)
	private String title;

	/**
	 * Year the movie was created/released. 
	 */
	@Column(name = "year")
	private String year;

	/**
	 * Average rating of the movie from TMDB.
	 */
	@Column(name = "rating")
	private String rating;

	/**
	 * Average user rating of the movie from Spoilled Tomatillos. 
	 */
	@Column(name = "user_rating")
	private double userRating;

	/**
	 * Number of times the movie has been rated on Spoilled Tomatillos. 
	 */
	@Column(name = "number_of_ratings")
	private int numberOfRatings;

	/**
	 * Plot summary of the movie from TMDB. 
	 */
	@Column(name = "plot")
	private String plot;

	/**
	 * Poster of the movie from TMDB. 
	 */
	@Column(name = "poster")
	private String poster;

	/**
	 * One of the four genres associated with the movie. 
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "genre_1")
	private Genre genre1;

	/**
	 * One of the four genres associated with the movie. 
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "genre_2")
	private Genre genre2;

	/**
	 * One of the four genres associated with the movie. 
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "genre_3")
	private Genre genre3;

	/**
	 * One of the four genres associated with the movie. 
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "genre_4")
	private Genre genre4;



	/**
	 * General super constructor for a movie. 
	 */
	public Movie() {
		super();
	}

	/**
	 * Method that returns the unique movie ID for a movie in the database. 
	 * @return string that represents the movie ID of a movie. 
	 */
	public String getMovieID() {
		return movieId;
	}

	/**
	 * Method that returns the title of a movie in the database.
	 * @return string that represents the title of a movie. 
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Method that returns the plot of a movie in the database.
	 * @return string that represents the plot/summary of a movie. 
	 */
	public String getPlot() {
		return plot;
	}

	/**
	 * Method that returns the poster of a movie. 
	 * @return string that represents the poster of a movie. 
	 */
	public String getPoster() {
		return poster;
	}

	/**
	 * Constructor of a movie. Contains a movieID, title, year, rating, plot, poster, and up to four genres. 
	 * @param movieID unique movieID that represents a movie
	 * @param title title of the movie
	 * @param year year the movie was released
	 * @param rating average rating of the movie as imported from TMDB
	 * @param plot plot of the movie
	 * @param poster URL poster of the movie
	 * @param genre1 one of the four genres a movie can be associated with
	 * @param genre2 one of the four genres a movie can be associated with
	 * @param genre3 one of the four genres a movie can be associated with
	 * @param genre4 one of the four genres a movie can be associated with
	 */
	public Movie(String movieID, String title, String year, String rating, String plot, String poster,
			String genre1, String genre2, String genre3, String genre4) {
		this.movieId = movieID;
		this.title = title;
		this.year = year;
		this.rating = rating;
		this.userRating = 0.0;
		this.numberOfRatings = 0;
		this.plot = plot;
		this.poster = poster;
		if (genre1 != null) {
			this.genre1 = Genre.setGenreString(genre1);
		}

		if (genre2 != null) {
			this.genre2 = Genre.setGenreString(genre2);
		}

		if (genre3 != null) {
			this.genre3 = Genre.setGenreString(genre3);
		}

		if (genre4 !=  null) {
			this.genre4 = Genre.setGenreString(genre4);
		}
	}
	
	/**
	 * Constructor of a movie, contains movie ID, title, year, rating, plot, and poster. This constructor does not have genre.
	 * @param movieID unique movieID that represents a movie
	 * @param title title of the movie
	 * @param year year the movie was released
	 * @param rating average rating of the movie as imported from TMDB
	 * @param plot plot of the movie
	 * @param poster URL poster of the movie
	 */
	public Movie(String movieID, String title, String year, String rating, String plot, String poster) {
		this.movieId = movieID;
		this.title = title;
		this.year = year;
		this.rating = rating;
		this.userRating = 0.0;
		this.numberOfRatings = 0;
		this.plot = plot;
		this.poster = poster;
	}

	/**
	 * Method that converts the movie ID and movie title to one string. 
	 */
	public String toString() {
		return movieId + ", " + title;
	}

	/**
	 * Method that gets the year the movie was released
	 * @return string with the year the movie was released
	 */
	public String getYear() {
		return year;
	}

	/**
	 * Method that updates the year that the movie was released
	 * @param year string that will be used to update the "year" of this movie. 
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * Method that returns the average rating from TMDB of the movie
	 * @return string that has the average rating of the movie
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * Method that updates the rating of this movie with the given rating
	 * @param rating string that will be used to update the movie's current rating
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * Method that returns the average user rating of this movie. 
	 * @return returns the double (numerical value) of the user's rating of this movie. 
	 */
	public double getUserRating() {
		return userRating;
	}

	/**
	 * Method that updates the user's rating with the given input
	 * @param userRatingsDisplay user's rating that will be used to update the current user rating of this movie. 
	 */
	public void setUserRating(double userRatingsDisplay) {
		this.userRating = userRatingsDisplay;
	}

	/**
	 * Method that returns the number of ratings that the movie has. 
	 * @return int that represents the number of ratings the movie has. 
	 */
	public int getNumberOfRatings() {
		return numberOfRatings;
	}

	/**
	 * Method that updates the number of ratings the movie has with the given input
	 * @param number_of_ratings number of ratings that will be used to udpate the current number of ratings of this movie. 
	 */
	public void setNumberOfRatings(int numberOfRatings) {
		this.numberOfRatings = numberOfRatings;
	}

	/**
	 * Method that returns the first genre associated with this movie. 
	 * @return first genre associated with this movie. 
	 */
	public Genre getGenre1() {
		return genre1;
	}

	/**
	 * Method that returns the second genre associated with this movie. 
	 * @return second genre associated with this movie. 
	 */
	public Genre getGenre2() {
		return genre2;
	}

	/**
	 * Method that returns the third genre associated with this movie. 
	 * @return third genre associated with this movie. 
	 */
	public Genre getGenre3() {
		return genre3;
	}

	/**
	 * Method that returns the fourth genre associated with this movie. 
	 * @return fourth genre associated with this movie. 
	 */
	public Genre getGenre4() {
		return genre4;
	}

	/**
	 * Method that updates the first genre of this movie with the given input
	 * @param genre_1 string representation of the genre that will replace the first genre associated with this movie. 
	 */
	public void setGenre1(String genre1) {
		this.genre1 = Genre.setGenreString(genre1);
	}

	/**
	 * Method that updates the second genre of this movie with the given input
	 * @param genre_2 string representation of the genre that will replace the second genre associated with this movie. 
	 */
	public void setGenre2(String genre2) {
		this.genre2 = Genre.setGenreString(genre2);
	}

	/**
	 * Method that updates the third genre of this movie with the given input
	 * @param genre_3 string representation of the genre that will replace the third genre associated with this movie. 
	 */
	public void setGenre3(String genre3) {
		this.genre3 = Genre.setGenreString(genre3);
	}

	/**
	 * Method that updates the fourth genre of this movie with the given input
	 * @param genre_4 string representation of the genre that will replace the fourth genre associated with this movie. 
	 */
	public void setGenre4(String genre4) {
		this.genre4 = Genre.setGenreString(genre4);
	}

	/**
	 * Method that updates the first genre of this movie with the given input
	 * @param genre_1 int representation of the genre that will replace the first genre associated with this movie. 
	 */
	public void setGenre1(int genre1) {
		this.genre1 = Genre.setGenreInt(genre1);
	}

	/**
	 * Method that updates the second genre of this movie with the given input
	 * @param genre_2 int representation of the genre that will replace the second genre associated with this movie. 
	 */
	public void setGenre2(int genre2) {
		this.genre2 = Genre.setGenreInt(genre2);
	}

	/**
	 * Method that updates the third genre of this movie with the given input
	 * @param genre_3 int representation of the genre that will replace the third genre associated with this movie. 
	 */
	public void setGenre3(int genre3) {
		this.genre3 = Genre.setGenreInt(genre3);
	}

	/**
	 * Method that updates the fourth genre of this movie with the given input
	 * @param genre_4 int representation of the genre that will replace the fourth genre associated with this movie. 
	 */
	public void setGenre4(int genre4) {
		this.genre4 = Genre.setGenreInt(genre4);
	}

	/**
	 * Returns the list of genres that are associated with this movie. 
	 * @return list of genres that are associated with this movie
	 */
	public List<Genre> getGenres() {
		List<Genre> result = new ArrayList<>();
		if (this.genre1 != null) {
			result.add(genre1);
		}
		if (this.genre2 != null) {
			result.add(genre2);
		}
		if (this.genre3 != null) {
			result.add(genre3);
		}
		if (this.genre4 != null) {
			result.add(genre4);
		}
		return result;
	}

}