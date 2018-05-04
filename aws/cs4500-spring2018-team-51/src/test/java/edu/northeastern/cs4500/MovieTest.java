package edu.northeastern.cs4500;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.northeastern.cs4500.objects.Movie;

/**
 * This class MovieTest was created to test the methods and constructors of the class Movie.
 * @author emilytrinh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieTest {
	
	/**
	 * Local variables that will be referenced throughout the MovieTest class
	 */
	Movie movie1 = new Movie("00123", "cars", "1000", "10", "fast car", "car.img");
	Movie movie2 = new Movie("123", "ET", "1997", "9", "aliens", "alien-spec");
	
	/**
	 * To test the method toString
	 */
	@Test
	public void toStringTest() {
		assertEquals(movie1.toString(), "00123, cars");
		assertEquals(movie2.toString(), "123, ET");
	}
	
	// ----------- Tests for Setters and Getters ---------------
	
	/**
	 * To test the method getMovieID
	 */
	@Test
	public void getMovieIDTest() {
		assertEquals("00123", movie1.getMovieID());
		assertEquals("123", movie2.getMovieID());
	}
	
	/**
	 * To test the method getTitle
	 */
	@Test
	public void getTitleTest() {
		assertEquals("cars", movie1.getTitle());
		assertEquals("ET", movie2.getTitle());
	}
	
	/**
	 * To test the method getPlot
	 */
	@Test
	public void getPlotTest() {
		assertEquals("fast car", movie1.getPlot());
		assertEquals("aliens", movie2.getPlot());
	}
	
	/**
	 * To test the method getPoster
	 */
	@Test
	public void getPoster() {
		assertEquals("car.img", movie1.getPoster());
		assertEquals("alien-spec", movie2.getPoster());
	}
	
	/**
	 * To test the method getYear
	 */
	@Test
	public void getYearTest() {
		assertEquals(movie1.getYear(), "1000");
		assertEquals(movie2.getYear(), "1997");
	}
	
	/**
	 * To test that the method setYear updates the year properly 
	 */
	@Test
	public void setYearTest() {
		Movie movie3 = new Movie("1234", "Ianderson", "1990", "9", "dogs", "puppypic");
		assertEquals(movie3.getYear(), "1990");
		assertEquals(movie3.getYear().equals(movie2.getYear()), false);
		movie3.setYear("1997");
		assertEquals(movie3.getYear().equals(movie2.getYear()), true);
	}
	
	/**
	 * To test the method getRating
	 */
	@Test
	public void getRatingTest() {
		assertEquals(movie1.getRating(), "10");
		assertEquals(movie2.getRating(), "9");
	}
	
	/**
	 * To test the method setRating
	 */
	@Test
	public void setRatingTest() {
		Movie movie3 = new Movie("1234", "Ianderson", "1990", "9", "dogs", "puppypic");
		assertEquals(movie3.getRating(), "9");
		assertEquals(movie3.getRating(), movie2.getRating());
		movie3.setRating("10");
		assertEquals(movie3.getRating().equals(movie2.getRating()), false);
	}
	
	/**
	 * To test the method setGenre1
	 */
	@Test
	public void setGenre1Test() {
		Movie movie3 = new Movie("1234", "Ianderson", "1990", "9", "dogs", "puppypic", null, null, null, null);
		Movie movie4 = new Movie("555", "EmilyTrinh", "1990", "9", "dogs", "puppypic", "Action", "Action", "Action", "Action");
		assertEquals(movie3.getGenre1(), null);
		movie3.setGenre1("Action");
		assertEquals(movie3.getGenre1(), movie4.getGenre1());
	}
	
	/**
	 * To test the method setGenre2
	 */
	@Test
	public void setGenre2Test() {
		Movie movie3 = new Movie("1234", "Ianderson", "1990", "9", "dogs", "puppypic", null, null, null, null);
		Movie movie4 = new Movie("555", "EmilyTrinh", "1990", "9", "dogs", "puppypic", "Action", "Action", "Action", "Action");
		assertEquals(movie3.getGenre2(), null);
		movie3.setGenre2("Action");
		assertEquals(movie3.getGenre2(), movie4.getGenre2());
	}
	
	/**
	 * To test the method setGenre3
	 */
	@Test
	public void setGenre3Test() {
		Movie movie3 = new Movie("1234", "Ianderson", "1990", "9", "dogs", "puppypic", null, null, null, null);
		Movie movie4 = new Movie("555", "EmilyTrinh", "1990", "9", "dogs", "puppypic", "Action", "Action", "Action", "Action");
		assertEquals(movie3.getGenre3(), null);
		movie3.setGenre3("Action");
		assertEquals(movie3.getGenre3(), movie4.getGenre3());
	}
	
	/**
	 * To test the method setGenre4
	 */
	@Test
	public void setGenre4Test() {
		Movie movie3 = new Movie("1234", "Ianderson", "1990", "9", "dogs", "puppypic", null, null, null, null);
		Movie movie4 = new Movie("555", "EmilyTrinh", "1990", "9", "dogs", "puppypic", "Action", "Action", "Action", "Action");
		assertEquals(movie3.getGenre4(), null);
		movie3.setGenre4("Action");
		assertEquals(movie3.getGenre4(), movie4.getGenre4());
	}
	
	/**
	 * To test the method setGenre1 with an int
	 */
	@Test
	public void setGenre1v2Test() {
		Movie movie3 = new Movie("1234", "Ianderson", "1990", "9", "dogs", "puppypic", null, null, null, null);
		Movie movie4 = new Movie("555", "EmilyTrinh", "1990", "9", "dogs", "puppypic", "Action", "Action", "Action", "Action");
		assertEquals(movie3.getGenre1(), null);
		movie3.setGenre1(28);
		assertEquals(movie3.getGenre1(), movie4.getGenre1());
	}
	
	/**
	 * To test the method setGenre2 with an int
	 */
	@Test
	public void setGenre2v2Test() {
		Movie movie3 = new Movie("1234", "Ianderson", "1990", "9", "dogs", "puppypic", null, null, null, null);
		Movie movie4 = new Movie("555", "EmilyTrinh", "1990", "9", "dogs", "puppypic", "Action", "Action", "Action", "Action");
		assertEquals(movie3.getGenre2(), null);
		movie3.setGenre2(28);
		assertEquals(movie3.getGenre2(), movie4.getGenre2());
	}
	
	/**
	 * To test the method setGenre3 with an int
	 */
	@Test
	public void setGenre3v2Test() {
		Movie movie3 = new Movie("1234", "Ianderson", "1990", "9", "dogs", "puppypic", null, null, null, null);
		Movie movie4 = new Movie("555", "EmilyTrinh", "1990", "9", "dogs", "puppypic", "Action", "Action", "Action", "Action");
		assertEquals(movie3.getGenre3(), null);
		movie3.setGenre3(28);
		assertEquals(movie3.getGenre3(), movie4.getGenre3());
	}
	
	/**
	 * To test the method setGenre4 with an int
	 */
	@Test
	public void setGenre4v2Test() {
		Movie movie3 = new Movie("1234", "Ianderson", "1990", "9", "dogs", "puppypic", null, null, null, null);
		Movie movie4 = new Movie("555", "EmilyTrinh", "1990", "9", "dogs", "puppypic", "Action", "Action", "Action", "Action");
		assertEquals(movie3.getGenre4(), null);
		movie3.setGenre4(28);
		assertEquals(movie3.getGenre4(), movie4.getGenre4());
	}
	
	/**
	 * To test the method getUserRating
	 */
	@Test
	public void getUserRatingTest() {
		Movie movie4 = new Movie("1234", "cars", "2006", "5", "cool cars", "cars.img");
		assertEquals(movie4.getUserRating(), 0.0, 0.1);
		assertEquals(movie1.getUserRating(), 0.0, 0.1);
		assertEquals(movie2.getUserRating(), 0.0, 0.1);
	}
	
	/**
	 * To test the method setUserRating
	 */
	@Test
	public void setUserRatingTest() {
		Movie movie4 = new Movie("1234", "cars", "2006", "5", "cool cars", "cars.img");
		assertEquals(movie4.getUserRating(), 0.0, 0.1);
		movie4.setUserRating(7.5);
		assertEquals(movie4.getUserRating(), 7.5, 0.1);
		
	}
	
	/**
	 * To test the method getNumber_of_ratings
	 */
	@Test
	public void getNumber_of_ratings() {
		assertEquals(movie1.getNumberOfRatings(), 0);
		assertEquals(movie2.getNumberOfRatings(), 0);
	}
	
	/**
	 * To test the method setNumber_of_ratings
	 */
	@Test
	public void setNumber_of_ratings() {
		Movie movie4 = new Movie("1234", "cars", "2006", "5", "cool cars", "cars.img");
		assertEquals(movie4.getNumberOfRatings(), 0);
		movie4.setNumberOfRatings(5);
		assertEquals(movie4.getNumberOfRatings(), 5);
	}
	
	/**
	 * To test the super constructor
	 */
	@Test
	public void superConstructorTest() {
		Movie movie = new Movie();
		assertEquals(true, movie instanceof Movie);
	}

}
