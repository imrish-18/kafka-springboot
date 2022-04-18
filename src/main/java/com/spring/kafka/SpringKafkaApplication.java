package com.spring.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO: Auto-generated Javadoc
/**
 * The Class SpringKafkaApplication.
 */
@SpringBootApplication
public class SpringKafkaApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaApplication.class, args);
		
		System.out.println("hello ... welcome to kafka streams");
	}

}
