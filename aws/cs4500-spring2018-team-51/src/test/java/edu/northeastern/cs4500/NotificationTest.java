package edu.northeastern.cs4500;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs4500.objects.Notification;


/**
 * THe class NoficiationTest was created to test the methods of class Notification
 * @author emilytrinh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationTest {
	
	/**
	 * Local variables that will be referenced throughout the test class. 
	 */
	Notification n1 = new Notification("receiver1", "sender1", "friend request");
	Notification n2 = new Notification("receiver2", "sender2", "recommended movie");
	Notification n3 = new Notification("receiver3", "sender3", "friend request confirmed");


	/**
	 * To test the method getId
	 */
	@Test
	public void testGetID() {
		assertEquals(0, n1.getId());
		assertEquals(0, n2.getId());
	}

	/**
	 * To test the method getReceiver
	 */
	@Test
	public void testGetReceiver() {
		assertEquals("receiver1", n1.getReceiver());
		assertEquals("receiver2", n2.getReceiver());
	}

	/**
	 * To test the method getSender
	 */
	@Test
	public void testGetSender() {
		assertEquals("sender1", n1.getSender());
		assertEquals("sender2", n2.getSender());
	}

	/**
	 * To test the method getMessage
	 */
	@Test
	public void testGetMessage() {
		assertEquals("friend request", n1.getMessage());
		assertEquals("recommended movie", n2.getMessage());
		assertEquals("friend request confirmed", n3.getMessage());
	}

	/**
	 * To test the method getViewed
	 */
	@Test
	public void testGetViewed() {
		assertEquals(false, n1.getViewed());
		assertEquals(false, n2.getViewed());
		n3.setViewed(true);
		assertEquals(true, n3.getViewed());
	}

	/**
	 * To test the method setID
	 */
	@Test
	public void testSetID() {
		assertEquals(0, n1.getId());
		n1.setId(8);
		assertEquals(8, n1.getId());
		n1.setId(0);
		assertEquals(0, n1.getId());
	}

	/**
	 * To test the method setReceiver
	 */
	@Test
	public void testSetReceiver() {
		assertEquals("receiver1", n1.getReceiver());
		n1.setReceiver("new receiver");
		assertEquals("new receiver", n1.getReceiver());
	}

	/**
	 * To test the method setSender
	 */
	@Test
	public void testSetSender() {
		assertEquals("sender1", n1.getSender());
		n1.setSender("new sender");
		assertEquals("new sender", n1.getSender());
	}

	/**
	 * To test the method setMessage
	 */
	@Test
	public void testSetMessage() {
		assertEquals("friend request", n1.getMessage());
		n1.setMessage("friend request denied");
		assertEquals("friend request denied", n1.getMessage());
	}

	/**
	 * To test the method setViewed
	 */
	@Test
	public void testSetViewed() {
		assertEquals(false, n1.getViewed());
		n1.setViewed(true);
		assertEquals(true, n1.getViewed());
		n1.setViewed(false);
		assertEquals(false, n1.getViewed());
	}
}
