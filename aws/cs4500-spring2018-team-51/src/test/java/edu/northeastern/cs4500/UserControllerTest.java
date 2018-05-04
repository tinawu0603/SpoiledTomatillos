package edu.northeastern.cs4500;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.util.NestedServletException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.northeastern.cs4500.controllers.UserController;
import edu.northeastern.cs4500.objects.EndUser;
import edu.northeastern.cs4500.objects.FriendObject;
import edu.northeastern.cs4500.objects.Friendship;
import edu.northeastern.cs4500.objects.LoginObject;
import edu.northeastern.cs4500.objects.Notification;
import edu.northeastern.cs4500.objects.Status;
import edu.northeastern.cs4500.repository.EndUserRepository;
import edu.northeastern.cs4500.repository.FriendRepository;
import edu.northeastern.cs4500.repository.NotificationRepository;

/**
 * This class UserControllerTest was created to test the methods of the class UserController
 * @author emilytrinh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@CrossOrigin
public class UserControllerTest {

	/**
	 * Local variables that will be referenced throughout the test class. 
	 */
	private MockMvc mvc;
	private ObjectMapper objectMapper;

	@Mock
	private EndUserRepository endUserRepo;

	@Mock
	private FriendRepository friendRepo;

	@Mock
	private NotificationRepository notificationRepo;

	@InjectMocks
	private UserController control; //= UserController.getInstance();

	private EndUser user1;
	private EndUser user2;
	private EndUser admin1;
	private EndUser admin2;
	private Friendship friendship;
	private FriendObject friendObject;
	private Notification notificaiton;
	private Notification notification2;

	@Before
	public void init() {
		this.mvc = MockMvcBuilders.standaloneSetup(this.control).build();
		this.user1 = new EndUser(1, "Emily", "Trinh", "et@neu.edu", "password", false);
		this.user2 = new EndUser(4, "Barack", "Obama", "michelle2020@gmail.com", "password", false);
		this.admin1 = new EndUser(2, "Admin", "Admin", "ms@ms.com", "crocodile", true);
		this.admin2 = new EndUser(1, "Emily", "Trinh", "et@neu.edu", "password", true);
		this.friendship = new Friendship("et@neu.edu", "ms@ms.com");
		this.friendObject = new FriendObject("et@neu.edu", "ms@ms.com", "friend", "et@neu.edu");
		this.notificaiton = new Notification("ms@ms.com", "et@neu.edu", "yay friends!!!!");
		this.notification2 = new Notification("michelle2020@gmail.com", "et@neu.edu", "You received a friend request from Emily Trinh");
		this.objectMapper = new ObjectMapper();
	}

	/**
	 * To test the method findAllUsers
	 * @throws Exception
	 */
	@Test
	public void findAllUsersTest() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(this.user1);
		users.add(this.admin1);
		when(this.endUserRepo.findByEmail("ms@ms.com")).thenReturn(admin1);
		when(this.endUserRepo.isAuthorized("ms@ms.com")).thenReturn(true);
		when(this.endUserRepo.findAll()).thenReturn(users);
		this.mvc.perform(get("/api/allusers/ms@ms.com/"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(users)));
	}

	/**
	 * To test the method userAuthorization, should return true for an admin
	 * @throws Exception
	 */
	@Test
	public void userAuthorizationTest1() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(this.user1);
		users.add(this.admin1);
		when(this.endUserRepo.findByEmail("ms@ms.com")).thenReturn(admin1);
		when(this.endUserRepo.isAuthorized("ms@ms.com")).thenReturn(true);
		this.mvc.perform(get("/api/userauthorized/ms@ms.com/"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("true")));
	}

	/**
	 * To test the method userAuthorization returns false for a reg. end user. 
	 * @throws Exception
	 */
	@Test
	public void userAuthorizationTest2() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(this.user1);
		users.add(this.admin1);
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(user1);
		when(this.endUserRepo.isAuthorized("et@neu.edu")).thenReturn(false);
		this.mvc.perform(get("/api/userauthorized/et@neu.edu/"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("false")));
	}

	/**
	 * To test the method searchByFirstName
	 * @throws Exception
	 */
	@Test
	public void searchByFirstNameTest() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(this.user1);
		users.add(this.admin1);
		when(this.endUserRepo.findByFirstName("%Emily%")).thenReturn(users);
		this.mvc.perform(get("/api/searchFirst/Emily"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(users)));
	}

	/**
	 * To test the method searchByLastName
	 * @throws Exception
	 */
	@Test
	public void searchByLastNameTest() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(this.user1);
		users.add(this.admin1);
		when(this.endUserRepo.findBylastName("%Trinh%")).thenReturn(users);
		this.mvc.perform(get("/api/searchLast/Trinh"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(users)));
	}

	/**
	 * To test the method searchByFullName
	 * @throws Exception
	 */
	@Test
	public void searchByFullNameTest() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(this.user1);
		users.add(this.admin1);
		when(this.endUserRepo.findByfullName("%Emily%", "%Trinh%")).thenReturn(users);
		this.mvc.perform(get("/api/searchFull/Emily Trinh"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(users)));
	}

	/**
	 * To test the method findByID
	 * @throws Exception
	 */
	@Test
	public void findByIDTest() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(this.user1);
		users.add(this.admin1);
		when(this.endUserRepo.findOne(1)).thenReturn(user1);
		this.mvc.perform(get("/api/findid/1"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(user1)));
	}

	/**
	 * To test the method findEmail
	 * @throws Exception
	 */
	@Test
	public void findEmailTest() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(this.user1);
		users.add(this.admin1);
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(user1);
		this.mvc.perform(get("/api/findEmail/et@neu.edu/"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(user1)));
	}

	/**
	 * To test the method deleteUser
	 * @throws Exception
	 */
	@Test
	public void deleteUserTest() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(this.user1);
		users.add(this.admin1);
		when(this.endUserRepo.findByEmail("ms@ms.com")).thenReturn(admin1);
		when(this.endUserRepo.isAuthorized("ms@ms.com")).thenReturn(true);
		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				users.remove(user1);
				return null;
			}
		}).when(endUserRepo).deleteUser("et@neu.edu");
		mvc.perform(get("/api/deleteuser/ms@ms.com/et@neu.edu/"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("")));
	}

	/**
	 * To test the method deleteUser
	 * @throws Exception
	 */
	@Test
	public void deleteTest() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(this.user1);
		users.add(this.admin1);
		LoginObject l = new LoginObject("e.trinh", "whitehall");
		EndUser emilytrinh = new EndUser(7, "Emily", "Trinh", "e.trinh", "whitehall", false);
		when(this.endUserRepo.checkUserPass("e.trinh", "whitehall")).thenReturn(emilytrinh);
		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				users.remove(user1);
				return null;
			}
		}).when(endUserRepo).deleteUser("et@neu.edu");
		mvc.perform(post("/api/delete")
				.content(this.objectMapper.writeValueAsString(l))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	//To test that the method deleteUser throws an exception if the user does not exist 
	@Test(expected = NestedServletException.class)
	public void deleteExceptionTest() throws Exception {
		LoginObject l = new LoginObject("et@neu.edu", "password");
		mvc.perform(post("/api/delete")
				.content(this.objectMapper.writeValueAsString(l))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method updateEmail
	 * @throws Exception
	 */
	@Test
	public void updateEmailTest() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(this.user1);
		users.add(this.admin1);
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(user1);
		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				user1.setEmail("dogs@dogs.com");
				return null;
			}
		}).when(endUserRepo).updateEmail("et@neu.edu", "dogs@dogs.com");
		mvc.perform(get("/api/updateEmail/et@neu.edu/dogs@dogs.com/"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("dogs@dogs.com")));
	}

	/**
	 * To test the method getFriends
	 * @throws Exception
	 */
	@Test
	public void getFriendsTest() throws Exception {
		List<String> friends = new ArrayList<String>();
		friends.add("ms@ms.com");
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(user1);
		when(this.friendRepo.findFriends("et@neu.edu")).thenReturn(friends);
		this.mvc.perform(get("/api/findFriends/et@neu.edu/"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("")));
	}

	/**
	 * To test the method confirmFriend
	 * @throws Exception
	 */
	@Test
	public void confirmFriendTest() throws Exception {
		List<String> friends = new ArrayList<String>();
		friends.add("ms@ms.com");
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(user1);
		when(this.endUserRepo.findByEmail("admin@admin.com")).thenReturn(admin1);
		when(this.friendRepo.findFriendship("et@neu.edu", "admin@admin.com")).thenReturn(friendObject);
		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				friendObject.setStatus("friend");
				return null;
			}
		}).when(friendRepo).confirmFriend("et@neu.edu", "admin@admin.com");
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(user1);
		when(this.notificationRepo.save(notificaiton)).thenReturn(null);
		when(this.friendRepo.findFriendship("et@neu.edu", "ms@ms.com")).thenReturn(friendObject);
		mvc.perform(get("/api/confirmfriend/et@neu.edu/admin@admin.com/"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Friend")));
	}

	/**
	 * To test the method getSent
	 * @throws Exception
	 */
	@Test
	public void getSentTest() throws Exception {
		List<FriendObject> friendObjects = new ArrayList<FriendObject>();
		friendObjects.add(friendObject);
		when(this.friendRepo.getSent("ms@ms.com")).thenReturn(friendObjects);
		this.mvc.perform(get("/api/getSent/ms@ms.com/"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(friendObjects)));
	}

	/**
	 * To test the method getReceived
	 * @throws Exception
	 */
	@Test
	public void getReceivedTest() throws Exception {
		List<FriendObject> friendObjects = new ArrayList<FriendObject>();
		friendObjects.add(friendObject);
		when(this.friendRepo.getReceived("ms@ms.com")).thenReturn(friendObjects);
		this.mvc.perform(get("/api/getReceived/ms@ms.com/"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(friendObjects)));
	}

	/**
	 * To test the method searchUser
	 * @throws Exception
	 */
	@Test
	public void searchUserTest() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(user1);
		users.add(admin1);
		when(this.endUserRepo.search("%Emily Trinh%")).thenReturn(users);
		this.mvc.perform(get("/api/searchUser/Emily Trinh"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(users)));
	}

	/**
	 * To test the method makeAdmin
	 * @throws Exception
	 */
	@Test
	public void makeAdminTest() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(user1);
		users.add(admin1);
		when(this.endUserRepo.findByEmail("ms@ms.com")).thenReturn(admin1);
		when(this.endUserRepo.isAuthorized("ms@ms.com")).thenReturn(true);
		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				user1.setAdmin();
				return null;
			}
		}).when(endUserRepo).makeAdmin("et@neu.edu");
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(admin2);
		mvc.perform(get("/api/makeAdmin/ms@ms.com/et@neu.edu/"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(admin2)));
	}

	/**
	 * To test the method findAllUsers throws an exception if the user does not exist
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void findAllUsersExceptionTest() throws Exception {
		mvc.perform(get("/api/allusers/et@neu.edu/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method searchUser throws an exception if the user does not exist
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void searchUserExceptionTest() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(admin1);
		when(this.endUserRepo.search("%Emily Trinh%")).thenReturn(null);
		this.mvc.perform(get("/api/searchUser/Emily Trinh"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method findAllUsers throws an exception if the user does not exist
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void findAllUsers4ExceptionTest() throws Exception {
		EndUser jokes = new EndUser(3, "jokes", "jokes", "jokes@jokes.com","password", false);
		when(this.endUserRepo.findByEmail("jokes@jokes.com")).thenReturn(null);
		when(this.endUserRepo.isAuthorized("jokes@jokes.com")).thenReturn(false);
		mvc.perform(get("/api/allusers/jokes@jokes/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method findAllUsers throws an exception if the user is already an admin
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void findAllUsers2ExceptionTest() throws Exception {
		mvc.perform(get("/api/allusers/ms@ms.com/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method udpateEmail does not work if the user performing hte action is not an admin
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void updateEmailExceptionTest() throws Exception {
		mvc.perform(get("/api/updateEmail/yourmom@gmail.com/lol@lol.com/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method updateEmail does not work if the newEmail aready exists
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void updateEmail2ExceptionTest() throws Exception {
		mvc.perform(get("/api/updateEmail/et@neu.edu/ms@ms.com/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method findFriends throws an exception if the endUser does not exist
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void getFriendsExceptionTest() throws Exception {
		mvc.perform(get("/api/findFriends/heehee@neu.edu/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method confirmFriend throws an exception if the user doesnt exist
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void confirmFriendExceptionTest() throws Exception {
		when(this.endUserRepo.findByEmail("heehee@neu.edu")).thenReturn(null);
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(user1);
		mvc.perform(get("/api/confirmfriend/heehee@neu.edu/et@neu.edu/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method confirmFriend throws an exception if the user doesnt exist
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void confirm2FriendExceptionTest() throws Exception {
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(user1);
		when(this.endUserRepo.findByEmail("heehee@neu.edu")).thenReturn(null);
		mvc.perform(get("/api/confirmfriend/et@neu.edu/heehee@neu.edu/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method confirmFriend throws an exception if the friendship doesnt exist. 
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void confirmFriendException2Test() throws Exception {
		mvc.perform(get("/api/confirmfriend/et@neu.edu/et@neu.edu/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method confirmFriend throws an exception if the user doesnt exist
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void confirmFriendException3Test() throws Exception {
		when(this.endUserRepo.findByEmail("heehee@neu.edu")).thenReturn(null);
		when(this.endUserRepo.findByEmail("jesus@jesus.com")).thenReturn(null);
		mvc.perform(get("/api/confirmfriend/heehee@neu.edu/jesus@jesus.com/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method confirmFriend throws and exception if the friendship already exists
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void confirmFriendExceptio42Test() throws Exception {
		mvc.perform(get("/api/confirmfriend/michelle2020@gmail.com/et@neu.edu/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method makeAdmin throws an exception if the person performing the action is not an admin
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void makeAdminExceptionTest() throws Exception {
		mvc.perform(get("/api/makeAdmin/michelle2020@gmail.com/et@neu.edu/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method getUserController
	 */
	@Test
	public void getUserControllerTest() {
		UserController m = UserController.getInstance();
		assertEquals(m, UserController.getInstance());
	}

	/**
	 * To test the method userAuthorized
	 * @throws Exception
	 */
	@Test
	public void userauthorizedNullTest() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(this.user1);
		users.add(this.admin1);
		when(this.endUserRepo.findByEmail("bleh@bleh.com")).thenReturn(null);
		when(this.endUserRepo.isAuthorized("bleh@bleh.com")).thenReturn(false);
		mvc.perform(get("/api/userauthorized/bleh@bleh.com/"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("false")));
	}

	/**
	 * To test the method serachByFullName
	 * @throws Exception
	 */
	@Test
	public void searchByFullNameNullTest() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(this.user1);
		users.add(this.admin1);
		when(this.endUserRepo.findByfullName("Emily", "Trinh")).thenReturn(null);
		this.mvc.perform(get("/api/searchFull/EmilyTrinh"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(containsString("")));
	}

	/**
	 * To test the method logout throws an exception if the user is already logged out
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void logoutExceptionTest() throws Exception {
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(user1);
		when(this.endUserRepo.loginStatus("et@neu.edu")).thenReturn(false);
		mvc.perform(get("/api/logout/et@neu.edu/"))
		.andExpect(status().isOk());
	}

	/**
	 * To test the method logout throws an exception if the user is already logged out
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void logoutException2Test() throws Exception {
		user1.logOut();
		mvc.perform(get("/api/logout/et@neu.edu/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test hte method updateEmail throws an exception if the newEmail is already the current email
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void updateEmail4ExceptionTest() throws Exception {
		mvc.perform(get("/api/updateEmail/et@neu.edu/et@neu.edu/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * Test throws an exception because the email already exists with another user. 
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void insertUserExceptionTest() throws Exception {
		EndUser dummyUser = new EndUser(99, "dog", "dog", "et@neu.edu", "dog", false);
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(user1);
		mvc.perform(post("/api/user/insert")
				.content(this.objectMapper.writeValueAsString(dummyUser))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}


	/**
	 * To test the method insertUser
	 * @throws Exception
	 */
	@CrossOrigin
	@Test
	public void insert2UserTest() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(this.user1);
		users.add(this.admin1);
		EndUser dummyUser = new EndUser(100, "dog", "dog", "lame@lame.com", "dog", false);
		when(this.endUserRepo.findByEmail("lame@lame.com")).thenReturn(null);
		mvc.perform(post("/api/user/insert")
				.content(this.objectMapper.writeValueAsString(dummyUser))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	/**
	 * To test the method updateEmail throws an exception if the newEmail already exists 
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void updateEmailException3Test() throws Exception {
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(user1);
		when(this.endUserRepo.findByEmail("ms@ms.com")).thenReturn(admin1);
		mvc.perform(get("/api/updateEmail/et@neu.edu/ms@ms.com/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method addFriend
	 * @throws Exception
	 */
	@CrossOrigin
	@Test
	public void addFriendTest() throws Exception {
		List<EndUser> users = new ArrayList<EndUser>();
		users.add(this.user1);
		users.add(this.admin1);
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(user1);
		when(this.endUserRepo.findByEmail("michelle2020@gmail.com")).thenReturn(user2);
		when(this.friendRepo.findFriendship("et@neu.edu", "michelle2020@gmail.com")).thenReturn(null);
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(user1);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(this.notificationRepo).save(notification2);
		this.mvc.perform(get("/api/addfriend/et@neu.edu/michelle2020@gmail.com/"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(containsString("")));
	}

	/**
	 * To test the method findByEmail throws an exception if the email does not exist
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void addFriendNull1Test() throws Exception {
		when(this.endUserRepo.findByEmail("weeeee@gmail.com")).thenReturn(null);
		when(this.endUserRepo.findByEmail("michelle2020@gmail.com")).thenReturn(user2);
		this.mvc.perform(get("/api/addfriend/weeeee@gmail.com/michelle2020@gmail.com/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method findByEmail throws an exception if the email does not exist
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void addFriendNull3Test() throws Exception {
		when(this.endUserRepo.findByEmail("michelle2020@gmail.com")).thenReturn(user2);
		when(this.endUserRepo.findByEmail("weeeee@gmail.com")).thenReturn(null);
		this.mvc.perform(get("/api/addfriend/michelle2020@gmail.com/weeeee@gmail.com/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method findByEmail throws an exception if the email does not exist
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void addFriend4Null1Test() throws Exception {
		when(this.endUserRepo.findByEmail("weeeee@gmail.com")).thenReturn(null);
		when(this.endUserRepo.findByEmail("lol@gmail.com.com")).thenReturn(null);
		this.mvc.perform(get("/api/addfriend/weeeee@gmail.com/lol@gmail.com/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method addFirned throws an exception if the friendship already exists
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void addFriendNull2Test() throws Exception {
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(user1);
		when(this.endUserRepo.findByEmail("ms@ms.com")).thenReturn(admin1);
		when(this.friendRepo.findFriendship("et@neu.edu", "ms@ms.com")).thenReturn(friendObject);
		this.mvc.perform(get("/api/addfriend/et@neu.edu/ms@ms.com/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method confirmFriend throws an exception if the friendship already exists
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void confirmedFriendTest() throws Exception {
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(user1);
		when(this.endUserRepo.findByEmail("michelle2020@gmail.com")).thenReturn(user2);
		when(this.friendRepo.findFriendship("et@neu.edu", "michelle2020@gmail.com")).thenReturn(null);
		mvc.perform(get("/api/confirmfriend/et@neu.edu/michelle2020@gmail.com/"))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method login works
	 * @throws Exception
	 */
	@Test
	public void loginTest() throws Exception {
		EndUser emilytrinh = new EndUser(7, "Emily", "Trinh", "e.trinh", "whitehall", false);
		when(this.endUserRepo.checkUserPass("e.trinh", "whitehall")).thenReturn(emilytrinh);
		when(this.endUserRepo.loginStatus("e.trinh")).thenReturn(false);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(this.endUserRepo).login("e.trinh");
		when(this.endUserRepo.findByEmail("e.trinh")).thenReturn(emilytrinh);
		LoginObject l = new LoginObject("e.trinh", "whitehall");
		mvc.perform(post("/api/login/")
				.content(this.objectMapper.writeValueAsString(l))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		when(this.endUserRepo.findByEmail("et@neu.edu")).thenReturn(user1);
		when(this.endUserRepo.loginStatus("et@neu.edu")).thenReturn(true);
		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				user1.logOut();
				return null;
			}
		}).when(endUserRepo).logout("et@neu.edu");
		mvc.perform(get("/api/logout/et@neu.edu/"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("false")));
	}

	/**
	 * To test the method login throws an exception if the user does not exist
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void loginException2Test() throws Exception {
		EndUser emilytrinh = new EndUser(7, "Emily", "Trinh", "e.trinh", "whitehall", false);
		when(this.endUserRepo.checkUserPass("e.trinh", "whitehall")).thenReturn(null);
		mvc.perform(post("/api/login/")
				.content(this.objectMapper.writeValueAsString(emilytrinh))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}

	/**
	 * Test throws an exception because email doesn't have a @
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void insertUserException2Test() throws Exception {
		EndUser dummyUser = new EndUser(99, "dog", "dog", "et", "dog", false);
		mvc.perform(post("/api/user/insert")
				.content(this.objectMapper.writeValueAsString(dummyUser))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}

	/**
	 * Test throws an exception because email doesn't have a @
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void insertUserException3Test() throws Exception {
		EndUser dummyUser = new EndUser(99, "dog", "dog", "et.", "dog", false);
		mvc.perform(post("/api/user/insert")
				.content(this.objectMapper.writeValueAsString(dummyUser))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}

	/**
	 * Test throws an exception because email doesn't have a @
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void insertUserException4Test() throws Exception {
		EndUser dummyUser = new EndUser(99, "dog", "dog", "et@", "dog", false);
		mvc.perform(post("/api/user/insert")
				.content(this.objectMapper.writeValueAsString(dummyUser))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
}
