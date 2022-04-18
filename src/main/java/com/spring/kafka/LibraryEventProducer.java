package com.spring.kafka;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import lombok.extern.slf4j.Slf4j;

// TODO: Auto-generated Javadoc
/**
 * The Class LibraryEventProducer.
 */
@Component
@Slf4j
public class LibraryEventProducer {

	
	/** The kafka template. */
	@Autowired
	KafkaTemplate<Integer,String> kafkaTemplate;
	
	/** The mapper. */
	@Autowired
	ObjectMapper mapper;
	
	/** The topic. */
	String topic="library-events";
	
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(LibraryEventProducer.class);
	
	
	/**
	 * Send library event.
	 *
	 * @param event the event
	 * @throws JsonProcessingException the json processing exception
	 */
	public void SendLibraryEvent(LibraryEvent event) throws JsonProcessingException
	{
		Integer key=event.getLibraryEventId();
		String value=mapper.writeValueAsString(event);
		
		ListenableFuture<SendResult<Integer,String>> listenableFuture=kafkaTemplate.sendDefault( key,value);
		
		
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer,String>>() {

			@Override
			public void onSuccess(SendResult<Integer, String> result) {
				// TODO Auto-generated method stub
				handleSuccess(key,value,result);
				
			}

			@Override
			public void onFailure(Throwable ex) {
				// TODO Auto-generated method stub
				handleFailure(key, value, ex);
				
			}
			
		});
	}
	
	/**
	 * Send library event asysn.
	 *
	 * @param event the event
	 * @throws JsonProcessingException the json processing exception
	 */
	public void SendLibraryEventAsysn(LibraryEvent event) throws JsonProcessingException
	{
		Integer key=event.getLibraryEventId();
		String value=mapper.writeValueAsString(event);
		
		ProducerRecord<Integer,String> producerRecord=buildProducerRecord(key,value,topic);
		
		ListenableFuture<SendResult<Integer,String>> listenableFuture=kafkaTemplate.send(producerRecord);
		
		
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer,String>>() {

			@Override
			public void onSuccess(SendResult<Integer, String> result) {
				// TODO Auto-generated method stub
				handleSuccess(key,value,result);
				
			}

			@Override
			public void onFailure(Throwable ex) {
				// TODO Auto-generated method stub
				handleFailure(key, value, ex);
				
			}
			
		});
	}
	
      /**
       * Builds the producer record.
       *
       * @param key the key
       * @param value the value
       * @param topic the topic
       * @return the producer record
       */
      ProducerRecord<Integer, String>	buildProducerRecord(Integer key,String value,String topic){
		return new ProducerRecord<Integer, String>(topic,null,key, value,null);
	}
	
	/**
	 * Send library events synchronously.
	 *
	 * @param event the event
	 * @return the send result
	 * @throws Exception the exception
	 */
	public SendResult<Integer, String> sendLibraryEventsSynchronously(LibraryEvent event) throws Exception
	{
		
		Integer key=event.getLibraryEventId();
		String value=mapper.writeValueAsString(event);
		SendResult<Integer,String> sendResult=null;
		try {
			sendResult=kafkaTemplate.sendDefault(key,value).get();
		} catch (InterruptedException | ExecutionException e) {
			logger.error(" InterruptedException | ExecutionException error sending  the message  and the exceptions  is {}",e.getMessage());
			// TODO Auto-generated catch block
		throw e;
		}
		catch(Exception e)
		{
			
			logger.error(" InterruptedException | ExecutionException error sending  the message  and the exceptions  is {}",e.getMessage());
		throw e;
		}
		
		return sendResult;
	}
	
	/**
	 * Handle failure.
	 *
	 * @param key the key
	 * @param value the value
	 * @param ex the ex
	 */
	private void handleFailure(Integer key,String value,Throwable ex)
	
{  
		logger.error("error sending  the message  and the exceptions  is {}",ex.getMessage());
		
		try
		{
			throw ex;
		}
		catch(Throwable throwable)
		{
			logger.error("error in on onFailure {}",throwable.getMessage());
		}
	
}
		
		/**
		 * Handle success.
		 *
		 * @param key the key
		 * @param value the value
		 * @param result the result
		 */
		private void 	handleSuccess(Integer key,String value,SendResult<Integer,String> result)
		{
			
		  logger.info("message sent successfully for the key : "
		  		+ "{} and the value :{} ,partitions is :{}",key,value,result.getProducerRecord().partition());	
		}
	
}

