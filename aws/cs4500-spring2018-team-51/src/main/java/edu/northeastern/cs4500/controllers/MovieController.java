package edu.northeastern.cs4500.controllers;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import edu.northeastern.cs4500.repository.RatingsRepository;
import edu.northeastern.cs4500.log4j2.Log;
import edu.northeastern.cs4500.objects.EndUser;
import edu.northeastern.cs4500.objects.Genre;
import edu.northeastern.cs4500.objects.GenreObject;
import edu.northeastern.cs4500.objects.Movie;
import edu.northeastern.cs4500.objects.Notification;
import edu.northeastern.cs4500.objects.Rating;
import edu.northeastern.cs4500.objects.RatingDummy;
import edu.northeastern.cs4500.objects.Recommendation;
import edu.northeastern.cs4500.objects.SpoiledTomatillosException;
import edu.northeastern.cs4500.repository.EndUserRepository;
import edu.northeastern.cs4500.repository.MovieRepository;
import edu.northeastern.cs4500.repository.NotificationRepository;


/**
 * The MovieController class is a RestController that takes in arguments from the
 * front end and retrieves, inserts, or updates the database.
 * The repositories the MovieController class works with is the MovieRepository,
 * the NotificationRepository, the RatingsRepository, and the EndUserRepository.
 * 
 *
 * @author  Patrick Milne
 * @version 1.0
 * @since   2018-04-13 
 */
@ControllerAdvice
@RestController
@CrossOrigin
public class MovieController {


	private static MovieController movieControllerInstance = null;

	/**
	 * Singleton pattern for instantiating MovieController. Makes it so there can only be one
	 * instance of a MovieController.
	 * @return the instance of MovieController if there is already an instance created or
	 * a new MovieController if no instance has been created.
	 */
	public static MovieController getMovieControllerInstance() {
		if (movieControllerInstance == null) {
			movieControllerInstance = new MovieController();
		}
		return movieControllerInstance;
	}

	/**
	 * Constructor for a MovieController that can only be called by getMovieControllerInstance()
	 * Part of the singleton pattern
	 */
	private MovieController() { }

	@Autowired
	MovieRepository movieRepo;

	@Autowired
	NotificationRepository notificationRepo;

	@Autowired
	RatingsRepository ratingsRepo;

	@Autowired
	EndUserRepository userRepo;


	/**
	   * This function searches the TMDB database through the TmdbConnection class by title.
	   * @param title is the title of the movie the user is looking for.
	   * @return List<Movie> This returns a list of movies that have a similar title to the parameter title.
	   */
	@RequestMapping(value="/api/movie/title/{title}", method=RequestMethod.GET)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> searchMovieByTitle(@PathVariable(name="title") String title) {
		ArrayList<Movie> movies = new ArrayList<>();

		String jsonString = TmdbConnection.searchMovieByTitle(title);
		JSONObject moviesJsonObj = new JSONObject(jsonString);
		JSONArray search = moviesJsonObj.getJSONArray("results");
		int count = search.length();
		for(int i = 0; i < count; i++) {
			JSONObject movieJsonObj = search.getJSONObject(i);
			Movie movie = this.getJsonMovie(movieJsonObj, "genre_ids");
			if (this.findMoviebyID(movie.getMovieID())!= null) {
				movies.add(this.findMoviebyID(movie.getMovieID()));
			}
			else  {
				movies.add(movie);
			}
		}
		Log.reportEvent("User has searched for movie, "+ title + " by title.");
		return movies;
	}



	/**
	 * This function searches the TMDB database through the TmdbConnection class by id.
	 * @param movieID is the id of the movie the user is looking for.
	 * @return Movie This returns a movie that has the same id as the parameter movie_ID.
	 */
	@CrossOrigin
	@RequestMapping(value="/api/movie/id/{id}", method=RequestMethod.GET)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Movie searchMovieById(@PathVariable(name="id") String movieId)  {
		String jsonString = TmdbConnection.searchMovieById(movieId);

		JSONObject movieJsonObj = new JSONObject(jsonString);
		Movie movie = this.getJsonMovie(movieJsonObj, "genres");
		
		Log.reportEvent("User has searched for a movie by ID");
		return movie;
	}
	
