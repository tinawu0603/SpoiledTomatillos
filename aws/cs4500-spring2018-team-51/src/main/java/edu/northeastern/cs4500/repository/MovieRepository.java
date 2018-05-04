package edu.northeastern.cs4500.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import edu.northeastern.cs4500.objects.Movie;


/**
* The MovieRepository interface is an interface that extends JpaRepository. It manages creating, reading, updating,
* and deleting things from the the movie_table.
* 
*
* @author  Patrick Milne
* @version 1.0
* @since   2018-04-13 
*/
public interface MovieRepository extends JpaRepository<Movie, Integer> {

 	/**
 	 * This function allows a Movie to be deleted from the movie_table.
 	 * @param deleted is the Movie that is going to be deleted.
 	 * @return This does not return anything.
 	 */
	void delete(Movie deleted);
	
	 /**
	   * This function finds all the Movies in the movie_table.
	   * @return List<Movie> This returns a list of all the movies in the movie_table.
	   */
	@Query(value = "SELECT * FROM movie_table m", nativeQuery = true)
	List<Movie> findAll();
	
 	/**
 	 * This function finds a movie based on its movieID.
 	 * @param movieID is the id of the movie that is being searched for.
 	 * @return Movie This returns the movie associated with the movieID.
 	 */
	@Query(value = "SELECT * FROM movie_table m WHERE m.movie_ID = :movieID", nativeQuery = true)
	Movie findById(@Param("movieID") String movieID);
	
 	/**
 	 * This function finds the rating of a movie based on the ratings of EndUsers.
 	 * @param movieID is the id of the movie that is being searched for.
 	 * @return Double This returns the rating of the movie associated with the movieID.
 	 */
	@Query(value = "SELECT user_rating FROM movie_table m WHERE m.movie_ID =:movieID", nativeQuery = true)
	Double getUserRating(@Param("movieID") String movieID);
	
 	/**
 	 * This function updates the user rating of a movie.
 	 * @param movieId is the id of the movie that is being searched for.
 	 * @param rating is the new rating that the user_rating will be set to.
 	 * @return Double This returns the rating of the movie associated with the movieId.
 	 */
	@Transactional
	@Modifying
	@Query(value = "UPDATE movie_table m SET m.user_rating =:rating WHERE m.movie_ID =:movieId", nativeQuery = true)
	void setRating(@Param("movieId") String movieId, @Param("rating") double rating);
	
	
 	/**
 	 * This function updates the number of ratings of a movie.
 	 * @param movieId is the id of the movie that is being searched for.
 	 * @param numRatinsg is the number of ratings of the movie associated with the movieId.
 	 * @return This does not return anything.
 	 */
	@Transactional
	@Modifying
	@Query(value = "UPDATE movie_table m SET m.number_of_ratings =:numRatings WHERE m.movie_ID =:movieId", nativeQuery = true)
	void incrementCount(@Param("movieId") String movieId, @Param("numRatings") int numRatings);
	
	/**
	 * This function finds all the movies of a specific genre and orders them from highest rating to lowest rating.
	 * @param genre This is the genre being searched for.
	 * @return List<Movie>  all the movies of a specific genre and orders them from highest rating to lowest rating.
	 */
	@Query(value = "SELECT * FROM movie_table m WHERE m.genre_1 = :genre OR m.genre_2 =:genre OR m.genre_3 =:genre OR m.genre_4 =:genre "
			+ "ORDER BY m.user_rating DESC", nativeQuery = true)
	List<Movie> searchTopGenre(@Param("genre") String genre);
	
	/**
	 * This function finds all the movies and orders them from highest rating to lowest rating.
	 * @return List<Movie>  all the movies ordered from highest rating to lowest rating.
	 */
	@Query(value = "SELECT * FROM movie_table m ORDER BY m.user_rating DESC", nativeQuery = true)
	List<Movie> topRated();
	
	
	/**
	 * This function finds all the movies and orders them from most ratings to least ratings.
	 * @return List<Movie>  all the movies ordered from most ratings to least ratings.
	 */
	@Query(value = "SELECT * FROM movie_table m ORDER BY m.number_of_ratings DESC", nativeQuery = true)
	List<Movie> mostRated();
}
