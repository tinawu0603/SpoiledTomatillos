package edu.northeastern.cs4500.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* The Log class creates a logger that reports the errors and events that occur in the application. 
* 
*
* @author  Emily Trinh
* @version 1.0
* @since   2018-04-13 
*/
@SpringBootApplication
public class  Log {
	


	/**
	 * Static logger that is used in all other classes to report events or errors. 
	 */
	private static final Logger logger = LogManager.getLogger(Log.class);

	/**
	 * reportError reports the errors and prints a specific message that details what the error is related to. 
	 * @param message: error message that gives details about the error that occurred
	 */
	public static void reportError(String message) {
		logger.error(message);
	}

	/**
	 * reportEvent reports events that the application executes and prints a specific message that details what the event 
	 * is related to. 
	 * @param message: event message that gives details about the event that occurred
	 */
	public static void reportEvent(String message) {
		logger.info(message);
	}
}

