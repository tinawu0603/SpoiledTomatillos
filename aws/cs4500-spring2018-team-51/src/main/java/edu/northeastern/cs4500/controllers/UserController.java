package edu.northeastern.cs4500.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import edu.northeastern.cs4500.log4j2.Log;
import edu.northeastern.cs4500.objects.EndUser;
import edu.northeastern.cs4500.objects.FriendObject;
import edu.northeastern.cs4500.objects.LoginObject;
import edu.northeastern.cs4500.objects.Notification;
import edu.northeastern.cs4500.objects.SpoiledTomatillosException;
import edu.northeastern.cs4500.repository.EndUserRepository;
import edu.northeastern.cs4500.repository.FriendRepository;
import edu.northeastern.cs4500.repository.NotificationRepository;

/**
 * The UserController class is a RestController that takes in arguments from the
 * front end and retrieves, inserts, or updates the database.
 * The repositories the UserController class works with is the EndUserRepository,
 * the FriendRepository, and the NotificationRepository.
 * 
 *
 * @author  Patrick Milne
 * @version 1.0
 * @since   2018-04-13 
 */
@RestController
@CrossOrigin
public class UserController {


	private static UserController controller = null;

	/**
	 * Singleton pattern for instantiating UserController. Makes it so there can only be one
	 * instance of a UserController.
	 * @return the instance of UserController if there is already an instance created or
	 * a new UserController if no instance has been created.
	 */
	public static UserController getInstance() {
		if (controller == null) {
			controller = new UserController();
		}
		return controller; 
	}

	/**
	 * Constructor for a UserController that can only be called by getInstance()
	 * Part of the singleton pattern.
	 */
	private UserController() { 
	}

	@Autowired
	EndUserRepository endUserRepo;

	@Autowired
	FriendRepository friendRepo;

	@Autowired
	NotificationRepository notificationRepo;

	/**
	 * Local variables that will be used throughout the class
	 */

	String emailError = "Email and Password do not match";
	String userSpace = "User ";
	String givenEmail = "Given email ";
	String closingConnection = "Closing connection failed.";
	String dne = " does not exist.";

	/**
	 * This function allows a user to delete his or her account. The user can only delete his or her account after passing in their
	 * email and password. If the email and password don't match, it does not delete their account.
	 * @param lo is a LoginObject that contains the email and password of the user.
	 * @return boolean This returns true if the user was deleted, false otherwise.
	 */
	@RequestMapping(value = "/api/delete", method = RequestMethod.POST)
	@ResponseBody

	public EndUser delete(@RequestBody LoginObject lo) throws SpoiledTomatillosException {

		try {
			EndUser user = endUserRepo.checkUserPass(lo.getEmail(), lo.getPassword());
			endUserRepo.delete(user);
			Log.reportEvent(user.getEmail() + "deleted their account");
			return user;
		}
		catch (NullPointerException e) {
			Log.reportError(emailError);
			throw new SpoiledTomatillosException(emailError);

		}
	}


	/**
	 * This function returns all the users in our database. It can only be accessed by an admin user.
	 * @param email is the email of the user who is trying to access all the users.
	 * @return List<EndUser> This returns a list of all the EndUsers.
	 * @throws SpoiledTomatillosException 
	 */
	@RequestMapping(value = "/api/allusers/{email}")
	@ResponseBody
	public List<EndUser> findAllUsers(@PathVariable(name="email") String email) throws SpoiledTomatillosException {
		EndUser u = this.findEmail(email);
		if (u != null && endUserRepo.isAuthorized(email)) {
			return this.endUserRepo.findAll();
		}
		else {
			Log.reportError(userSpace + email + " is not authorized to perform this action.");
			throw new SpoiledTomatillosException("User is not authorized to perform this action.");
		}
	}


	/**
	 * This function returns if a user is an authorized admin.
	 * @param email is the email of the user who is being checked if they have authorization.
	 * @return boolean This returns true if the user is authorized, false otherwise.
	 */
	@RequestMapping(value = "/api/userauthorized/{email}")
	@ResponseBody
	public boolean userAuthorization(@PathVariable(name="email") String email) {
		EndUser u = this.findEmail(email);
		if (u == null) {
			return false;
		}
		else {
			return endUserRepo.isAuthorized(email);
		}
	}

