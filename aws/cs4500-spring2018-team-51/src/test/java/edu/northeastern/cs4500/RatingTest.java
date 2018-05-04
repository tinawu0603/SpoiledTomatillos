package edu.northeastern.cs4500;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import edu.northeastern.cs4500.objects.Rating;
import edu.northeastern.cs4500.objects.RatingDummy;

/**
 * This RatingTest class was created to test the methods of the class Rating. 
 * @author emilytrinh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingTest {
	/**
	 * Local variables that will be referenced throughout the test class. 
	 */
	Rating rating1 = new Rating("e.trinh", "00123", 9.0, "i am speed.");
	Rating rating2 = new Rating("wu.tin", "597", 7.0, "i fell asleep.");
	Rating rating3 = new Rating("wu.tin", "420", 10.0);
	RatingDummy rd = new RatingDummy("wu.tin", "420", 10.0);
	
	/**
	 * To test the default constructor of Rating. 
	 */
	@Test 
	public void defaultConstructorTest() {
		Rating obj = new Rating();
		assertEquals(true, obj instanceof Rating);
	}
	
	/**
	 * To test the constructor of Rating. 
	 */
	@Test 
	public void constructorTest() {
		assertEquals(true, rating3 instanceof Rating);
	}
	
	/**
	 * To test the constructor of RatingDummy. 
	 */
	@Test 
	public void ratingDummyTesting() {
		assertEquals(true, rd instanceof RatingDummy);
	}
	
	/**
	 * To test the method getUser
	 */
	@Test
	public void getUserTest() {
		assertEquals(rating1.getName(), "e.trinh");
		assertEquals(rating2.getName(), "wu.tin");
	}
	
	/**
	 * To test the method getMovie_ID
	 */
	@Test
	public void getMovie_IDTest() {
		assertEquals(rating1.getMovieID(), "00123");
		assertEquals(rating2.getMovieID(), "597");
	}
	
	/**
	 * To test the method getRating
	 */
	@Test
	public void getRatingTest() {
		assertEquals(rating1.getRating(), 9.0, 0.1);
		assertEquals(rating2.getRating(), 7.0, 0.1);
	}
	
	/**
	 * To test the method getReview
	 */
	@Test
	public void getReviewTest() {
		assertEquals(rating1.getReview(), "i am speed.");
		assertEquals(rating2.getReview(), "i fell asleep.");
	}
	
	/**
	 * To test the method setName
	 */
	@Test
	public void setNameTest() {
		assertEquals(rating1.getName(), "e.trinh");
		rating1.setName("emily trinh");
		assertEquals(rating1.getName(), "emily trinh");
		rating1.setName("e.trinh");
		assertEquals(rating1.getName(), "e.trinh");
	}
	
	/**
	 * To test the method setMovie_ID
	 */
	@Test
	public void setMovie_IDTest() {
		assertEquals(rating2.getMovieID(), "597");
		rating2.setMovieID("600");
		assertEquals(rating2.getMovieID(), "600");
		rating2.setMovieID("597");
		assertEquals(rating2.getMovieID(), "597");
	}
	
	/**
	 * To test the method setReview
	 */
	@Test
	public void setReviewTest() {
		assertEquals(rating1.getReview(), "i am speed.");
		rating1.setReview("fast cars are cool");
		assertEquals(rating1.getReview(), "fast cars are cool");
		rating1.setReview("i am speed.");
		assertEquals(rating1.getReview(), "i am speed.");
	}
	
	/**
	 * To test the method for setRating
	 */
	@Test
	public void setRatingTest() {
		assertEquals(rating2.getRating(), 7.0, 0.1);
		rating2.setRating(9.5);
		assertEquals(rating2.getRating(), 9.5, 0.1);
		rating2.setRating(7.0);
		assertEquals(rating2.getRating(), 7.0, 0.1);
	}
}
