package edu.northeastern.cs4500;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import edu.northeastern.cs4500.objects.RatingPK;

/**
 * This RatingPKTest class was created to test the methods of the class RatingPK
 * @author emilytrinh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingPKTest {
	
	/**
	 * Local variables that will be referenced throughout the test class. 
	 */
	RatingPK rating1 = new RatingPK("e.trinh", "00123");
	RatingPK rating2 = new RatingPK("wu.tin", "920");
	
	/**
	 * To test the default constructor for RatingPK
	 */
	@Test 
	public void defaultConstructorTest() {
		RatingPK obj = new RatingPK();
		assertEquals(true, obj instanceof RatingPK);
	}
	
	/**
	 * To test the method getUser
	 */
	@Test
	public void getUserTest() {
		assertEquals(rating1.getUser(), "e.trinh");
		assertEquals(rating2.getUser(), "wu.tin");
	}
	
	/**
	 * To test the method getMovie_ID
	 */
	@Test
	public void getMovie_IDTest() {
		assertEquals(rating1.getMovieId(), "00123");
		assertEquals(rating2.getMovieId(), "920");
	}
	
	/**
	 * To test the method setUser
	 */
	@Test
	public void setUserTest() {
		assertEquals(rating1.getUser(), "e.trinh");
		rating1.setUser("emily");
		assertEquals(rating1.getUser(), "emily");
		rating1.setUser("e.trinh");
		assertEquals(rating1.getUser(), "e.trinh");
	}
	
	/**
	 * To test the method setMovie_ID
	 */
	@Test
	public void setMovie_IDTest() {
		assertEquals(rating2.getMovieId(), "920");
		rating2.setMovieId("597");
		assertEquals(rating2.getMovieId(), "597");
		rating2.setMovieId("920");
		assertEquals(rating2.getMovieId(), "920");
	}
}