	/**
	 * This function creates a new movie object based on the input JSONObject.
	 * @param movieJsonObj is the json object containing the information to create a new movie object.
	 * @return Movie This returns a movie based on the input JSONObject.
	 */
	public Movie getJsonMovie(JSONObject movieJsonObj, String genre) {
		int movieID = movieJsonObj.getInt("id");
		String t = movieJsonObj.getString("title");
		String year = movieJsonObj.getString("release_date");
		double rating = movieJsonObj.getDouble("vote_average");
		String plot = movieJsonObj.getString("overview");
		Object poster = movieJsonObj.get("poster_path");
		String movieString = Integer.toString(movieID);
		String ratingString = Double.toString(rating);

		String posterString = "http://image.tmdb.org/t/p/w185/" + poster;

		String yearString = year.substring(0, Math.min(year.length(), 4));

		String genre1 = null;
		String genre2 = null;
		String genre3 = null;
		String genre4 = null;

		JSONArray genres = movieJsonObj.getJSONArray(genre);
		int k = 0;
		int l = genres.length();
		if (genre.equals("genre_ids")) {
			if (k < l) {
				int gen1 = genres.getInt(0);
				genre1 = Genre.setGenreInt(gen1).toString();
				k++;
			}
			if (k < l) {
				int gen2 = genres.getInt(1);
				genre2 = Genre.setGenreInt(gen2).toString();
				k++;
			}
			if (k < l) {
				int gen3 = genres.getInt(2);
				genre3 = Genre.setGenreInt(gen3).toString();
				k++;
			}
			if (k < l) {
				int gen4 = genres.getInt(3);
				genre4 = Genre.setGenreInt(gen4).toString();
			}
		return new Movie(movieString, t, yearString, ratingString, plot, posterString, genre1, genre2,
				genre3, genre4);
		}
		else {
			if (k < l) {
				genre1 = genres.getJSONObject(0).getString("name");
				k++;
			}
			if (k < l) {
				genre2 = genres.getJSONObject(1).getString("name");
				k++;
			}
			if (k < l) {
				genre3 = genres.getJSONObject(2).getString("name");
				k++;
			}
			if (k < l) {
				genre4 = genres.getJSONObject(3).getString("name");
			}
		return new Movie(movieString, t, yearString, ratingString, plot, posterString, genre1, genre2,
				genre3, genre4);
		}
	}

	/**
	 * This function finds all the movies in our database through the MovieRepository class.
	 * @return List<Movie> This returns a list of all the movies that our in are database.
	 */
	@RequestMapping("/api/movie") 
	@ResponseBody
	public List<Movie> findAllMovies() {
		return movieRepo.findAll();
	}


	/**
	 * This function searches our database through the MovieRepository class by id.
	 * @param movieId is the id of the movie the user is looking for.
	 * @return Movie This returns a movie that has the same id as the parameter movie_ID.
	 */
	@RequestMapping("/api/movie/{movieId}")
	@ResponseBody
	public Movie findMoviebyID(@PathVariable(name = "movieId") String movieID) {
		return movieRepo.findById(movieID);
	}	

	/**
	 * This function searches the TMDB database through the TmdbConnection class by id and then adds that movie to our database
	 * through the MovieRepository class.
	 * @param movie_ID is the id of the movie the user is looking for.
	 * @return Movie This returns the movie that was inserted into our database.
	 */
	@RequestMapping(value="/api/movie/insert/{movieId}")
	@ResponseBody
	public Movie insertMovie(@PathVariable(name = "movieId") String movieId) {
		Movie m = this.searchMovieById(movieId);
		Log.reportEvent("Backend has added movie " + movieId + " to the database");
		return movieRepo.save(m);
	}