	/**
	 * This function deletes a user from our database. This can only be done by a user who is an admin.
	 * @param admin is the email of the admin user.
	 * @param user is the email of the user who is getting delete
	 * @return boolean This returns true if the user was deleted, false otherwise.
	 */
	@RequestMapping(value = "/api/deleteuser/{admin}/{user}")
	@ResponseBody
	public void deleteUser(@PathVariable(name="admin") String admin, @PathVariable(name="user") String user) {
		if (this.userAuthorization(admin)) {
			endUserRepo.deleteUser(user);
			Log.reportEvent("Deleted user " + user + " from the database");
		}
	}


	/**
	 * This function allows a person to create an account and adds it to the database.
	 * @param u is an EndUser object that contains the person's first name, last name, email, and password.
	 * @return EndUser This returns the EndUser object that was added to our database.
	 * @throws SpoiledTomatillosException 
	 */
	@RequestMapping(value = "/api/user/insert", method = RequestMethod.POST)
	public EndUser insertEndUser(@RequestBody EndUser u) throws SpoiledTomatillosException {
		if (!u.getEmail().contains(".") || !u.getEmail().contains("@")) {
			Log.reportError(givenEmail + u.getEmail() + " is not in valid email form.");
			throw new SpoiledTomatillosException(givenEmail + u.getEmail() + " is not in valid email form.");
		}
		if (this.findEmail(u.getEmail()) != null) {
			Log.reportError(givenEmail + u.getEmail() + " already has an account.");
			throw new SpoiledTomatillosException(givenEmail + u.getEmail() + " already has an account.");

		}
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://cs4500-spring2018-team51.cu4dur2ssaow.us-east-2.rds.amazonaws.com/cs4500_spring2018_team51",
					"team51", "whitehallbesthall");
			String sql = "INSERT INTO user (authorized, email, first_name, last_name, password, logged_in)"
					+ "VALUES (FALSE, ?, ?, ?, ?, TRUE)";
			statement = connection.prepareStatement(sql);

