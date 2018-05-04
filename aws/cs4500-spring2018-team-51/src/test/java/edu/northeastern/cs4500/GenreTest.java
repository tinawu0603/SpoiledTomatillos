package edu.northeastern.cs4500;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import edu.northeastern.cs4500.objects.Genre;

/**
 * GenreTest was created to test the methods in the Genre class. 
 * @author emilytrinh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreTest {

	/**
	 * To test the method toString for the case "Crime"
	 */
	@Test 
	public void crimeStringTest() {
		Genre obj = Genre.CRIME;
		assertEquals("Crime", obj.toString());
	}

	/**
	 * To test the method toString for the case "Fantasy"
	 */
	@Test 
	public void fantasyStringTest() {
		Genre obj = Genre.FANTASY;
		assertEquals("Fantasy", obj.toString());
	}

	/**
	 * To test the method toString for the case "History"
	 */
	@Test 
	public void historyStringTest() {
		Genre obj = Genre.HISTORY;
		assertEquals("History", obj.toString());
	}

	/**
	 * To test the method toString for the case "Music"
	 */
	@Test 
	public void musicStringtest() {
		Genre obj = Genre.MUSIC;
		assertEquals("Music", obj.toString());
	}

	/**
	 * To test the method toString for the case "Western"
	 */
	@Test 
	public void westStringtest() {
		Genre obj = Genre.WESTERN;
		assertEquals("Western", obj.toString());
	}

	/**
	 * To test the method toString for the case "Science Fiction"
	 */
	@Test 
	public void scienceStringTest() {
		Genre obj = Genre.SCIENCEFICTION;
		assertEquals("Science Fiction", obj.toString());
	}

	/**
	 * To test the method toString for the case "TV Movie"
	 */
	@Test 
	public void tvMovieStringTest() {
		Genre obj = Genre.TVMOVIE;
		assertEquals("TV Movie", obj.toString());
	}

	/**
	 * To test the method toString for the case "War"
	 */
	@Test 
	public void warStringTest() {
		Genre obj = Genre.WAR;
		assertEquals("War", obj.toString());
	}

	/**
	 * To test the method getGenreInt for the case "Animation"
	 */
	@Test 
	public void animationGenreIntTest() {
		Genre obj = Genre.ANIMATION;
		assertEquals(16, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "Action"
	 */
	@Test 
	public void actionGenreIntTest() {
		Genre obj = Genre.ACTION;
		assertEquals(28, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "Adventure"
	 */
	@Test 
	public void adventureGenreIntTest() {
		Genre obj = Genre.ADVENTURE;
		assertEquals(12, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "Comedy"
	 */
	@Test 
	public void comedyGenreIntTest() {
		Genre obj = Genre.COMEDY;
		assertEquals(35, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "Crime"
	 */
	@Test 
	public void crimeGenreIntTest() {
		Genre obj = Genre.CRIME;
		assertEquals(80, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "Western"
	 */
	@Test 
	public void westGenreIntTest() {
		Genre obj = Genre.WESTERN;
		assertEquals(37, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "Documentary"
	 */
	@Test 
	public void docGenreIntTest() {
		Genre obj = Genre.DOCUMENTARY;
		assertEquals(99, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "Drama"
	 */
	@Test 
	public void dramaGenreIntTest() {
		Genre obj = Genre.DRAMA;
		assertEquals(18, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "Family"
	 */
	@Test 
	public void familyGenreIntTest() {
		Genre obj = Genre.FAMILY;
		assertEquals(10751, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "Fantasy"
	 */
	@Test 
	public void fantasyGenreIntTest() {
		Genre obj = Genre.FANTASY;
		assertEquals(14, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "History"
	 */
	@Test 
	public void historyGenreIntTest() {
		Genre obj = Genre.HISTORY;
		assertEquals(36, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "Horror"
	 */
	@Test 
	public void horrorGenreIntTest() {
		Genre obj = Genre.HORROR;
		assertEquals(27, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "Music"
	 */
	@Test 
	public void musicGenreIntTest() {
		Genre obj = Genre.MUSIC;
		assertEquals(10402, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "Mystery"
	 */
	@Test 
	public void mysteryGenreIntTest() {
		Genre obj = Genre.MYSTERY;
		assertEquals(9648, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "Romance"
	 */
	@Test 
	public void romanceGenreIntTest() {
		Genre obj = Genre.ROMANCE;
		assertEquals(10749, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "Science fiction"
	 */
	@Test 
	public void scienceGenreIntTest() {
		Genre obj = Genre.SCIENCEFICTION;
		assertEquals(878, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "TV Movie"
	 */
	@Test 
	public void tvMovieGenreIntTest() {
		Genre obj = Genre.TVMOVIE;
		assertEquals(10770, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "Thriller"
	 */
	@Test 
	public void thrillerMovieGenreIntTest() {
		Genre obj = Genre.THRILLER;
		assertEquals(53, obj.getGenreInt());
	}

	/**
	 * To test the method getGenreInt for the case "War"
	 */
	@Test 
	public void warrMovieGenreIntTest() {
		Genre obj = Genre.WAR;
		assertEquals(10752, obj.getGenreInt());
	}

	/**
	 * To test the method setGenreString for the case "Crime"
	 */
	@Test
	public void sciencefictionSetGenreStringTest() {
		Genre obj = Genre.SCIENCEFICTION;
		assertEquals(Genre.setGenreString("science fiction"), obj);
	}

	/**
	 * To test the method setGenreString for the case "Science Fiction"
	 */
	@Test
	public void crimeSetGenreStringTest() {
		Genre obj = Genre.CRIME;
		assertEquals(Genre.setGenreString("crime"), obj);
	}

	/**
	 * To test the method setGenreString for the case "Western"
	 */
	@Test
	public void westSetGenreStringTest() {
		Genre obj = Genre.WESTERN;
		assertEquals(Genre.setGenreString("western"), obj);
	}

	/**
	 * To test the method setGenreString for the case "History"
	 */
	@Test
	public void hisSetGenreStringTest() {
		Genre obj = Genre.HISTORY;
		assertEquals(Genre.setGenreString("history"), obj);
	}

	/**
	 * To test the method setGenreString for the case "Music"
	 */
	@Test
	public void musicSetGenreStringTest() {
		Genre obj = Genre.MUSIC;
		assertEquals(Genre.setGenreString("music"), obj);
	}

	/**
	 * To test the method setGenreString for the case "Science Fiction"
	 */
	@Test
	public void sciSetGenreStringTest() {
		Genre obj = Genre.SCIENCEFICTION;
		assertEquals(Genre.setGenreString("sciencefiction"), obj);
	}

	/**
	 * To test the method setGenreString for the case "TV Movie"
	 */
	@Test
	public void tvMovieSetGenreStringTest() {
		Genre obj = Genre.TVMOVIE;
		assertEquals(Genre.setGenreString("tvmovie"), obj);
	}



	/**
	 * To test the method setGenreString for the case "TV Movie"
	 */
	@Test
	public void tvMovieSetGenreString2Test() {
		Genre obj = Genre.TVMOVIE;
		assertEquals(Genre.setGenreString("tv movie"), obj);
	}

	/**
	 * To test the method setGenreString for the case "War"
	 */
	@Test
	public void warSetGenreString2Test() {
		Genre obj = Genre.WAR;
		assertEquals(Genre.setGenreString("war"), obj);
	}

	/**
	 * To test the method setGenreInt for the case "Crime"
	 */
	@Test
	public void crimeSetGenreIntTest() {
		Genre obj = Genre.CRIME;
		assertEquals(Genre.setGenreInt(80), obj);
	}

	/**
	 * To test the method setGenreInt for the case "Western"
	 */
	@Test
	public void westernSetGenreIntTest() {
		Genre obj = Genre.WESTERN;
		assertEquals(Genre.setGenreInt(37), obj);
	}

	/**
	 * To test the method setGenreInt for the case "Fantasy"
	 */
	@Test
	public void fantasySetGenreIntTest() {
		Genre obj = Genre.FANTASY;
		assertEquals(Genre.setGenreInt(14), obj);
	}

	/**
	 * To test the method setGenreInt for the case "History"
	 */
	@Test
	public void historySetGenreIntTest() {
		Genre obj = Genre.HISTORY;
		assertEquals(Genre.setGenreInt(36), obj);
	}

	/**
	 * To test the method setGenreInt for the case "Music"
	 */
	@Test
	public void musicSetGenreIntTest() {
		Genre obj = Genre.MUSIC;
		assertEquals(Genre.setGenreInt(10402), obj);
	}

	/**
	 * To test the method setGenreInt for the case "Science Fiction"
	 */
	@Test
	public void scifiSetGenreIntTest() {
		Genre obj = Genre.SCIENCEFICTION;
		assertEquals(Genre.setGenreInt(878), obj);
	}

	/**
	 * To test the method setGenreInt for the case "TV Movie"
	 */
	@Test
	public void tvMovieSetGenreIntTest() {
		Genre obj = Genre.TVMOVIE;
		assertEquals(Genre.setGenreInt(10770), obj);
	}

	/**
	 * To test the method setGenreInt for the case "War"
	 */
	@Test
	public void warSetGenreIntTest() {
		Genre obj = Genre.WAR;
		assertEquals(Genre.setGenreInt(10752), obj);
	}

	/**
	 * To test the method setGenreString for the case "action"
	 */
	@Test
	public void action2SetGenreStringTest() {
		Genre obj = Genre.ACTION;
		assertEquals(Genre.setGenreString("AcTion"), obj);
	}

	/**
	 * To test the method setGenreString for the case "adventure"
	 */
	@Test
	public void adventureSetGenreStringTest() {
		Genre obj = Genre.ADVENTURE;
		assertEquals(Genre.setGenreString("AdVenture"), obj);
	}

	/**
	 * To test the method setGenreString for the case "animation"
	 */
	@Test
	public void anSetGenreStringTest() {
		Genre obj = Genre.ANIMATION;
		assertEquals(Genre.setGenreString("aNIMATion"), obj);
	}

	/**
	 * To test the method setGenreString for the case "comedy"
	 */
	@Test
	public void comedySetGenreStringTest() {
		Genre obj = Genre.COMEDY;
		assertEquals(Genre.setGenreString("cOMEDY"), obj);
	}

	/**
	 * To test the method setGenreString for the case "crime"
	 */
	@Test
	public void crim2SetGenreStringTest() {
		Genre obj = Genre.CRIME;
		assertEquals(Genre.setGenreString("CRIme"), obj);
	}

	/**
	 * To test the method setGenreString for the case "documentary"
	 */
	@Test
	public void docSetGenreStringTest() {
		Genre obj = Genre.DOCUMENTARY;
		assertEquals(Genre.setGenreString("docuMENTARY"), obj);
	}

	/**
	 * To test the method setGenreString for the case "drama"
	 */
	@Test
	public void dramaSetGenreStringTest() {
		Genre obj = Genre.DRAMA;
		assertEquals(Genre.setGenreString("dRAMA"), obj);
	}

	/**
	 * To test the method setGenreString for the case "fam"
	 */
	@Test
	public void familySetGenreStringTest() {
		Genre obj = Genre.FAMILY;
		assertEquals(Genre.setGenreString("faMILY"), obj);
	}

	/**
	 * To test the method setGenreString for the case "fantasy"
	 */
	@Test
	public void fantasySetGenreStringTest() {
		Genre obj = Genre.FANTASY;
		assertEquals(Genre.setGenreString("FANTASy"), obj);
	}

	/**
	 * To test the method setGenreString for the case "history"
	 */
	@Test
	public void historySetGenreStringTest() {
		Genre obj = Genre.HISTORY;
		assertEquals(Genre.setGenreString("HisTORY"), obj);
	}

	/**
	 * To test the method setGenreString for the case "horror"
	 */
	@Test
	public void horrorSetGenreStringTest() {
		Genre obj = Genre.HORROR;
		assertEquals(Genre.setGenreString("HORror"), obj);
	}

	/**
	 * To test the method setGenreString for the case "music"
	 */
	@Test
	public void music2SetGenreStringTest() {
		Genre obj = Genre.MUSIC;
		assertEquals(Genre.setGenreString("MUSic"), obj);
	}

	/**
	 * To test the method setGenreString for the case "mystery"
	 */
	@Test
	public void mysterySetGenreStringTest() {
		Genre obj = Genre.MYSTERY;
		assertEquals(Genre.setGenreString("MYStery"), obj);
	}

	/**
	 * To test the method setGenreString for the case "romance"
	 */
	@Test
	public void romance2SetGenreStringTest() {
		Genre obj = Genre.ROMANCE;
		assertEquals(Genre.setGenreString("ROMAnce"), obj);
	}

	/**
	 * To test the method setGenreString for the case "Science Fiction"
	 */
	@Test
	public void scienceFiction2SetGenreStringTest() {
		Genre obj = Genre.SCIENCEFICTION;
		assertEquals(Genre.setGenreString("SCIENCEfiction"), obj);
	}

	/**
	 * To test the method setGenreString for the case "Science Fiction"
	 */
	@Test
	public void scienceFiction3SetGenreStringTest() {
		Genre obj = Genre.SCIENCEFICTION;
		assertEquals(Genre.setGenreString("SCIENCE fiction"), obj);
	}

	/**
	 * To test the method setGenreString for the case "tv movie"
	 */
	@Test
	public void tvmovie3SetGenreStringTest() {
		Genre obj = Genre.TVMOVIE;
		assertEquals(Genre.setGenreString("TV MOVie"), obj);
	}

	/**
	 * To test the method setGenreString for the case "tv movie"
	 */
	@Test
	public void tvmovie4SetGenreStringTest() {
		Genre obj = Genre.TVMOVIE;
		assertEquals(Genre.setGenreString("TVMOVie"), obj);
	}

	/**
	 * To test the method setGenreString for the case "thriller"
	 */
	@Test
	public void thrillerSetGenreStringTest() {
		Genre obj = Genre.THRILLER;
		assertEquals(Genre.setGenreString("THRIller"), obj);
	}

	/**
	 * To test the method setGenreString for the case "war"
	 */
	@Test
	public void warSetGenreStringTest() {
		Genre obj = Genre.WAR;
		assertEquals(Genre.setGenreString("WAr"), obj);
	}

	/**
	 * To test the method setGenreString for the case "western"
	 */
	@Test
	public void westernSetGenreStringTest() {
		Genre obj = Genre.WESTERN;
		assertEquals(Genre.setGenreString("CARSdfdafasd"), obj);
	}

	/**
	* To test the method setGenreString for the case "western"
	*/
	@Test
	public void otherTest() {
		Genre obj = Genre.WESTERN;
		assertEquals(Genre.setGenreString("\0action"), obj);
		assertEquals(Genre.setGenreString("\0adventure"), obj);
		assertEquals(Genre.setGenreString("\0animation"), obj);
		assertEquals(Genre.setGenreString("\0comedy"), obj);
		assertEquals(Genre.setGenreString("\0crime"), obj);
		assertEquals(Genre.setGenreString("\0documentary"), obj);
		assertEquals(Genre.setGenreString("\0drama"), obj);
		assertEquals(Genre.setGenreString("\0family"), obj);
		assertEquals(Genre.setGenreString("\0fantasy"), obj);
		assertEquals(Genre.setGenreString("\0history"), obj);
		assertEquals(Genre.setGenreString("\0horror"), obj);
		assertEquals(Genre.setGenreString("\0music"), obj);
		assertEquals(Genre.setGenreString("\0mystery"), obj);
		assertEquals(Genre.setGenreString("\0romance"), obj);
		assertEquals(Genre.setGenreString("\0sciencefiction"), obj);
		assertEquals(Genre.setGenreString("\0science fiction"), obj);
		assertEquals(Genre.setGenreString("\0tvmovie"), obj);
		assertEquals(Genre.setGenreString("\0tv movie"), obj);
		assertEquals(Genre.setGenreString("\0thriller"), obj);
		assertEquals(Genre.setGenreString("\0war"), obj);
	}


}