	/**
	 * This function recommends a movie to the user. The function works by determining the top three genres based on the movies
	 * the user has rated. It then searches the TMDB database for the most popular movies of those three genres. It then selects
	 * five movies from those lists of most popular movies that the user has not rated. It then returns one of those movies at
	 * random.
	 * @param user is the email of the user who is getting a recommendation.
	 * @return Movie This returns a movie recommendation based on the user's ratings of movies and the TMDB ratings of movies.
	 */
	@RequestMapping(value="/api/movie/recommend/{user}", method=RequestMethod.GET)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Movie recommendFromTMDB(@PathVariable(name="user") String user) throws SpoiledTomatillosException {
		List<Genre> gens = this.getTopGenres(user);
		ArrayList<Movie> movies = new ArrayList<>();
		Movie m;
		for (int j = 0; j < gens.size(); j++) {
			//p is the page number
			int p = 1;
			int added = 0;
			while (added < 5 && p < 5) {
				int genre = gens.get(j).getGenreInt();
				String jsonString = TmdbConnection.searchTopGenre(genre, p);
				JSONObject moviesJsonObj = new JSONObject(jsonString);
				JSONArray search = moviesJsonObj.getJSONArray("results");
				int count = search.length();
				for(int i = 0; i < count && added < 5; i++) {
					JSONObject movieJsonObj = search.getJSONObject(i);
					int movieID = movieJsonObj.getInt("id");
					double rating = movieJsonObj.getDouble("vote_average");
					String movieId = Integer.toString(movieID);
					m = this.searchMovieById(movieId);
					if (!this.rated(user, movieId) && rating > 7.5 && !movies.contains(m)) {
						movies.add(m);
						added++;
					}
				}
				p++;
			}
		}
		int randomMovie = ThreadLocalRandom.current().nextInt(0, movies.size());

		return movies.get(randomMovie);
	}

	
	 /**
	   * This function recommends a movie to the user. The function works by determining the top three genres based on the movies
	   * the user has rated. It then searches the TMDB database for the most popular movies of those three genres. It then selects
	   * five movies from those lists of most popular movies that the user has not rated. It then returns one of those movies at
	   * random.
	   * @param user is the email of the user who is getting a recommendation.
	   * @return Movie This returns a movie recommendation based on the user's ratings of movies and the TMDB ratings of movies.
	 * @throws SpoiledTomatillosException 
	   */
	@RequestMapping(value="/api/movie/recommend2/{user}", method=RequestMethod.GET)
	@GET
  @Produces(MediaType.APPLICATION_JSON)
	public Movie recommendFromST(@PathVariable(name = "user") String user) throws SpoiledTomatillosException {

		List<Genre> gens = this.getTopGenres(user);

		ArrayList<Movie> movies = new ArrayList<>();
		for (int j = 0; j < gens.size(); j++) {
			int added = 0;
			List<Movie> movieList = movieRepo.searchTopGenre(gens.get(j).toString());
			for (int i = 0; i < movieList.size() && added < 5; i++) {

				if (!this.rated(user, movieList.get(i).getMovieID()) && !movies.contains(movieList.get(i))) {
					movies.add(movieList.get(i));
					added++;
				}

			}
		}

		int randomMovie = ThreadLocalRandom.current().nextInt(0, movies.size());
		return movies.get(randomMovie);
	}
	
	
	 /**
	   * This function allows a user to recommend a movie to another user by inserting a recommendation notification through NotificationRepository.
	   * @param sender is the email of the user who is sending the movie recommendation.
	   * @param receiver is the email of the user who is receiving the movie recommendation.
	   * @param moveID is the id of the movie the sender is recommending.
	   * @return Notification This returns the Notification that was created and inserted into our database through the NotificationRepository class.
	   */

	@RequestMapping(value="/api/movie/recommend/{sender}/{receiver}/{movieID}")
	@ResponseBody
	public Notification recommendMovie(@PathVariable(name = "sender") String sender, @PathVariable(name = "receiver") String receiver, 
			@PathVariable(name = "movieID") String movieID) throws SpoiledTomatillosException{

		Movie m = this.searchMovieById(movieID);
		if (this.findRecommendation(sender, receiver, movieID)) {
			Log.reportError("User has already recommended this movie to this other user.");
			throw new SpoiledTomatillosException("You have already recommended this movie to this user");
		}
		EndUser u = userRepo.findByEmail(sender);
		String message = (u.getFullName()+ " recommended the movie " + m.getTitle() + 
				"(id:" + m.getMovieID() +")");

		Notification no = new Notification(receiver, sender, message, m.getMovieID());
		notificationRepo.save(no);
		Log.reportEvent(sender + " has recommended a movie " + m.getTitle() + " to user " + receiver);
		return no;
	}


