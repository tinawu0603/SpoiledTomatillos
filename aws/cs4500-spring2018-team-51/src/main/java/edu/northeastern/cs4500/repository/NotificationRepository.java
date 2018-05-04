package edu.northeastern.cs4500.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import edu.northeastern.cs4500.objects.Notification;

/**
* The NotificationRepository interface is an interface that extends JpaRepository. It manages creating, reading, updating,
* and deleting things from the the notification_table.
* 
*
* @author  Patrick Milne
* @version 1.0
* @since   2018-04-13 
*/

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	
 	/**
 	 * This function allows a Notification to be deleted from the notification_table.
 	 * @param deleted is the Notification that is going to be deleted.
 	 * @return This does not return anything.
 	 */
	void delete(Notification deleted);
	  
	 /**
	   * This function finds all the Notifications in the notification_table.
	   * @return List<Notification> This returns a list of all the Notifications in the notification_table.
	   */
	List<Notification> findAll();
	
	 /**
	   * This function finds the Notification in the notification_table based on the id.
	   * @param id is the id of the Notification being searched for.
	   * @return List<Notification> This returns a list of all the Notifications in the notification_table.
	   */
    Notification findOne(int id);
    
    
    /**
	   * This function saves a Notification in the notification_table.
	   * @param persisted is the notification that is going to be saved.
	   * @return Notification This returns the Notification that was added to the database.
	   */
    Notification save(Notification persisted);
    
    
	 /**
	   * This function finds the Notification that is a movie recommendation from one user to another.
	   * @param sender is the user who sent the recommendation.
	   * @param receiver is the user who received the recommendation.
	   * @param movie_ID is the id of the movie that was recommended.
	   * @return List<Notification> This returns the Notification of the movie with the movie_ID that was recommended by the sender 
	   * and sent to the receiver in the notification_table.
	   */
    @Query(value="SELECT * FROM notification_table n WHERE n.receiver =:receiver AND n.sender =:sender AND movie_id =:movieId", nativeQuery = true)
	Notification findRecommendation(@Param("sender") String sender, @Param("receiver") String receiver, @Param("movieId") String movieId);
    
	 /**
	   * This function finds all the Notifications that are a movie recommendation to that users.
	   * @param receiver is the user who received the recommendation.
	   * @param recommended is the the String "%recommended%".
	   * @return List<Notification> This returns all the Notifications that are movie recommendations to the receiver.
	   */
    @Query(value="SELECT * FROM notification_table n WHERE n.receiver = :receiver AND message LIKE :recommended", nativeQuery = true)
    List<Notification> getAllRecommendations(@Param("receiver") String receiver, @Param("recommended") String recommended);
    
	 /**
	   * This function finds all the Notifications that have not been viewed by the user.
	   * @param receiver is the user who received the Notification.
	   * @return List<Notification> This returns all the Notifications that have not been viewed by the receiver.
	   */
    @Query(value="SELECT * FROM notification_table n WHERE n.receiver = :receiver AND n.viewed = FALSE", nativeQuery = true)
    List<Notification> getAllUnviewed(@Param("receiver") String receiver);
    
    
	 /**
	   * This function finds all the email of the user who received a Notification based on the notification's id.
	   * @param id is the id of the Notification.
	   * @return String This returns the email of the user who received this Notification.
	   */
    @Query(value="SELECT receiver FROM notification_table n WHERE n.id =:id", nativeQuery = true)
    String getReceiver(@Param("id") int id);
    
	 /**
	   * This function updates the Notification associated with the id to viewed.
	   * @param id is the id of the Notification.
	   * @return This does not return anything.
	   */
    @Transactional
    @Modifying
    @Query(value="UPDATE notification_table n SET n.viewed = TRUE WHERE n.id =:id")
    void makeViewed(@Param("id") int id);
	
	
}
