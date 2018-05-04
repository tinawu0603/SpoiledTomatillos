package edu.northeastern.cs4500.repository; 

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import edu.northeastern.cs4500.objects.EndUser;


/**
* The EndUserRepository interface is an interface that extends JpaRepository. It manages creating, reading, updating,
* and deleting things from the the user table.
* 
*
* @author  Patrick Milne
* @version 1.0
* @since   2018-04-13 
*/
public interface EndUserRepository extends JpaRepository<EndUser, Integer>{
	  	
	 	/**
	 	 * This function allows a user be deleted from the user table.
	 	 * @param EndUser is the end user that is going to be deleted.
	 	 * @return This does not return anything.
	 	 */
		void delete(EndUser deleted);
	  
		 /**
		   * This function finds all the users in the user table.
		   * @return List<EndUser> This returns a list of all the users in the user table.
		   */
		List<EndUser> findAll();
	 
		 /**
		   * This function find a user based on their id.
		   * @param id is the id associated with the user.
		   * @return EndUser This return the user associated with the id.
		   */
	    EndUser findOne(int id);
	 
	    /**
		   * This function saves a user in the user table.
		   * @param EndUser is the end user that is going to be saved.
		   * @return EndUser This returns the EndUser that was added to the database.
		   */
	    EndUser save(EndUser persisted);
	    
	    /**
		   * This function finds all the users with similar first names as the parameter firstName.
		   * @param firstName is the first name being searched for.
		   * @return List<EndUser> This returns a list of all the users with similar first names.
		   */
	    @Query(value="SELECT * FROM user u WHERE u.first_name LIKE :firstName", nativeQuery = true)
		List<EndUser> findByFirstName(@Param("firstName") String firstName);
	    
	    /**
		   * This function finds all the users with similar last names as the parameter lastName.
		   * @param lastName is the first name being searched for.
		   * @return List<EndUser> This returns a list of all the users with similar last names.
		   */
	    @Query(value="SELECT * FROM user u WHERE u.last_name LIKE :lastName", nativeQuery = true)
		List<EndUser> findBylastName(@Param("lastName") String lastName);
	    
	    /**
		   * This function finds all the users with similar first names and last names as the parameter firstName and lastName.
		   * @param firstName is the first name being searched for.
		   * @param lastName is the last name being searched for.
		   * @return List<EndUser> This returns a list of all the users with similar first and last names.
		   */
	    @Query(value="SELECT * FROM user u WHERE u.first_name LIKE :firstName AND u.last_name LIKE :lastName", nativeQuery = true)
		List<EndUser> findByfullName(@Param("firstName") String firstName, @Param("lastName") String lastName);
	    
	    /**
		   * This function finds all the users with similar first names or last names as the parameter msg.
		   * @param msg is the String being searched for.
		   * @return List<EndUser> This returns a list of all the users with similar first or last names.
		   */
	    @Query(value = "SELECT * FROM user u WHERE u.first_name LIKE :msg OR u.last_name LIKE :msg", nativeQuery = true)
	    List<EndUser> search(@Param("msg") String msg);
	    
	    /**
	     	* This function finds the user with the same email as the parameter email.
			* @param email is the email being searched for.
			* @return EndUser This returns the user with the same email.
			*/
	    @Query(value="SELECT * FROM user u WHERE u.email = :email", nativeQuery = true)
		EndUser findByEmail(@Param("email") String email);
	    
	    /**
		   * This function checks if the email and password match the email and password in the database.
		   * @param email is the email associated with the account.
		   * @param password is the password associated with the email
		   * @return EndUser This returns the EndUser associated with the email and password if the email and password match,
		   * otherwise it returns null.
		   */
	    @Query(value="SELECT * FROM user u WHERE u.email = :email AND u.password = :password", nativeQuery = true)
		EndUser checkUserPass(@Param("email") String email, @Param("password") String password);
	    
	    
	    /**
		   * This function updates the user table so the user associated with parameter email is logged in.
		   * @param email is the email of the account trying to be logged in.
		   * @return This does not return anything.
		   */
	    @Transactional
	    @Modifying
	    @Query(value="UPDATE user u SET u.logged_in = TRUE WHERE u.email = :email", nativeQuery = true)
		void login(@Param("email") String email);
	    
	    
	    /**
		   * This function updates the user table so the user associated with parameter email is logged out.
		   * @param email is the email of the account trying to be logged out.
		   * @return This does not return anything.
		   */
	    @Transactional
	    @Modifying
	    @Query(value="UPDATE user u SET u.logged_in = FALSE WHERE u.email = :email", nativeQuery = true)
		void logout(@Param("email") String email);
	    
	    /**
		   * This function finds the login status of the user associated with the email.
		   * @param email is the email of the account.
		   * @return boolean This returns true if the user is logged in, false if the user is logged out.
		   */
	    @Query(value="SELECT logged_in l FROM user u WHERE u.email = :email", nativeQuery = true)
		boolean loginStatus(@Param("email") String email);
	    
	    
	    /**
		   * This function updates the user associated with the parameter oldEmail and sets the user's email to the parameter newEmail.
		   * @param oldEmail is the email of the account trying to change their email.
		   * @param newEmail is the new email that the user wants to change their email to.
		   * @return This does not return anything.
		   */
	    @Transactional
	    @Modifying
	    @Query(value="UPDATE user u SET u.email = :newEmail WHERE u.email = :oldEmail", nativeQuery = true)
	    void updateEmail(@Param("oldEmail") String oldEmail, @Param("newEmail") String newEmail);
	    
	    
	    /**
	     	* This function determines if the user associated with the parameter email is an authorized admin.
	     	* @param email is the email of the account.
	     	* @return boolean This returns true if the user is an authorized admin, false if the user is not an authorized admin.
	     	*/
	    @Query(value="SELECT authorized FROM user u WHERE u.email = :email", nativeQuery = true)
	    boolean isAuthorized(@Param("email") String email);
	    
	    /**
		   * This function deletes the user associated with the email.
		   * @param email is the email of the account that will be deleted.
		   * @return This does not return anything.
		   */
	    @Transactional
	    @Modifying
	    @Query(value="DELETE FROM user WHERE email = :email", nativeQuery = true)
	    void deleteUser(@Param("email") String email);
	    
	    /**
		   * This function updates the user associated with the parameter email and sets their authorization field to true, making the user an admin.
		   * @param email is the email of the account being turned into an admin.
		   * @return This does not return anything.
		   */
	    @Transactional
	    @Modifying
	    @Query(value="UPDATE user u SET u.authorized = true WHERE u.email = :email", nativeQuery = true)
	    void makeAdmin(@Param("email") String email);
}


