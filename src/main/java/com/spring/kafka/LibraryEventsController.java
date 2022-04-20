package com.spring.kafka;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

// TODO: Auto-generated Javadoc
/**
 * The Class LibraryEventsController.
 */
@RestController
public class LibraryEventsController {
	
	/** The producer. */
	@Autowired
	LibraryEventProducer producer;
	
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(LibraryEventsController.class);

	/**
	 * Show.
	 *
	 * @return the string
	 */
	@GetMapping("/path")
	public String show()
	{
		
		return "hello";
	}
	
	/**
	 * Post library event.
	 *
	 * @param libraryEvent the library event
	 * @return the response entity
	 * @throws JsonProcessingException the json processing exception
	 */
	@PostMapping("/v1/libraryevent")
	public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody @Valid LibraryEvent libraryEvent) throws JsonProcessingException
	{
		logger.info("before sendLibraryEvent");
		
		 producer.SendLibraryEvent(libraryEvent);
		 logger.info("after sendLibraryEvent");
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(libraryEvent);
	}
	
	/**
	 * Post library event aysn.
	 *
	 * @param libraryEvent the library event
	 * @return the response entity
	 * @throws JsonProcessingException the json processing exception
	 */
	@PostMapping("/v1/libraryeventAsyn")
	public ResponseEntity<LibraryEvent> postLibraryEventAysn(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException
	{
		logger.info("before sendLibraryEvent");
		
		 producer.SendLibraryEventAsysn(libraryEvent);
		 logger.info("after sendLibraryEvent");
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(libraryEvent);
	}
	
	/**
	 * Post library event sysnchronously.
	 *
	 * @param libraryEvent the library event
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PostMapping("/v1/libraryevenstSyn")
	public ResponseEntity<LibraryEvent> postLibraryEventSysnchronously(@RequestBody LibraryEvent libraryEvent) throws Exception
	{
		logger.info("before sendLibraryEvent");
		
		SendResult<Integer,String> sendResult= producer.sendLibraryEventsSynchronously(libraryEvent);
		logger.info("sendResult is {}  ",sendResult.toString() );
		 logger.info("after sendLibraryEvent");
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(libraryEvent);
	}
	
	@PutMapping("/v1/libraryevent/update")
	public ResponseEntity<?> putLibraryEvent(@RequestBody @Valid LibraryEvent libraryEvent) throws JsonProcessingException
	{
		logger.info("before sendLibraryEvent");
		if(libraryEvent.getLibraryEventId()==null)
		{
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please passs the libraryEventId");
			
		}
		
		 producer.SendLibraryEvent(libraryEvent);
		 logger.info("after sendLibraryEvent");
		return ResponseEntity.status(HttpStatus.OK)
				.body(libraryEvent);
	}
	
}
