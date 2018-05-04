package edu.northeastern.cs4500;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * The ServletInitializer class configures the SpringApplicationBuilder class and returns the application's sources.
 * @author emilytrinh
 *
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Cs4500Spring2018Team51Application.class);
	}

}
