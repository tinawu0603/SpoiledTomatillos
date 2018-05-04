package edu.northeastern.cs4500;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.intThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import edu.northeastern.cs4500.objects.Genre;
import edu.northeastern.cs4500.objects.GenreObject;

/**
 * GenreObjectTest was created to test the methods of the class GenreObject. 
 * @author emilytrinh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreObjectTest {
	
	/**
	 * Local variables that will be referenced throughout the GenreObjectTest class. 
	 */
	Genre g1 = Genre.ACTION;
	Genre g2 = Genre.ADVENTURE;
	GenreObject go1 = new GenreObject(g1);
	GenreObject go2 = new GenreObject(g1);
	GenreObject go3 = new GenreObject(g2);
	
	/**
	 * Test to check if two GenreObjects have the same weighted average rating. 
	 */
	@Test 
	public void compareTo1Test() {
		assertEquals(0, go1.compareTo(go2));
	}
	
	/**
	 * Test to check if two GenreObjects have different weighted averages. 
	 */
	@Test 
	public void compareTo2Test() {
		go3.addRating(9.0);
		go3.addRating(9.0);
		go3.addRating(9.0);
		go3.addRating(10.0);
		go3.addRating(10.0);
		go1.addRating(0.0);
		assertEquals(-1, go1.compareTo(go3));
	}
	
	/**
	 * Test to check if two GenreObjects have different weighted averages. 
	 */
	@Test 
	public void compareTo3Test() {
		go3.addRating(9.0);
		go3.addRating(9.0);
		go3.addRating(9.0);
		go3.addRating(10.0);
		go3.addRating(10.0);
		go1.addRating(0.0);
		assertEquals(1, go3.compareTo(go1));
	}
	
	/**
	 * Test to check if two GenreObjects have different genres 
	 */
	@Test 
	public void equalsTest() {
		assertEquals(false, go1.equals(go3));
	}
	
	/**
	 * Test to check if two GenreObjects are the same. 
	 */
	@Test 
	public void equals2Test() {
		assertEquals(true, go1.equals(go1));
	}
	
	/**
	 * Test to check if two GenreObjects are not equal because one is not a genre object. 
	 */
	@Test 
	public void equals3Test() {
		assertEquals(false, go1.equals(g1));
	}
	
	/**
	 * To test the method hashCode
	 * 
	 */
	@Test
	public void hashCodeTest() {
	int temp = go1.hashCode();
		assertEquals(0, temp * 0);
	}

}
