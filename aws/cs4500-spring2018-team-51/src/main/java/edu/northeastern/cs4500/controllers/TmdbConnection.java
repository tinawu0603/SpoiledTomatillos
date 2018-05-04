package edu.northeastern.cs4500.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import edu.northeastern.cs4500.log4j2.Log;


/**
* The TmbdbConnection class is a class that scrubs information from the TMDB api.
* 
*
* @author  Patrick Milne
* @version 1.0
* @since   2018-04-13 
*/
@ControllerAdvice
@CrossOrigin
public class TmdbConnection {
	/**
	* SEARCH_URL is the api url to search the TMDB api for movies based on title.
	*/
	public static final String SEARCH_URL = 
			"https://api.themoviedb.org/3/search/movie?api_key=25f03d330cee4cd2cad1edd6107994fd&language=en-US&query=TITLE&include_adult=false";

	/**
	* SEARCH_ID_URL is a the api url to search the TMDB api for movies based on id.
	*/
	public static final String SEARCH_ID_URL = 
			"https://api.themoviedb.org/3/movie/ID?api_key=25f03d330cee4cd2cad1edd6107994fd&language=en-US";
	
	/**
	* RECOMMEND_URL is the api url to get a list of the popular movies of a genre from the TMDB api.
	*/
	public static final String RECOMMEND_URL = 
			"https://api.themoviedb.org/3/discover/movie?api_key=25f03d330cee4cd2cad1edd6107994fd&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=PGE&vote_count.gte=25&vote_average.gte=7.5&with_genres=GENRE2";

	
	 /**
	   * This static function runs the get request from requestUrl and gets the results as a String. Throws an error if the URL is malformed or if
	   * there was an exception in sending the get request.
	   * @param requestUrl is one of the above URL's modified based on the search criteria.
	   * @return String This returns a string of the response from the TMDB api. It is in JSON form.
	   */
	public static String sendGetRequest(String requestUrl) {
		StringBuffer response = new StringBuffer();

		try {
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "*/*");
			connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
			InputStream stream = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(stream);
			BufferedReader buffer = new BufferedReader(reader);
			String line;
			while((line = buffer.readLine()) != null) {
				response.append(line);
			}
			buffer.close();
			connection.disconnect();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Log.reportError("URL is malformed in Get request in Tmdb Connection.");
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.reportError("IO Exception in sending Get request in Tmdb Connection.");
			e.printStackTrace();
		}
		return response.toString();
	}

	 /**
	   * This function finds all the movies in the TMDB database that have a similar title to the parameter title.
	   * @param title is the title being searched for.
	   * @return String This returns a String of the JSON that is returned from the TMDB api.
	   */
	public static String searchMovieByTitle(String title) {
		try {
			title = URLEncoder.encode(title,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.reportError("Unsupported encoding exception when searching movie by title viq get request.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String requestUrl = SEARCH_URL.replaceAll("TITLE", title);
		return sendGetRequest(requestUrl);
	}

	 /**
	   * This function find the movie in the TMDB database that has the parameter id.
	   * @param id is the id of the movie being searched for.
	   * @return String This returns a String of the JSON that is returned from the TMDB api.
	   */
	public static String searchMovieById(String id) {
		try {
			id = URLEncoder.encode(id,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.reportError("Unsupported encoding exception when searching movie by ID in get request.");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String requestUrl = SEARCH_ID_URL.replaceAll("ID", id);
		return sendGetRequest(requestUrl);
	}
	
	 /**
	   * This function finds a list of the most popular movies in the TMBD database based on the genre.
	   * @param gen is the genre id that is being searched for.
	   * @param pge is the what page of the results from the TMDB database is requested.
	   * @return String This returns a String of the JSON that is returned from the TMDB api.
	   */
   	public static String searchTopGenre(int gen, int pge) {
   		String genre = Integer.toString(gen);
   		String page = Integer.toString(pge);
   		try {
   			genre = URLEncoder.encode(genre,"UTF-8");
  
   		} catch (UnsupportedEncodingException e) {
   			Log.reportError("Unsupported encoding exception when searching movie by ID in get request.");
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   		String requestUrl = RECOMMEND_URL.replaceAll("GENRE2", genre);
   		requestUrl = requestUrl.replaceAll("PGE", page);
   		return sendGetRequest(requestUrl);
   	}

	public static void main(String[] args) {
	}
}