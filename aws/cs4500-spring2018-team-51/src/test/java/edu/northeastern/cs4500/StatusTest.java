package edu.northeastern.cs4500;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs4500.objects.Status;

/**
 * This class StatusTest was created to test the methods of the Status class
 * @author emilytrinh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StatusTest {
	
	/**
	 * Local variables that will be referenced throughout the test class
	 */
	Status f = Status.FRIEND;
	Status n = Status.NOTFRIEND;
	Status p = Status.PENDING;
	Status b = Status.BLOCKED;
	Status t = Status.FRIEND;
	
	/**
	 * To test the method getStatus
	 */
	@Test
	public void getStatusTest() {
		assertEquals(f.getStatus(), "Friend");
		assertEquals(n.getStatus(), "Not_Friend");
		assertEquals(p.getStatus(), "Pending");
		assertEquals(b.getStatus(), "Blocked");
	}
	
	/**
	 * To test the method setStatus
	 */
	@Test
	public void setStatusTest() {
		assertEquals(Status.FRIEND, t.setStatus("friend"));
		assertEquals(Status.NOTFRIEND, t.setStatus("not_friend"));
		assertEquals(Status.PENDING, t.setStatus("pending"));
		assertEquals(Status.BLOCKED, t.setStatus("blocked"));
		assertEquals(Status.FRIEND, t.setStatus("FRIEND"));
		assertEquals(Status.NOTFRIEND, t.setStatus("NOT_FRIEND"));
		assertEquals(Status.PENDING, t.setStatus("PENDING"));
		assertEquals(Status.BLOCKED, t.setStatus("BLOCKED"));
		assertEquals(Status.FRIEND, t.setStatus("Friend"));
		assertEquals(Status.NOTFRIEND, t.setStatus("Not_Friend"));
		assertEquals(Status.PENDING, t.setStatus("Pending"));
		assertEquals(Status.BLOCKED, t.setStatus("Blocked"));
		assertEquals(Status.FRIEND, t.setStatus("FrIEND"));
		assertEquals(Status.NOTFRIEND, t.setStatus("NOt_FrIEnd"));
		assertEquals(Status.PENDING, t.setStatus("PENDing"));
		assertEquals(Status.BLOCKED, t.setStatus("BLOCKed"));
	}
	
	/**
	 * To test the that the method setStatus throws an exception for an invalid status
	 */
	@Test(expected = IllegalArgumentException.class) 
	public void nonsenseTest() { 
		f.setStatus("nonsense"); 
	}
	
	/**
	 * To test the method setStatus throws an exception for an invalid status
	 */
	@Test(expected = IllegalArgumentException.class) 
	public void nonsenseTest2() { 
		f.setStatus("nNonNNSeDERneSDFSDFe2"); 
	}
	
	/**
	 * To test the method setStatus throws an exception if the status is null 
	 */
	@Test(expected = NullPointerException.class) 
	public void nullTest2() { 
		f.setStatus(null); 
	}
	
	/**
	 * To test the method setStatus throws an exception if the status is an empty string. 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void emptyTest() {
		f.setStatus("");
	}
}