	/**
	 * This function checks whether or not the user sender has recommended the movie to the receiver through using the NotificationRepository class.
	 * @param sender is the email of the user who is sending the movie recommendation.
	 * @param receiver is the email of the user who is receiving the movie recommendation.
	 * @param moveID is the id of the movie the sender is recommending.
	 * @return boolean This returns true if the sender has recommended this movie to the receiver already, otherwise returns false. 
	 */
	@RequestMapping(value="/api/movie/findrecommendation/{sender}/{receiver}/{movieID}")
	@ResponseBody
	public boolean findRecommendation(@PathVariable(name = "sender") String sender, @PathVariable(name = "receiver") String receiver, 
			@PathVariable(name = "movieID") String movieID) {
		Movie m = this.searchMovieById(movieID);
		String movieTitle = m.getTitle();

		if (notificationRepo.findRecommendation(sender, receiver, movieID) == null) {
			return false;
		}
		else { 
			Log.reportEvent("Found review for movie " + movieTitle + " from " + sender + " to " + receiver);
			return true;
		}
	}

	/**
	 * This function returns a list of all the movie recommendations the user has received.
	 * @param receiver is the email of the user who is received the movie recommendations.
	 * @return List<Recommendation> This returns a list of all the movie Recommendations the user with the email receiver has received. 
	 */
	@RequestMapping(value="/api/movie/getAllRecommendations/{receiver}")
	@ResponseBody
	public List<Recommendation> getAllRecommendations(@PathVariable(name = "receiver") String receiver) {
		Log.reportEvent("All recommendations for user " + receiver );
		List<Notification> tempList = notificationRepo.getAllRecommendations(receiver, "%recommended%");
		List<Recommendation> returnList = new ArrayList<>();
		for (int i = 0; i < tempList.size(); i++) {
			Recommendation r = new Recommendation(tempList.get(i), this.searchMovieById(tempList.get(i).getMovieId()));
			returnList.add(r);		
		}
		return returnList;
	}

	/**
	 * This function returns a list of all the unviewed Notifications the user has received.
	 * @param receiver is the email of the user who is received the Notifications.
	 * @return List<Notification> This returns a list of all the Notification the user with the email receiver has received and not viewed. 
	 */
	@RequestMapping(value="/api/movie/getUnviewed/{receiver}")
	@ResponseBody
	public List<Notification> seeAllUnviewed(@PathVariable(name = "receiver") String receiver) {
		Log.reportEvent("Found all unviewed notifications for user " + receiver);
		return notificationRepo.getAllUnviewed(receiver);
	}


	/**
	 * This function returns a list of all the movie recommendations the user has received after marking a Notification as viewed.
	 * @param id is the id of the Notification the user is marking as viewed.
	 * @return List<Notification> This returns a list of all the Notification the receiver of the Notification with the same id as the parameter id 
	 * that have not been viewed. 
	 */
	@RequestMapping(value="/api/movie/markViewed/{id}")
	@ResponseBody
	public List<Notification> markAsViewed(@PathVariable(name = "id") int id) {
		String receiver = notificationRepo.getReceiver(id);
		notificationRepo.makeViewed(id);
		return notificationRepo.getAllUnviewed(receiver);
	}

	/**
	 * This function returns a list of all the Ratings a user has made.
	 * @param user is the email of the user.
	 * @return List<Rating> This returns a list of all the Ratings the user has made.
	 * @throws SpoiledTomatillosException 
	 */
	@RequestMapping(value="/api/rate/{user}")
	@ResponseBody
	public List<Rating> getUserRatings(@PathVariable(name = "user") String user) throws SpoiledTomatillosException {
		if (userRepo.findByEmail(user) == null) {
			Log.reportError("User does not exist.");
			throw new SpoiledTomatillosException("User does not exist");
		}
		else {
			Log.reportEvent("Found all user ratings for user" + user);
			return ratingsRepo.findAllUserRatings(user);
		}
	}


