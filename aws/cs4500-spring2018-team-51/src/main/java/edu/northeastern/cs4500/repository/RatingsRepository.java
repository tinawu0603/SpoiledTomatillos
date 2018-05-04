package edu.northeastern.cs4500.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import edu.northeastern.cs4500.objects.Rating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
* The RatingsRepository interface is an interface that extends JpaRepository. It manages creating, reading, updating,
* and deleting things from the the ratings_table.
* 
*
* @author  Patrick Milne
* @version 1.0
* @since   2018-04-13 
*/
@Repository
public interface RatingsRepository extends JpaRepository<Rating, String> {
    /**
	   * This function saves a Rating in the ratings_table.
	   * @param persisted is the rating that is going to be saved.
	   * @return Rating This returns the Rating that was added to the database.
	   */
	Rating save(Rating persisted);
	
	 /**
	   * This function updates the Rating associated with the user and movie_ID to the new rating and review.
	   * @param user is the user who is submitting the updated rating.
	   * @param movieId is the id of the movie who's rating is being updated.
	   * @param rating is the new rating of the movie by the user.
	   * @param review is the new review of the movie by the user.
	   * @return This does not return anything.
	   */
	@Transactional
	@Modifying
	@Query(value="UPDATE ratings_table r SET r.rating = :rating, r.review = :review WHERE r.user = :user AND r.movie_ID = :movieId", nativeQuery = true)
	void updateRating(@Param("user") String user, @Param("movieId") String movieId, @Param("rating") double rating, @Param("review") String review);

    /**
	   * This function finds the Rating of the associated movie_ID made by the user associated with the parameter user.
	   * @param user is the user who's rating is being searched for.
	   * @param movieId is the id of the movie that is being searched for. 
	   * @return Rating This returns the Rating of the movie Id that was rated by the user.
	   */
	@Query(value="SELECT * FROM ratings_table r WHERE r.user =:user AND r.movie_ID = :movieId", nativeQuery = true)
	Rating findUserRating(@Param("user") String user, @Param("movieId") String movieId);

    /**
	   * This function finds all the Ratings that a user has made.
	   * @param user is the user who's ratings are being searched for.
	   * @return List<Rating> This returns a list of Ratings that the user has made.
	   */
	@Query(value="SELECT * FROM ratings_table r WHERE r.user =:user", nativeQuery = true)
	List<Rating> findAllUserRatings(@Param("user") String user);
	
    /**
	   * This function finds all the Ratings of a movie.
	   * @param movieID is the Movie who's ratings are being searched for.
	   * @return List<Rating> This returns a list of all the Ratings of the movie that has the given movieID.
	   */
	@Query(value="SELECT * FROM ratings_table r WHERE r.movie_ID = :movieID", nativeQuery = true)
	List<Rating> findAllRatings(@Param("movieID") String movieID);
	
	
    /**
	   * This function finds all the Reviews of a movie.
	   * @param movieID is the Movie who's ratings are being searched for.
	   * @return List<String> This returns a list of all the reviews of that has the given movieID.
	   */
	@Query(value="SELECT review FROM ratings_table r WHERE r.movie_ID = :movieID AND r.review != NULL", nativeQuery = true)
	List<String> findAllReviews(@Param("movieID") String movieID);
	
    /**
	   * This function deletes a Rating of a movie from the database.
	   * @param user is the user who's rating is being deleted.
	   * @param movieID is the Movie who's ratings is being deleted.
	   * @return This does not return anything.
	   */
	@Transactional
	@Modifying
	@Query(value="DELETE FROM ratings_table WHERE user = :user AND movie_ID = :movieID", nativeQuery = true)
	void deleteRating(@Param("user") String user, @Param("movieID") String movieID);

}
