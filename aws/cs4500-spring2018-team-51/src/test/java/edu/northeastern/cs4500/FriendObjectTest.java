package edu.northeastern.cs4500;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs4500.objects.FriendObject;

/**
 * The class FriendObjectTest was created to test the methods and constructors of the class FriendObject.
 * @author emilytrinh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendObjectTest {
	
	/**
	 * Local variables that will be referenced throughout the test class. 
	 */
	FriendObject pair = new FriendObject("trinh.e@husky.neu.edu", "wu.tin@husky.neu.edu", "friend", "wu.tin@husky.neu.edu");
	FriendObject notpair = new FriendObject("seraphin.ma@husky.neu.edu", "anderson.i@husky.neu.edu", "not_friend", "seraphin.ma@husky.neu.edu");
	
	
	/**
	 * To test the default constructor (empty constructor) of a FriendObject
	 */
	@Test 
	public void defaultConstructorTest() {
		FriendObject obj = new FriendObject();
		assertEquals(true, obj instanceof FriendObject);
	}
	
	/**
	 * To test the method getUser1Email
	 */
	@Test
	public void getUser1EmailTest() {
		assertEquals("trinh.e@husky.neu.edu", pair.getUser1Email());
		assertEquals("seraphin.ma@husky.neu.edu", notpair.getUser1Email());
	}
	
	/**
	 * To test the method getUser2Email
	 */
	@Test
	public void getUser2EmailTest() {
		assertEquals("wu.tin@husky.neu.edu", pair.getUser2Email());
		assertEquals("anderson.i@husky.neu.edu", notpair.getUser2Email());
	}
	
	/**
	 * To test the method getStatus
	 */
	@Test
	public void getStatusTest() {
		assertEquals("Friend", pair.getStatus());
		assertEquals("Not_Friend", notpair.getStatus());
	}
	
	/**
	 * To test the method getActionUser
	 */
	@Test
	public void getActionUserTest() {
		assertEquals("wu.tin@husky.neu.edu", pair.getActionUser());
		assertEquals("seraphin.ma@husky.neu.edu", notpair.getActionUser());
	}
	
	/**
	 * To test the method setUser1Email, to check if user1's email is properly updated.
	 */
	@Test
	public void setUser1EmailTest() {
		assertEquals("trinh.e@husky.neu.edu", pair.getUser1Email());
		pair.setUser1Email("emily88trinh@ccs.neu.edu");
		assertEquals("emily88trinh@ccs.neu.edu", pair.getUser1Email());
		pair.setUser1Email("trinh.e@husky.neu.edu");
		assertEquals("trinh.e@husky.neu.edu", pair.getUser1Email());
	}
	
	/**
	 * To test the method setUser2Email, to check if user2's email is properly updated.
	 */
	@Test
	public void setUser2EmailTest() {
		assertEquals("anderson.i@husky.neu.edu", notpair.getUser2Email());
		notpair.setUser2Email("iananderson@ccs.neu.edu");
		assertEquals("iananderson@ccs.neu.edu", notpair.getUser2Email());
		notpair.setUser2Email("anderson.i@husky.neu.edu");
		assertEquals("anderson.i@husky.neu.edu", notpair.getUser2Email());
	}
	
	/**
	 * To test the method setStatus, to check if the status of the friendobject is properly updated.
	 */
	@Test
	public void setStatusTest() {
		assertEquals("Friend", pair.getStatus());
		pair.setStatus("not_friend");
		assertEquals("Not_Friend", pair.getStatus());
		pair.setStatus("friend");
		assertEquals("Friend", pair.getStatus());
	}
	
	/**
	 * To test the method setActionUser, to check if the action user is properly updated.
	 */
	@Test
	public void setActionUserTest() {
		assertEquals("seraphin.ma@husky.neu.edu", notpair.getActionUser());
		notpair.setActionUser("anderson.i@husky.neu.edu");
		assertEquals("anderson.i@husky.neu.edu", notpair.getActionUser());
		notpair.setActionUser("seraphin.ma@husky.neu.edu");
		assertEquals("seraphin.ma@husky.neu.edu", notpair.getActionUser());
	}
	

}