	/**
	 * This function returns whether or not the user has rated a specific movie.
	 * @param user is the email of the user.
	 * @param movieID is the id of the movie that is being looked up.
	 * @return boolean This returns true if the user has rated this movie already, false otherwise.
	 */
	@RequestMapping(value="/api/rate/{user}/{movieID}")
	@ResponseBody
	public boolean rated(@PathVariable(name = "user") String user, @PathVariable(name = "movieID") String movieID) {
		if (ratingsRepo.findUserRating(user, movieID) == null) {
			return false;
		}
		Log.reportEvent("Found rating for movie " + movieID + " by user " + user);
		return true;
	}

	/**
	 * This function returns the Rating the user gave of a specific movie.
	 * @param user is the email of the user.
	 * @param movieID is the id of the movie that is being looked up.
	 * @return Rating This returns the Rating that the user gave to the movie. It returns null if the user hasn't rated that movie.
	 */
	@RequestMapping(value="/api/getrating/{user}/{movieID}")
	@ResponseBody
	public Rating findARating(@PathVariable(name = "user") String user, @PathVariable(name = "movieID") String movieID) {
		return ratingsRepo.findUserRating(user, movieID);
	}

	/**
	 * This function returns all the Rating given to a specific movie.
	 * @param movieID is the id of the movie that is being looked up.
	 * @return List<Rating> This returns a list of all the Ratings that a movie with the id matching the parameter movieID have received.
	 */
	@RequestMapping(value="/api/getallratings/{movieID}")
	@ResponseBody
	public List<Rating> findAllRatings(@PathVariable(name = "movieID") String movieID) {
		return ratingsRepo.findAllRatings(movieID);
	}

	/**
	 * This function returns all the reviews given to a specific movie.
	 * @param movieID is the id of the movie that is being looked up.
	 * @return List<String> This returns a list of all the reviews that a movie with the id matching the parameter movieID have received.
	 */
	@RequestMapping(value="/api/getallreviews/{movieID}")
	@ResponseBody
	public List<String> findAllReviews(@PathVariable(name = "movieID") String movieID) {
		return ratingsRepo.findAllReviews(movieID);
	}


	/**
	 * This function rates a movie. If the user has already rated the movie, the rating is updated. If the user has not rated the movie before,
	 * a new rating is created using the fields in the RatingDummy and is added to our database. 
	 * @param rates is a RatingDummy object containing the the movieID, the user email, the rating and the review.
	 * @return Rating This returns the rating that was updated or the new rating that was added to the database.
	 * @throws SpoiledTomatillosException 
	 */
	@RequestMapping(value = "/api/rate", method = RequestMethod.POST)
	public Rating updateMovieRating(@RequestBody RatingDummy rates) throws SpoiledTomatillosException  {
		Rating rate;
		if (rates.getReview() == null) {
			rate = new Rating(rates.getName(), rates.getMovieID(), rates.getRating());
		}
		else {
			rate = new Rating(rates.getName(), rates.getMovieID(), rates.getRating(), rates.getReview());
		}

		Rating result = null;
		if (rate.getRating() > 10.0 || rate.getRating() < 0.0) {
			Log.reportError("Rating is not in the corrrect range, must be between 0 and 10.");
			throw new SpoiledTomatillosException("Rating not in correct range");
		}

		if (null == this.findMoviebyID(rate.getMovieID())) {
			this.insertMovie(rate.getMovieID());
			Log.reportEvent("Added a new movie rating for movie " + rate.getMovieID() + "by user " + rate.getName());
		}

		if (ratingsRepo.findUserRating(rate.getName(), rate.getMovieID()) != null) {
			Rating r = ratingsRepo.findUserRating(rate.getName(), rate.getMovieID());
			double previous = r.getRating();

			if (rate.getReview() == null) {
				ratingsRepo.updateRating(rate.getName(), rate.getMovieID(), rate.getRating(), r.getReview());
			}
			else {
				ratingsRepo.updateRating(rate.getName(), rate.getMovieID(), rate.getRating(), rate.getReview());
			}

			Movie m = this.findMoviebyID(rate.getMovieID());
			double userRating = movieRepo.getUserRating(rate.getMovieID());
			int count = m.getNumberOfRatings();

			userRating = (userRating - (previous / (double)count) + (rate.getRating() / (double)count));
			movieRepo.setRating(rate.getMovieID(), userRating);
			Log.reportEvent("User " + rate.getName() +  "has rated this movie " + rate.getMovieID());
			return ratingsRepo.findUserRating(rate.getName(), rate.getMovieID());
		}
		else {	
			result = new Rating(rate.getName(), rate.getMovieID(), rate.getRating(), rate.getReview());
			ratingsRepo.save(result);
			Movie m = this.findMoviebyID(rate.getMovieID());
			double userRating = movieRepo.getUserRating(rate.getMovieID());
			int count = m.getNumberOfRatings();
			count++;
			userRating =  (userRating * ((double)count - 1) / (double)count) + (rate.getRating() * 1 / (double)count);
			movieRepo.setRating(rate.getMovieID(), userRating);
			movieRepo.incrementCount(rate.getMovieID(), count);
			return result;		
		}
	}

