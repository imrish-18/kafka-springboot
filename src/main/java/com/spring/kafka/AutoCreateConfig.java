package com.spring.kafka;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("local")
public class AutoCreateConfig {

	/**
	 * Library events.
	 *
	 * @return the new topic
	 */
	@Bean
	public NewTopic libraryEvents()
	{
	  return	TopicBuilder.name("library-events")
			  .partitions(3)
			  .replicas(1)
			  .build();
	}
}
