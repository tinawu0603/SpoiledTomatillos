package edu.northeastern.cs4500;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
* The Cs4500Spring2018Tem51Application class runs the entire spring application. 
* 
* @author  Emily Trinh
* @version 1.0
* @since   2018-04-13 
*/
@SpringBootApplication
public class Cs4500Spring2018Team51Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Cs4500Spring2018Team51Application.class, args);
	}
	
	
}
