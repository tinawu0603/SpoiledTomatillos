package edu.northeastern.cs4500;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs4500.objects.Movie;
import edu.northeastern.cs4500.objects.Notification;
import edu.northeastern.cs4500.objects.Recommendation;

/**
 * This RecommendationTest class was created to test the methods of Recommendation
 * @author emilytrinh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RecommendationTest {
	
	/**
	 * Local variables that will be referenced throughout the test class. 
	 */
	Notification n1 = new Notification("receiver1", "sender1", "friend request");
	Movie movie2 = new Movie("123", "ET", "1997", "9", "aliens", "alien-spec");
	Recommendation r1 = new Recommendation (n1, movie2);
	
	/**
	 * To test the method getN which gets a notification
	 */
	@Test
	public void getNotificationTest() {
		assertEquals(r1.getN(), n1);
	}
	
	/**
	 * To test the method getM which gets a movie. 
	 */
	@Test
	public void getMovieTest() {
		assertEquals(r1.getM(), movie2);
	}

}
