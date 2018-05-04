package edu.northeastern.cs4500.objects;

/**
 * Personalized exception class for spoiled tomatillos
 * @author emilytrinh
 *
 */
public class SpoiledTomatillosException extends Exception {

	/**
	 * Parameterless constructor
	 */
	public SpoiledTomatillosException() {}


	/**
	 * Constructor that accepts a message
	 * @param message
	 */
	public SpoiledTomatillosException(String message)
	{
		super(message);
	}
}
