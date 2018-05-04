package edu.northeastern.cs4500.objects;



/**
 * Genre is an enum class that represents all of the genres that the TMDB data base contains. 
 * @author emilytrinh
 *
 */
public enum Genre {
	ACTION, ADVENTURE, ANIMATION, COMEDY, CRIME, DOCUMENTARY, DRAMA, FAMILY, FANTASY, 
	HISTORY, HORROR, MUSIC, MYSTERY, ROMANCE, SCIENCEFICTION, TVMOVIE, THRILLER, WAR, WESTERN;
	
	/**
	 * Method that returns the genres of the movies as strings. 
	 */
	@Override
	public String toString() {
		switch(this) {
		case ACTION:
			return "Action";
		case ADVENTURE:
			return "Adventure";
		case ANIMATION:
			return "Animation";
		case COMEDY:
			return "Comedy";
		case CRIME:
			return "Crime";
		case DOCUMENTARY:
			return "Documentary";
		case DRAMA:
			return "Drama";
		case FAMILY:
			return "Family";
		case FANTASY:
			return "Fantasy";
		case HISTORY:
			return "History";
		case HORROR:
			return "Horror";
		case MUSIC:
			return "Music";
		case MYSTERY:
			return "Mystery";
		case ROMANCE:
			return "Romance";
		case SCIENCEFICTION:
			return "Science Fiction";
		case TVMOVIE:
			return "TV Movie";
		case THRILLER:
			return "Thriller";
		case WAR:
			return "War";
		default :
			return "Western";
		}
	}
	
	/**
	 * Method that returns the int that represents a specific genre.
	 * @return int that corresponds to a specific genre. 
	 */
	public int getGenreInt() {
		switch(this) {
		case ACTION:
			return 28;
		case ADVENTURE:
			return 12;
		case ANIMATION:
			return 16;
		case COMEDY:
			return 35;
		case CRIME:
			return 80;
		case DOCUMENTARY:
			return 99;
		case DRAMA:
			return 18;
		case FAMILY:
			return 10751;
		case FANTASY:
			return 14;
		case HISTORY:
			return 36;
		case HORROR:
			return 27;
		case MUSIC:
			return 10402;
		case MYSTERY:
			return 9648;
		case ROMANCE:
			return 10749;
		case SCIENCEFICTION:
			return 878;
		case TVMOVIE:
			return 10770;
		case THRILLER:
			return 53;
		case WAR:
			return 10752;
		default :
			return 37;
		}
	}
	
	/**
	 * Method that returns the genre that corresponds to the given string
	 * @param x string that represents a genre 
	 * @return genre that corresponds to the input string 
	 */
	public static Genre setGenreString(String x) {

		String lower = x.toLowerCase();
		switch(lower) {

		case "action":
			return ACTION;
		case "adventure":
			return ADVENTURE;
		case "animation":
			return ANIMATION;
		case "comedy":
			return COMEDY;
		case "crime":
			return CRIME;
		case "documentary":
			return DOCUMENTARY;
		case "drama":
			return DRAMA;
		case "family":
			return FAMILY;
		case "fantasy":
			return FANTASY;
		case "history":
			return HISTORY;
		case "horror":
			return HORROR;
		case "music":
			return MUSIC;
		case "mystery":
			return MYSTERY;
		case "romance":
			return ROMANCE;
		case "sciencefiction":  
			return SCIENCEFICTION;
		case "science fiction":
			return SCIENCEFICTION;
		case "tvmovie":
			return TVMOVIE;
		case "tv movie":
			return TVMOVIE; 
		case "thriller":
			return THRILLER;
		case "war":
			return WAR;
		default :
			return WESTERN;
		}
	}
	
	/**
	 * Method that returns the genre that corresponds to the given number
	 * @param x int that corresponds to a specific genre
	 * @return genre that corresponds to the input int 
	 */
	public static Genre setGenreInt(int x) {
		switch(x) {
		case 28:
			return ACTION;
		case 12:
			return ADVENTURE;
		case 16:
			return ANIMATION;
		case 35:
			return COMEDY;
		case 80:
			return CRIME;
		case 99:
			return DOCUMENTARY;
		case 18:
			return DRAMA;
		case 10751:
			return FAMILY;
		case 14:
			return FANTASY;
		case 36:
			return HISTORY;
		case 27:
			return HORROR;
		case 10402:
			return MUSIC;
		case 9648:
			return MYSTERY;
		case 10749:
			return ROMANCE;
		case 878:
			return SCIENCEFICTION;
		case 10770:
			return TVMOVIE;
		case 53:
			return THRILLER;
		case 10752:
			return WAR;
		default :
			return WESTERN;
		}
	}
	
}