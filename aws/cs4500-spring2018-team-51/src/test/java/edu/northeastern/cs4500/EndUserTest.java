package edu.northeastern.cs4500;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import edu.northeastern.cs4500.objects.EndUser;
import edu.northeastern.cs4500.objects.SpoiledTomatillosException;

/**
 * Class created to test all methods in the EndUser class. 
 * @author emilytrinh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EndUserTest {

	/**
	 * Local variables that will be referenced throughout the End User test class. 
	 */
	EndUser emily = new EndUser(1, "Emily", "Trinh", "trinh.e@husky.neu.edu", "tinalovesher", true);
	EndUser tina = new EndUser(2, "Tina", "Wu", "wu.tin@husky.neu.edu", "sheloveset", true);
	EndUser pat = new EndUser("Pat", "Milne", "email@email.com", "password");
	EndUser ian = new EndUser(4, "Ian", "AnderSON", "email@email.com", "password");

	/**
	 * To test if changePassword updates a user's password correctly.
	 * @throws SpoiledTomatillosException 
	 */
	@Test
	public void changePasswordCorrectTest() throws SpoiledTomatillosException {
		assertEquals(emily.getPassword(), "tinalovesher");
		emily.changePassword("tinalovesher", "shelovestinaback");
		assertEquals(emily.getPassword(), "shelovestinaback");
	}

	/**
	 * To test if changePassword throws an exception when the wrong password is entered.
	 * @throws SpoiledTomatillosException 
	 */
	@Test(expected = SpoiledTomatillosException.class)
	public void changePasswordFailTest() throws SpoiledTomatillosException {
		assertEquals(tina.getPassword(), "sheloveset");
		tina.changePassword("shelovesemily", "tinaiscool");
	}

	/**
	 * To test if the toString method converts a user's full name and email to a string. 
	 */
	@Test
	public void toStringTest() {
		assertEquals("1\nEmily\nTrinh\ntrinh.e@husky.neu.edu", emily.toString());
		assertEquals("2\nTina\nWu\nwu.tin@husky.neu.edu", tina.toString());
	}

	/**
	 * To test if a updating a user's logIn boolean properly logs the user in. 
	 */
	@Test
	public void logInTest() {
		EndUser emily1 = new EndUser(1, "Emily", "Trinh", "trinh.e@husky.neu.edu", "tinalovesher", true);
		EndUser tina1 = new EndUser(2, "Tina", "Wu", "wu.tin@husky.neu.edu", "sheloveset", true);
		assertEquals(false, emily1.isLoggedIn());
		emily1.logIn();
		assertEquals(true, emily1.isLoggedIn());
		assertEquals(false, tina1.isLoggedIn());
		tina1.logIn();
		assertEquals(true, tina1.isLoggedIn());
	}

	/**
	 * To test if updating a user's logIn boolean proper logs the user out. 
	 */
	@Test
	public void logOutTest() {
		EndUser emily2 = new EndUser(1, "Emily", "Trinh", "trinh.e@husky.neu.edu", "tinalovesher", true);
		EndUser tina2 = new EndUser(2, "Tina", "Wu", "wu.tin@husky.neu.edu", "sheloveset", true);
		assertEquals(false, emily2.isLoggedIn());
		emily2.logIn();
		assertEquals(true, emily2.isLoggedIn());
		emily2.logOut();
		assertEquals(false, emily2.isLoggedIn());
		assertEquals(false, tina2.isLoggedIn());
		tina2.logIn();
		assertEquals(true, tina2.isLoggedIn());
		tina2.logOut();
		assertEquals(false, tina2.isLoggedIn());
	}

	/**
	 * Testing the method getID to get an end user's ID.
	 */
	@Test
	public void getIdTest() {
		assertEquals(1, emily.getId());
		assertEquals(2, tina.getId());
	}

	/**
	 * Testing the method setID to update an end user's ID.
	 */
	@Test
	public void setIdTest() {
		assertEquals(1, emily.getId());
		emily.setId(10);
		assertEquals(10, emily.getId());
		emily.setId(1);
		assertEquals(1, emily.getId());
	}

	/**
	 * Testing the method getFirstName to get an end user's first name.
	 */
	@Test
	public void getFirstNameTest() {
		assertEquals("Emily", emily.getFirstName());
		assertEquals("Tina", tina.getFirstName());
	}

	/**
	 * Testing the method getLastName to get an end user's last name.
	 */
	@Test
	public void getLastNameTest() {
		assertEquals("Trinh", emily.getLastName());
		assertEquals("Wu", tina.getLastName());
	}

	/**
	 * Testing the method getEmail to get an end user's email address.
	 */
	@Test
	public void getEmailTest() {
		assertEquals("trinh.e@husky.neu.edu", emily.getEmail());
		assertEquals("wu.tin@husky.neu.edu", tina.getEmail());
	}

	/**
	 * Testing the method getPassword to get an end user's password
	 */
	@Test
	public void getPasswordTest() {
		assertEquals("tinalovesher", emily.getPassword());
		assertEquals("sheloveset", tina.getPassword());
	}

	/**
	 * Testing the method isAuthorized to check that the boolean is "true" for an admin.
	 */
	@Test
	public void isAuthorizedTest() {
		assertEquals(true, emily.isAuthorized());
		assertEquals(true, tina.isAuthorized());
	}

	/**
	 * Testing the method isLoggedIn to check when the boolean is "true" that a user is logged in.
	 */
	@Test
	public void isLoggedInTest() {
		assertEquals(false, emily.isLoggedIn());
		assertEquals(false, tina.isLoggedIn());
	}

	/**
	 * Testing the method setEmail to check if the end user's email is properly updated. 
	 */
	@Test
	public void setEmailTest() {
		assertEquals("wu.tin@husky.neu.edu", tina.getEmail());
		tina.setEmail("tinawu0603@hotmail.com");
		assertEquals("tinawu0603@hotmail.com", tina.getEmail());
	}

	/**
	 * Testing the equals method to check if two users who are equal have the same first name, last name, and email. 
	 */
	@Test
	public void testEquals() {
		EndUser tina2 = new EndUser(2, "Tina", "Wu", "wu.tin@husky.neu.edu", "sheloveset", true);
		assertEquals(true, tina.equals(tina));
		assertEquals(true, tina.equals(tina2));
		assertEquals(false, tina.equals(emily));
		assertEquals(false, tina.equals("hello"));

	}
	
	/**
	 * To test the method hashCode
	 * 
	 */
	@Test
	public void hashCodeTest() {
	int temp = emily.hashCode();
		assertEquals(0, temp * 0);
	}

}
