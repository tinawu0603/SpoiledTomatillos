package edu.northeastern.cs4500;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.NestedServletException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.northeastern.cs4500.controllers.MovieController;
import edu.northeastern.cs4500.controllers.TmdbConnection;
import edu.northeastern.cs4500.objects.EndUser;
import edu.northeastern.cs4500.objects.Movie;
import edu.northeastern.cs4500.objects.Notification;
import edu.northeastern.cs4500.objects.Rating;
import edu.northeastern.cs4500.objects.RatingDummy;
import edu.northeastern.cs4500.repository.EndUserRepository;
import edu.northeastern.cs4500.repository.MovieRepository;
import edu.northeastern.cs4500.repository.NotificationRepository;
import edu.northeastern.cs4500.repository.RatingsRepository;

/**
 * Class MovieControllerTest was created to test the methods of the class MovieController
 * @author emilytrinh
 *
 */
@CrossOrigin
@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieControllerTest {

	/**
	 * Local variables that will be referenced throughout the test class. 
	 */
	private MockMvc mvc;
	private ObjectMapper objectMapper; 

	@Mock
	private MovieRepository movieRepo;
	@Mock
	private TmdbConnection tmdb;
	@Mock
	private NotificationRepository notifRepo;
	@Mock
	private EndUserRepository userRepo;
	@Mock
	private RatingsRepository ratingsRepo;

	@InjectMocks
	private MovieController control;

	private Movie movie1;
	private Movie movie2;
	private Movie movie3;
	private Notification notif1;
	private Notification notif2;
	private Rating rating1;
	private EndUser user1;
	private EndUser user2;
	private RatingDummy ratingdummy1;

	@Before
	public void init() {
		this.mvc = MockMvcBuilders.standaloneSetup(this.control).build();
		this.movie1 = new Movie("00123", "cars", "1000", "10", "fast car", "car.img");
		this.movie2 = new Movie("123", "ET", "1997", "9", "aliens", "alien-spec");
		this.movie3 = null;
		this.notif1 = new Notification("e.trinh", "wu.tin", "recommending cars to emily");
		this.notif2 = new Notification("e.trinh", "wu.tin", "recommending titanic emily");
		this.rating1 = new Rating("e.trinh", "00123", 10.0, "i am speed");
		this.objectMapper = new ObjectMapper();
		this.user1 = new EndUser(1, "Emily", "Trinh", "trinh.e@husky.neu.edu", "tinalovesher", true);
		this.user2 = new EndUser(2, "Emily", "Trinh", "e.trinh", "tinalovesher", true);
		this.ratingdummy1 = new RatingDummy("cars", "00123", 10.0, "ka chow");
	}


	/**
	 * To test the method findAllMovies
	 * @throws Exception
	 */
	@Test
	public void findAllMoviesTest() throws Exception {
		List<Movie> movies = Arrays.asList(this.movie1, this.movie2);
		when(this.movieRepo.findAll()).thenReturn(movies);
		this.mvc.perform(get("/api/movie"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(
				this.objectMapper.writeValueAsString(movies)
				));
	}

	/**
	 * To test the method searchMovieByTitle
	 * @throws Exception
	 */
	@Test
	public void searchMovieByTitleTest() throws Exception {
		this.mvc.perform(get("/api/movie/title/cars"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].title").value("Cars"));
	}

	/**
	 * To test the method findMoviebyID
	 * @throws Exception
	 */
	@Test
	public void findMoviebyIDTest() throws Exception {
		when(movieRepo.findById("00123")).thenReturn(this.movie1);

		this.mvc.perform(get("/api/movie/00123"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.title", is("cars")))
		.andExpect(jsonPath("$.year", is("1000")));
	}

	/**
	 * To test the method insertMovie
	 * @throws Exception
	 */
	@CrossOrigin
	@Test
	public void insertMovieTest() throws Exception {
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(movieRepo).save(any(Movie.class));

		mvc.perform(get("/api/movie/insert/920"))
		.andExpect(status().isOk());
	}

	/**
	 * To test the method recommendFromTMDB
	 * @throws Exception
	 */
	@Test
	public void recommendFromTMDBTest() throws Exception {
		Movie m = new Movie("00123", "cars", "1000", "10", "fast car", "car.img", "Action", "Adventure", "Comedy", "Romance");
		when(this.ratingsRepo.findUserRating("emily", "00123")).thenReturn(null);
		when(this.userRepo.findByEmail("emily")).thenReturn(this.user1);
		when(this.ratingsRepo.findAllUserRatings("emily")).thenReturn(Arrays.asList(this.rating1, this.rating1, this.rating1));
		when(this.movieRepo.findById("00123")).thenReturn(m);
		mvc.perform(get("/api/movie/recommend/emily"))
		.andExpect(status().isOk());
	}

	@Test
	public void recommendFromSTTest1() throws Exception {
		Movie m = new Movie("00123", "cars", "1000", "10", "fast car", "car.img", "Action", "Adventure", "Comedy", "Romance");
		List<Movie> movies = Arrays.asList(m, this.movie1, this.movie2);
		when(this.movieRepo.searchTopGenre("Action")).thenReturn(movies);
		when(this.ratingsRepo.findAllUserRatings("emily")).thenReturn(Arrays.asList(this.rating1, this.rating1));
		when(this.userRepo.findByEmail("emily")).thenReturn(this.user1);
		when(this.movieRepo.findById("00123")).thenReturn(m);
		mvc.perform(get("/api/movie/recommend2/emily"))
		.andExpect(status().isOk());
	}

	/**
	 * To test the method recommendMovie throws an exception if the movie has already been recommended.
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void recommendMovieTest1() throws Exception {
		when(notifRepo.findRecommendation("wu.tin", "e.trinh", "920")).thenReturn(this.notif1);
		mvc.perform(get("/api/movie/recommend/wu.tin/e.trinh/920/").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method recommendMovie works when that movie has not been recommended between two users. 
	 * @throws Exception
	 */
	@Test
	public void recommendMovieTest2() throws Exception {
		when(movieRepo.findById("920")).thenReturn(this.movie1);
		when(this.notifRepo.findRecommendation("wu.tin", "e.trinh", "%Cars%")).thenReturn(notif1);
		when(userRepo.findByEmail("wu.tin")).thenReturn(this.user1);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				System.out.println("notificationRepo.save(no)");
				return null;
			}
		}).when(this.notifRepo).save(any(Notification.class));
		mvc.perform(get("/api/movie/recommend/wu.tin/e.trinh/920").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("e.trinh")))
		.andExpect(content().string(containsString("wu.tin")));
	}

	/**
	 * To test if findRecommendation returns false when a movie has not been recommended. 
	 * @throws Exception
	 */
	@Test
	public void findRecommendationFalseTest() throws Exception {
		when(notifRepo.findRecommendation("wu.tin", "e.trinh", "%Cars%")).thenReturn(notif1);
		mvc.perform(get("/api/movie/findrecommendation/wu.tin/e.trinh/920"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("false")));
	}

	/**
	 * To test if findRecommendation returns false when a movie has not been recommended. 
	 * @throws Exception
	 */
	@Test
	public void findRecommendationTrueTest() throws Exception {
		when(notifRepo.findRecommendation("wu.tin", "e.trinh", "%Cars%")).thenReturn(notif1);
		mvc.perform(get("/api/movie/findrecommendation/wu.tin/e.trinh/920"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("false")));
	}

	/**
	 * To test the method getAllRecommendations
	 * @throws Exception
	 */
	@Test
	public void getAllRecommendationsTest() throws Exception {
		Notification n = new Notification("e.trinh", "wu.tin", "recommending cars to emily", "00123");
		List<Notification> notifs = Arrays.asList(n);
		when(notifRepo.getAllRecommendations("e.trinh", "%recommended%")).thenReturn(notifs);

		mvc.perform(get("/api/movie/getAllRecommendations/e.trinh/"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("")));
	}

	/**
	 * To test the method seeAllUnviewed, which returns all unread notficiations. 
	 * @throws Exception
	 */
	@Test
	public void seeAllUnviewedTest() throws Exception {
		List<Notification> notifs = Arrays.asList(this.notif1, this.notif2);
		when(control.seeAllUnviewed("e.trinh")).thenReturn(notifs);

		mvc.perform(get("/api/movie/getUnviewed/e.trinh/").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].receiver", is("e.trinh")))
		.andExpect(jsonPath("$[0].sender", is("wu.tin")))
		.andExpect(jsonPath("$[0].message", is("recommending cars to emily")));
	}

	/**
	 * To test the method markAsViewed
	 * @throws Exception
	 */
	@Test
	public void markAsViewedTest() throws Exception {
		List<Notification> notifs = Arrays.asList(this.notif1, this.notif2);
		when(control.markAsViewed(0)).thenReturn(notifs);

		mvc.perform(get("/api/movie/markViewed/0").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[1].receiver", is("e.trinh")))
		.andExpect(jsonPath("$[1].sender", is("wu.tin")));
	}

	/**
	 * To test the method getUserRatings
	 * @throws Exception
	 */
	@Test
	public void getUserRatingsTest() throws Exception {
		List<Rating> ratings = Arrays.asList(this.rating1);
		when(userRepo.findByEmail("e.trinh")).thenReturn(user2);
		when(ratingsRepo.findAllUserRatings("e.trinh")).thenReturn(ratings);
		mvc.perform(get("/api/rate/e.trinh/"))
		.andExpect(jsonPath("$[0].rating", is(10.0)))
		.andExpect(jsonPath("$[0].review", is("i am speed")));
	}

	/**
	 * To test that the method getUserRatings throws an exception when the user does not exist. 
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void getUserRatingsTestException() throws Exception {
		when(userRepo.findByEmail("e.trinh")).thenReturn(null);
		mvc.perform(get("/api/rate/e.trinh/"));
	}

	/**
	 * To test that the method findUserRating throws an exception if the rating does not exist. 
	 * @throws Exception
	 */
	@Test
	public void ratedTest1() throws Exception {
		when(ratingsRepo.findUserRating("e.trinh", "00123")).thenReturn(null);
		mvc.perform(get("/api/rate/e.trinh/00123"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("false")));
	}

	/**
	 * To test the method findUserRating
	 * @throws Exception
	 */
	@Test
	public void ratedTest2() throws Exception {
		when(ratingsRepo.findUserRating("e.trinh", "00123")).thenReturn(this.rating1);
		mvc.perform(get("/api/rate/e.trinh/00123"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("true")));
	}

	/**
	 * To test the method findARating
	 * @throws Exception
	 */
	@Test
	public void findARatingTest() throws Exception {
		when(ratingsRepo.findUserRating("e.trinh", "00123")).thenReturn(this.rating1);
		mvc.perform(get("/api/getrating/e.trinh/00123").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.rating", is(10.0)));
	}

	/**
	 * To test the method findAllRatings
	 * @throws Exception
	 */
	@Test
	public void findAllRatingsTest() throws Exception {
		List<Rating> ratings = Arrays.asList(this.rating1);
		when(ratingsRepo.findAllRatings("00123")).thenReturn(ratings);
		mvc.perform(get("/api/getallratings/00123"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(
				this.objectMapper.writeValueAsString(ratings)));
	}

	/**
	 * To test the method findAllReviews
	 * @throws Exception
	 */
	@Test
	public void findAllReviewsTest() throws Exception {
		List<String> reviews = Arrays.asList("i am speed.");
		when(ratingsRepo.findAllReviews("00123")).thenReturn(reviews);
		mvc.perform(get("/api/getallreviews/00123"))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(
				this.objectMapper.writeValueAsString(reviews)));
	}

	/**
	 * To test the method updateMovieRating throws an exception if the rating does not already exist
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void updateMovieRatingTest0() throws Exception {
		RatingDummy rd = new RatingDummy("cars", "00123", 12.0, null);
		when(movieRepo.findById("920")).thenReturn(this.movie3);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(this.movieRepo).save(this.movie3);
		when(ratingsRepo.findUserRating("e.trinh", "00123")).thenReturn(null);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(this.ratingsRepo).save(any(Rating.class));
		mvc.perform(post("/api/rate")
				.content(this.objectMapper.writeValueAsString(rd))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test that the method updateMovieRating throws an exception if the rating is above 10
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void updateMovieRatingTest1() throws Exception {
		RatingDummy rd = new RatingDummy("cars", "00123", 12.0, "ka chow");
		mvc.perform(post("/api/rate")
				.content(this.objectMapper.writeValueAsString(rd))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test that the method updateMovieRating throws an exception if the rating is below 0
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void updateMovieRatingTest2() throws Exception {
		RatingDummy rd = new RatingDummy("cars", "00123", -5, "ka chow");
		mvc.perform(post("/api/rate")
				.content(this.objectMapper.writeValueAsString(rd))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test that the method updateMoieRating throws an exception if the movie doesn't exist
	 * @throws Exception
	 */
	@Test(expected = NestedServletException.class)
	public void updateMovieRatingTest3() throws Exception {
		when(movieRepo.findById("920")).thenReturn(this.movie3);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(this.movieRepo).save(this.movie3);
		when(ratingsRepo.findUserRating("e.trinh", "00123")).thenReturn(null);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(this.ratingsRepo).save(any(Rating.class));
		RatingDummy rd = new RatingDummy("cars", "00123", 9.0, "ka chow");
		mvc.perform(post("/api/rate")
				.content(this.objectMapper.writeValueAsString(rd))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}

	/**
	 * To test the method updateMovieRating 
	 * @throws Exception
	 */
	@Test
	public void updateMovieRatingTest4() throws Exception {
		List<Rating> ratings = Arrays.asList(this.rating1);
		when(this.movieRepo.findById("00123")).thenReturn(this.movie1);
		when(ratingsRepo.findUserRating("e.trinh", "00123")).thenReturn(this.rating1);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				Rating r = ratings.get(0);
				r.setRating(9.0);
				ratings.set(0, r);
				return null;
			}
		}).when(ratingsRepo).updateRating("e.trinh", "00123", 9.0, "i am speed.");
		when(movieRepo.getUserRating("00123")).thenReturn(9.0);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(movieRepo).setRating("00123", 9.0);
		RatingDummy rd = new RatingDummy("cars", "00123", 9.0, "ka chow");
		mvc.perform(post("/api/rate")
				.content(this.objectMapper.writeValueAsString(rd))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	/**
	 * To test the method updateMovieRating throws and exception when incorrect.
	 * @throws Exception
	 */
	@Test
	public void updateMovieRatingTest5() throws Exception {
		when(this.movieRepo.findById("00123")).thenReturn(this.movie1);
		when(ratingsRepo.findUserRating("e.trinh", "00123")).thenReturn(null);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(ratingsRepo).save(any(Rating.class));
		when(movieRepo.getUserRating("00123")).thenReturn(9.0);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(movieRepo).setRating("00123", 9.0);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(movieRepo).incrementCount("00123", 1);
		RatingDummy rd = new RatingDummy("cars", "00123", 9.0, "ka chow");
		mvc.perform(post("/api/rate")
				.content(this.objectMapper.writeValueAsString(rd))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	public void updateRatingTest6() throws Exception {
		when(this.movieRepo.findById("00123")).thenReturn(this.movie1);
		when(ratingsRepo.findUserRating("cars", "00123")).thenReturn(this.rating1);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(ratingsRepo).save(any(Rating.class));
		when(movieRepo.getUserRating("00123")).thenReturn(9.0);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(movieRepo).setRating("00123", 9.0);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(movieRepo).incrementCount("00123", 1);
		RatingDummy rd = new RatingDummy("cars", "00123", 9.0, "ka chow");
		mvc.perform(post("/api/rate")
				.content(this.objectMapper.writeValueAsString(rd))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	public void updateRatingTest7() throws Exception {
		Rating r = new Rating("e.trinh", "00123", 10.0, null);
		when(this.movieRepo.findById("00123")).thenReturn(this.movie1);
		when(ratingsRepo.findUserRating("cars", "00123")).thenReturn(r);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(ratingsRepo).save(any(Rating.class));
		when(movieRepo.getUserRating("00123")).thenReturn(9.0);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(movieRepo).setRating("00123", 9.0);
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
				return null;
			}
		}).when(movieRepo).incrementCount("00123", 1);
		RatingDummy rd = new RatingDummy("cars", "00123", 9.0, "ka chow");
		mvc.perform(post("/api/rate")
				.content(this.objectMapper.writeValueAsString(rd))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	/**
	 * To test the method getMovieControllerInstance.
	 */
	@Test
	public void getMovieControllerInstanceTest() {
		MovieController m = MovieController.getMovieControllerInstance();
		assertEquals(m, MovieController.getMovieControllerInstance());
	}

	/**
	 * To test the method findTopRated to find the top rated movies.
	 * @throws Exception
	 */
	@Test
	public void findTopRatedTest() throws Exception {
		when(movieRepo.topRated()).thenReturn(Arrays.asList(this.movie1));
		mvc.perform(get("/api/movie/topRated"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].title", is("cars")));
	}

	/**
	 * To test the method findTopRated(Genre) to find the top rated movies in the given genre.
	 * @throws Exception
	 */
	@Test
	public void findTopRatedGenreTest() throws Exception {
		when(movieRepo.searchTopGenre("Action")).thenReturn(Arrays.asList(this.movie1));
		mvc.perform(get("/api/movie/topRatedGenre/Action"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].title", is("cars")));
	}

	@Test
	public void findMostRatedTest() throws Exception {
		when(movieRepo.mostRated()).thenReturn(Arrays.asList(this.movie1));
		mvc.perform(get("/api/movie/mostRated"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].title", is("cars")));
	}

}