			statement.setString(1, u.getEmail());
			statement.setString(2, u.getFirstName());
			statement.setString(3, u.getLastName());
			statement.setString(4, u.getPassword());
			statement.executeUpdate();
			return this.findEmail(u.getEmail());


		} catch (SQLException e) {
			Log.reportError("Error adding new user. Please ensure email is not used for another account.");

		}
		finally {

			if(statement !=  null) {
				try {
					statement.close();
				}
				catch (SQLException e) {
					Log.reportError(closingConnection);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} 
				catch (SQLException e) {

					Log.reportError(closingConnection);

				}
			}
		}
		Log.reportEvent("New user " + u.getEmail() + " has been created.");
		return null;
	}

	/**
	 * This function searches our database of users by first name.
	 * @param firstName is the first name of the user being searched for.
	 * @return List<EndUser> returns a list of all the users with a similar first name to the parameter firstName.
	 */
	@RequestMapping(value = "/api/searchFirst/{firstName}")
	@ResponseBody
	public List<EndUser> searchByFirstName(@PathVariable(name="firstName") String firstName) {

		String firstN = "%" + firstName + "%";
		List<EndUser> users = endUserRepo.findByFirstName(firstN);
		Log.reportEvent("User has searched for another user, " + firstN + " by first name.");

		return users;
	}

	/**
	 * This function searched our database of users by last name.
	 * @param lastName is the last name of the user being searched for.
	 * @return List<EndUser> This returns a list of all the users with a similar last name to the parameter lastName.
	 */
	@RequestMapping(value = "/api/searchLast/{lastName}")
	@ResponseBody
	public List<EndUser> searchByLastName(@PathVariable(name="lastName")String lastName) {

		String lastN = "%" + lastName + "%";
		List<EndUser> users = endUserRepo.findBylastName(lastN);
		Log.reportEvent("User has searched for another user " + lastN + " by last name.");

		return users;
	}


	/**
	 * This function searches our database of users by full name.
	 * @param fullName is the full name of the user being searched for.
	 * @return List<EndUser> This returns a list of all the users with a similar full name to the parameter fullName.
	 */
	@RequestMapping(value = "/api/searchFull/{fullName}")
	@ResponseBody
	public List<EndUser> searchByFullName(@PathVariable(name="fullName")String fullName) {
		if (!fullName.contains(" ")) {
			return Collections.emptyList();
		}
		int space = fullName.indexOf(' ');
		String firstName = fullName.substring(0, space);
		String lastName = fullName.substring(space+1);
		firstName = "%" + firstName + "%";
		lastName = "%" + lastName + "%";
		List<EndUser> users = endUserRepo.findByfullName(firstName, lastName);
		Log.reportEvent("User has searched for another user, " + fullName +" by full name.");
		return users;
	}

	/**
	 * This function finds all EndUsers that have are similar to the input msg
	 * @param msg is the search string from the users
	 * @return HashSet<EndUser> This returns a set of all the users by using the parameter to search by first name,
	 * last name, and full name.
	 */
	@RequestMapping(value = "/api/searchUser/{msg}")
	@ResponseBody
	public Set<EndUser> searchUser(@PathVariable(name ="msg") String msg) {
		HashSet<EndUser> result = new HashSet<>();

		String temp = "%" + msg + "%";
		result.addAll(endUserRepo.search(temp));

		if (this.searchByFullName(temp) != null) {
			result.addAll(this.searchByFullName(temp));

		}

		return result;
	}

	/**
	 * This function searches our database of users by id.
	 * @param id is the id of the user being searched for.
	 * @return EndUser This returns the EndUser with the same id as the parameter id.
	 */
	@RequestMapping(value = "/api/findid/{id}")
	@ResponseBody
	public EndUser findByID(@PathVariable(value="id")int id) {
		EndUser user = endUserRepo.findOne(id);
		Log.reportEvent(userSpace + id + " has been searched.");
		return user;
	}

	/**
	 * This function searches our database of users by email.
	 * @param email is the email of the user being searched for.
	 * @return EndUser returns the EndUser with the same email as the parameter email.
	 */
	@RequestMapping(value = "/api/findEmail/{email}")
	@ResponseBody
	public EndUser findEmail(@PathVariable(name="email")String email) {
		EndUser user = endUserRepo.findByEmail(email);
		Log.reportEvent("User has searched for another user " + email + " by email.");
		return user;
	}



	/**
	 * This function logs a user into their account if their email and password match what is stored in our database
	 * and they are not already logged in.
	 * @param lo a LoginObject containing the email and password the user has entered.
	 * @return EndUser This returns user if the email and password match the database. 
	 * @throws SpoiledTomatillosException 
	 */
	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	@ResponseBody
	public EndUser login(@RequestBody LoginObject lo) {
		try {
			EndUser user = endUserRepo.checkUserPass(lo.getEmail(), lo.getPassword());
			endUserRepo.login(user.getEmail());
			return endUserRepo.findByEmail(lo.getEmail());
		}
		catch (NullPointerException e) {
			throw new NullPointerException(emailError);
		}
	}

	/**
	 * This function logs a user out of their account if they are not already logged out.
	 * @param email is the email of the user trying to logout.
	 * @return EndUser This returns user if the user was successfully logged out.
	 */
	@RequestMapping(value = "/api/logout/{email}")
	@ResponseBody
	public EndUser logout(@PathVariable(name="email")String email) throws SpoiledTomatillosException {
		EndUser user = endUserRepo.findByEmail(email);
		if (!endUserRepo.loginStatus(user.getEmail())) {
			Log.reportError("User, " + email + " has already logged out.");
			throw new SpoiledTomatillosException("User, " + email + " has already logged out.");
		}
		else {
			endUserRepo.logout(user.getEmail());
			Log.reportError(userSpace + email + " has logged out");
			return endUserRepo.findByEmail(email);
		}

	}


	/**
	 * This function allows a user to change their email address. Throws an error if the oldEmail does not match an email
	 * in our database or the new email is already associated with an account.
	 * @param oldEmail is the old email of the user.
	 * @param newEmail is the new email the user is trying to change to.
	 * @return String This returns the new email if the email was successfully changed.
	 */
	@RequestMapping(value = "/api/updateEmail/{oldEmail}/{newEmail}")
	@ResponseBody
	public String updateEmail(@PathVariable(name="oldEmail")String oldEmail, @PathVariable(name="newEmail")String newEmail) throws SpoiledTomatillosException {
		if (endUserRepo.findByEmail(oldEmail) == null) {
			Log.reportError("User account does not exist.");
			throw new SpoiledTomatillosException("Account does not exist");
		}
		if (endUserRepo.findByEmail(newEmail) != null) {
			Log.reportError("New email already has an account.");
			throw new SpoiledTomatillosException("New email already has an account.");
		}
		else {
			endUserRepo.updateEmail(oldEmail, newEmail);
			Log.reportEvent("Email for user " + oldEmail + " has been updated to " + newEmail);
			return newEmail;
		}
	}

	/**
	 * This function finds all the friends of a user by using the FriendRepository class.
	 * @param email is the email of the user.
	 * @return List<EndUser> This returns all the friends of the user associated with the parameter email. 
	 */
	@RequestMapping(value = "/api/findFriends/{email}")
	@ResponseBody
	public List<EndUser> getFriends(@PathVariable(name="email")String email) throws SpoiledTomatillosException {
		if (endUserRepo.findByEmail(email) == null) {
			Log.reportError("Account does not exist.");
			throw new SpoiledTomatillosException("Account does not exist");
		}
		else {
			Log.reportEvent("Found friends for user " + email);
			List<String> emails = friendRepo.findFriends(email);
			List<EndUser> result = new ArrayList<>();
			for (int i = 0; i < emails.size(); i++) {
				result.add(this.findEmail(emails.get(i)));
			}
			return result;
		}
	}


	/**
	 * This function sends a friend request from one user to another. The receiver will get a notification added in the Notification_Table.
	 * A new friendship relationship will also be added to the Friend_Table with a 'Pending' status. An error is thrown if one of the users
	 * does not exist or a relationship between the two members already exists.
	 * @param user1_email is the email of the user sending the friend request.
	 * @param user2_email is the email of the user receiving the friend request.
	 * @return This does not return anything.
	 */
	@CrossOrigin
	@RequestMapping(value = "/api/addfriend/{user1_email}/{user2_email}")
	@ResponseBody
	public void addFriend(@PathVariable(name="user1_email") String user1email, @PathVariable(name="user2_email") String user2email) throws SpoiledTomatillosException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://cs4500-spring2018-team51.cu4dur2ssaow.us-east-2.rds.amazonaws.com/cs4500_spring2018_team51",
					"team51", "whitehallbesthall");
			if (this.findEmail(user1email) == null || this.findEmail(user2email) == null) {
				Log.reportError("One of the users does not exist.");
				throw new SpoiledTomatillosException("One of the users does not exist.");
			}

			if (friendRepo.findFriendship(user1email, user2email) != null) {
				Log.reportError("Users have already submitted a friend request.");
				throw new SpoiledTomatillosException("Users have already submitted a friend request or the user is blocked.");
			}
			FriendObject fo = new FriendObject(user1email, user2email, "Pending", user1email);
			String sql = "INSERT INTO friend_table(user1_email, user2_email, status, action_user) VALUES(?, ?, 'Pending', ?)";
			statement = connection.prepareStatement(sql);
			statement.setString(1, fo.getUser1Email());
			statement.setString(2, fo.getUser2Email());
			statement.setString(3, fo.getActionUser());
			statement.executeUpdate();
			EndUser u = this.findEmail(user1email);
			Notification no = new Notification(user2email, user1email, "You received a friend request from " + 
					u.getFirstName() + " " + u.getLastName());
			notificationRepo.save(no);
			Log.reportEvent("Friend request has been sent from " + user1email + " to " + user2email);
		} catch (SQLException e) {
			Log.reportError("SQL Exception when trying to add a friend.");

		}
		finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					Log.reportError("Closing connection failed.");
				}
			}
			if(statement !=  null) {
				try {
					statement.close();
				}
				catch (SQLException e) {
					Log.reportError(closingConnection);
				}
			}
		}

	}

	/**
	 * This function allows a user to confirm a friend request. An error is thrown if one of the users
	 * does not exist or a relationship between the two members does not exist.
	 * @param user1_email is the email of the user confirming the friend request.
	 * @param user2_email is the email of the user who sent the friend request.
	 * @return FriendObject This returns the friendship relationship if the friend request was successfully
	 * confirmed.
	 */
	@RequestMapping(value = "/api/confirmfriend/{user1_email}/{user2_email}")
	@ResponseBody
	public FriendObject confirmFriend(@PathVariable(name="user1_email") String user1email, @PathVariable(name="user2_email") String user2email) 
			throws SpoiledTomatillosException {
		if (endUserRepo.findByEmail(user1email) == null || endUserRepo.findByEmail(user2email) == null) {
			Log.reportError("Account " + user1email + " or " + user2email +  dne);
			throw new SpoiledTomatillosException("Account " + user1email + " or " + user2email +  dne);
		}
		if (friendRepo.findFriendship(user1email, user2email) == null) {
			Log.reportError("Friendship for " + user1email + " and " + user2email + dne);
			throw new SpoiledTomatillosException("Friendship for " + user1email + " and " + user2email + dne);
		}
		friendRepo.confirmFriend(user1email, user2email);

		EndUser u = this.findEmail(user1email);
		Notification no = new Notification(user2email, user1email, u.getFirstName() + " " + u.getLastName() + " confirmed your friend request");
		notificationRepo.save(no);

		Log.reportEvent("Friend request for " + user1email + " has been confirmed by " + user2email);
		return friendRepo.findFriendship(user1email, user2email);
	}

	/**
	 * This function finds all the friend requests a user has sent but have not been confirmed.
	 * @param user is the email of the user.
	 * @return List<FriendObject> This returns all the friendship relationships of the user associated with the parameter user
	 * that are still 'Pending'. 
	 */
	@RequestMapping(value = "/api/getSent/{user}")
	@ResponseBody
	public List<FriendObject> getSent(@PathVariable(name = "user") String user) {
		return friendRepo.getSent(user);
	}

	/**
	 * This function finds all the friend requests a user has received but has not confirmed.
	 * @param user is the email of the user.
	 * @return List<FriendObject> This returns all the friendship relationships of the user associated with the parameter user
	 * that the user has not confirmed. 
	 */
	@RequestMapping(value = "/api/getReceived/{user}")
	@ResponseBody
	public List<FriendObject> getReceived(@PathVariable(name = "user") String user) {
		return friendRepo.getReceived(user);
	}



	/**
	 * This function allows an admin user to make another user an admin. If the user is not an admin user, they can not
	 * make another user an admin.
	 * @param admin is the email of the admin user.
	 * @param newAdmin is the email of the user who is going to become an admin user.
	 * @return EndUser This returns the user who has been turned into an admin. 
	 */
	@RequestMapping(value = "/api/makeAdmin/{admin}/{newAdmin}")
	@ResponseBody
	public EndUser makeAdmin(@PathVariable(name ="admin") String admin, @PathVariable(name = "newAdmin") String newAdmin) throws SpoiledTomatillosException{
		EndUser u = this.findEmail(admin);

		if (u != null && endUserRepo.isAuthorized(admin)) {
			endUserRepo.makeAdmin(newAdmin);
			Log.reportEvent(admin + " authorized " + newAdmin + " admin privledges.");
			return this.findEmail(newAdmin);
		}
		else {
			Log.reportError(admin + " does not have authorization to make " + newAdmin + " an admin");
			throw new SpoiledTomatillosException(admin + " does not have authorization to make " + newAdmin + " an admin");
		}
	}

}