	/**
	 * This function returns the top 3 genres of a user. This is determined by calculating the average rating of movies of each genre and adding .1
	 * to that average for each rating the user has made of that genre. Thus, the number of movies a user watches of a specific genre is factored into
	 * determining the top genre.
	 * @param user is the email of the user
	 * @return List<Genre> This returns a list of the top 3 genres based on the uers's ratings of movies.
	 * @throws SpoiledTomatillosException 
	 */
	public List<Genre> getTopGenres(String user) throws SpoiledTomatillosException {
		List<Rating> ratings = this.getUserRatings(user);
		Map<Genre, GenreObject> map = new EnumMap<>(Genre.class);
		Set<GenreObject> set = new TreeSet<>();
		for (int i = 0; i < ratings.size(); i++) {
			String movieID = ratings.get(i).getMovieID();
			Movie m = this.findMoviebyID(movieID);
			List<Genre> g = m.getGenres();
			for (int k = 0; k < g.size(); k++) {
				//If the Genre has been added to the HashMap
				if (map.containsKey(g.get(k))) {
					//Get the GenreObject
					GenreObject obj = map.get(g.get(k));
					//Update the count and average field
					obj.addRating(ratings.get(i).getRating());
					//Put the updated object back in the HashMap
					map.put(g.get(k), obj);
					//Put the updated object into the set
					set.add(obj);
				}
				else {
					GenreObject obj = new GenreObject(g.get(k));
					obj.addRating(ratings.get(i).getRating());
					map.put(g.get(k), obj);
					set.add(obj);
				}

			}
		}
		List<Genre> result = new ArrayList<>();

		for (int l = 0; l < 3 && l < set.size(); l++) {
			result.add(((GenreObject) set.toArray()[l]).getGenre());
		}

		return result;	
	}
	
	 /**
	   * This function searches our database and returns all the movies from highest rating to lowest rating.
	   * @return List<Movie> This returns a list of all the movies ordered by rating.
	   */
	@RequestMapping(value="/api/movie/topRated")
	@ResponseBody
	public List<Movie> findTopRated() {
		return movieRepo.topRated();
	}
	
	 /**
	   * This function searches our database and returns all the movies of that genre from highest rating to lowest rating.
	   * @param genre is the genre that is being searched for.
	   * @return List<Movie> This returns a list of all the movies of the given genre ordered by rating.
	   */
	@RequestMapping(value="/api/movie/topRatedGenre/{genre}")
	@ResponseBody
	public List<Movie> findTopRated(@PathVariable(name="genre") String genre) {
		return movieRepo.searchTopGenre(genre);
	}
	
	 /**
	   * This function searches our database and returns all the movies from most ratings to least ratings.
	   * @return List<Movie> This returns a list of all the movies ordered by rating.
	   */
	@RequestMapping(value="/api/movie/mostRated")
	@ResponseBody
	public List<Movie> findMostRated() {
		return movieRepo.mostRated();
	}



}
