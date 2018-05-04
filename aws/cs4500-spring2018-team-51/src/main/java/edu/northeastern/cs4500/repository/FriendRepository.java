package edu.northeastern.cs4500.repository;

import java.util.List;


/**
* The FriendRepository interface is an interface that extends JpaRepository. It manages creating, reading, updating,
* and deleting things from the the friend_table.
* 
*
* @author  Patrick Milne
* @version 1.0
* @since   2018-04-13 
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import edu.northeastern.cs4500.objects.FriendObject;


public interface FriendRepository extends JpaRepository<FriendObject, String>{

 	/**
 	 * This function allows a FriendObject to be deleted from the friend_table.
 	 * @param deleted is the friendship that is going to be deleted.
 	 * @return This does not return anything.
 	 */
	void delete(FriendObject deleted);
	  
	
	 /**
	   * This function finds all the friendships in the friend_table.
	   * @return List<FriendObject> This returns a list of all the friendships in the friend_table.
	   */
	List<FriendObject> findAll();
 

    /**
	   * This function saves a friendObject in the friend_table.
	   * @param persisted is the friendship that is going to be saved.
	   * @return FriendObject This returns the FriendObject that was added to the database.
	   */
    FriendObject save(FriendObject persisted);
    
    
    /**
	   * This function finds all the emails associated with users that are friends with the user associated with the parameter email. 
	   * @param email is the email of the user whose friends are being searched for.
	   * @return List<String> This returns the emails of all the users friends with the user.
	   */
    @Query(value="(SELECT user2_email FROM friend_table ft WHERE ft.user1_email= :email AND ft.status = 'Friend') UNION\n" + 
    		"(SELECT user1_email FROM friend_table ft WHERE ft.user2_email = :email AND ft.status = 'Friend');", nativeQuery = true)
    List<String> findFriends(@Param("email") String email);
    
    
    /**
	   * This function finds the friendObject between two users. 
	   * @param user1 is the email of the first user.
	   * @param user2 is the email of the second user.
	   * @return FriendObject This returns the friendship between the two users.
	   */
    @Query(value="SELECT * FROM friend_table ft WHERE (ft.user1_email= :user1 AND ft.user2_email = :user2) OR "
    		+ "(ft.user1_email= :user2 AND ft.user2_email = :user1)", nativeQuery = true) 
    FriendObject findFriendship(@Param("user1") String user1, @Param("user2") String user2);
    
    /**
	   * This function finds all the friendships that the user associated with the parameter user has sent but have not been confirmed. 
	   * @param user is the email of the user whose unconfirmed sent friend requests are being searched for.
	   * @return List<FriendObject> This returns the list of FriendObjects that the user has sent but have not been confirmed.
	   */
    @Query(value="SELECT * FROM friend_table ft WHERE (ft.action_user=:user AND ft.status = 'Pending')", nativeQuery = true)
    List<FriendObject> getSent(@Param("user") String user);
    
    
    /**
	   * This function finds all the friendships that the user associated with the parameter user has received but has not been confirmed. 
	   * @param user is the email of the user whose unconfirmed received friend requests are being searched for.
	   * @return List<FriendObject> This returns the list of FriendObjects that the user has received but has not confirmed.
	   */
    @Query(value="SELECT * FROM friend_table ft WHERE (ft.user2_email=:user AND ft.status = 'Pending')", nativeQuery = true)
    List<FriendObject> getReceived(@Param("user") String user);
    
    /**
	   * This function updates the friendship between to user to confirm the friend request. 
	   * @param user1 is the email of the first user.
	   * @param user2 is the email of the second user.
	   * @return This does not return anything.
	   */
    @Transactional
    @Modifying
    @Query(value="UPDATE friend_table ft SET ft.status = 'Friend' WHERE (ft.user1_email = :user1 AND user2_email =:user2) OR "
    		+ "(ft.user1_email = :user2 AND user2_email =:user1)", nativeQuery = true)
    void confirmFriend(@Param("user1") String user1, @Param("user2") String user2);
    
    /**
	   * This function deletes the relationship between two users from the table. 
	   * @param user1 is the email of the first user.
	   * @param user2 is the email of the second user.
	   * @return This does not return anything.
	   */
    @Transactional
    @Modifying
    @Query(value="DELETE FROM friend_table ft WHERE (ft.user1_email = :user1 AND user2_email =:user2) OR "
    + "(ft.user1_email = :user2 AND user2_email =:user1)", nativeQuery = true)
    void removeFriendship(@Param("user1") String user1, @Param("user2") String user2);
    
    
    /**
	   * This function creates a new relationship in the table where user2 is blocked. 
	   * @param user1 is the email of the first user.
	   * @param user2 is the email of the second user.
	   * @return This does not return anything.
	   */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO friend_table ft VALUES(ft.user1_email = :user1, ft.user2_email = :user2, "
    		+ "ft.status = 'Blocked', ft.action_user = :user1)", nativeQuery = true)
    void block(@Param("user1") String user1, @Param("user2") String user2);
    
    
    /**
	   * This function removes a block on the user. Can only be done by the user who created the block. 
	   * @param user1 is the email of the first user.
	   * @param user2 is the email of the second user.
	   * @return This does not return anything.
	   */
    @Transactional
    @Modifying
    @Query(value="DELETE FROM friend_table ft WHERE (ft.user1_email = :user1 AND user2_email =:user2)", nativeQuery = true)
    void removeBlock(@Param("user1") String user1, @Param("user2") String user2);
    
}
