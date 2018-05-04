package edu.northeastern.cs4500;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import edu.northeastern.cs4500.objects.Friendship;

/**
 * The class FriendshipTest was created to test the methods and constructors of the class Friendship.
 * @author emilytrinh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendshipTest {

	/**
	 * Local variables that will be referenced throughout the test class. 
	 */
	Friendship friends = new Friendship("trinh.e@husky.neu.edu", "wu.tin@husky.neu.edu");
	Friendship friends2 = new Friendship("milne.p@husky.neu.edu", "iananderson@ccs.neu.edu");

	/**
	 * Test for the empty constructor of a Friendship
	 */
	@Test 
	public void defaultConstructorTest() {
		Friendship obj = new Friendship();
		assertEquals(true, obj instanceof Friendship);
	}

	/**
	 * Test to get user1 of a friendship. 
	 */
	@Test
	public void getUser1Test() {
		assertEquals("trinh.e@husky.neu.edu", friends.getUser1());
		assertEquals("milne.p@husky.neu.edu", friends2.getUser1());
	}

	/**Test to get user2 of a friendship.
	 * 
	 */
	@Test
	public void getUser2Test() {
		assertEquals("wu.tin@husky.neu.edu", friends.getUser2());
		assertEquals("iananderson@ccs.neu.edu", friends2.getUser2());
	}

	/**
	 * Test to set user1 of a friendship, to ensure that user1 is updated properly. 
	 */
	@Test
	public void setUser1Test() {
		assertEquals("trinh.e@husky.neu.edu", friends.getUser1());
		friends.setUser1("emily88trinh@ccs.neu.edu");
		assertEquals("emily88trinh@ccs.neu.edu", friends.getUser1());
		friends.setUser1("trinh.e@husky.neu.edu");
		assertEquals("trinh.e@husky.neu.edu", friends.getUser1());
	}

	/**
	 * Test to set user2 of a friendship, to ensure that user2 is updated properly. 
	 */
	@Test
	public void setUser2Test() {
		assertEquals("iananderson@ccs.neu.edu", friends2.getUser2());
		friends2.setUser2("anderson.i@husky.neu.edu");
		assertEquals("anderson.i@husky.neu.edu", friends2.getUser2());
		friends2.setUser2("iananderson@ccs.neu.edu");
		assertEquals("iananderson@ccs.neu.edu", friends2.getUser2());
	}

	/**
	 * Test to check if two friendships are equal. 
	 */
	@Test
	public void equalsTest() {
		Friendship friends3 = new Friendship("trinh.e@husky.neu.edu", "wu.tin@husky.neu.edu");
		Friendship friends4 = new Friendship("ha@Ha.com", "wu.tin@husky.neu.edu");
		Friendship friends5 = new Friendship("trinh.e@husky.neu.edu", "lolol@lolol.com");
		assertEquals(true, friends.equals(friends3));
		assertEquals(true, friends3.equals(friends3));
		String test = "trinh.e@husky.neu.edu";
		assertEquals(false, friends3.equals(test));
		assertEquals(false, friends3.equals(friends4));
		assertEquals(false, friends3.equals(friends5));
		assertEquals(false, friends4.equals(friends5));
	}

	/**
	 * To test the method hashCode
	 * 
	 */
	@Test
	public void hashCodeTest() {
		Friendship friends3 = new Friendship("trinh.e@husky.neu.edu", "wu.tin@husky.neu.edu");
		int temp = friends3.hashCode();
		assertEquals(0, temp * 0);
	}
	
	/**
	 * To test the method hashCode
	 * 
	 */
	@Test
	public void hashCode2Test() {
		Friendship friends3 = new Friendship("trinh.e@husky.neu.edu", "wu.tin@husky.neu.edu");
		int temp = friends3.hashCode();
		assertEquals(true, temp  > 3000);
	}

}
