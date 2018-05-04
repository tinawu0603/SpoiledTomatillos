package edu.northeastern.cs4500.objects;

import edu.northeastern.cs4500.objects.Genre;

/**
 * GenreObject that represents the genre of a movie. Used to compare genres to each other to 
 * determine which genre’s are most popular
 * @author emilytrinh
 *
 */
public class GenreObject implements Comparable<GenreObject>{
	private Genre genre;
	private int count;
	private double average;
	
	/**
	 * Constructor of a GenreObject which has a genre, count, and average.
	 * @param genre input genre 
	 */
	public GenreObject(Genre genre) {
		this.genre = genre;
		this.count = 0;
		this.average = 0;
	}
	
	/**
	 * Method that adds a rating to the count of the GenreObject. 
	 * @param rating rating that will be added to the GenreObject
	 */
	public void addRating(double rating) {
		count = count + 1;
		average = (average * ((double)count - 1) / (double)count) + (rating * 1 / (double)count);
	}
	
	/**
	 * Method that returns the weighted average of the ratings and how many ratings there are. 
	 * @return double the represents the weighted average of the ratings and how many ratings there are. 
	 */
	public double getWeightedAverage() {
		return average + ((double)count * .1);
	}
	
	/**
	 * Method that returns the genre related to the genre object.
	 * @return genre of the genre object
	 */
	public Genre getGenre() {
		return genre;
	}
	
	/**
	 * Method that compares two genre objects, returns -1 if the weighted average of the given genre object is greater than
	 * this genre object. Returns 0 if the two genre objects have the same weighted average. Otherwise, returns 1. 
	 */
	@Override
	public int compareTo(GenreObject go) {
		if (go.getWeightedAverage() > this.getWeightedAverage()) {
			return -1;
		}
		else if (go.getWeightedAverage() == this.getWeightedAverage()) {
			return 0;
		}
		else {
			return 1;
		}
	}
	
	/**
	 * Method that checks if two genre objects are equal, if so returns true. Otherwise, returns false. 
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof GenreObject)) {
			return false;
		}
		GenreObject g = (GenreObject)o;

		return(g.getGenre() == this.getGenre());
	}
	
	/**
	 * Overrides HashCode
	 */
	 @Override
	    public int hashCode() {
	        final int PRIME = 31;
	        int result = 1;
	        result = PRIME * result
	                + ((this.genre == null) ? 0 : this.genre.hashCode()) +
	               this.count + (int)this.average + (int)this.getWeightedAverage();
	        return result;
	    }
	
}
