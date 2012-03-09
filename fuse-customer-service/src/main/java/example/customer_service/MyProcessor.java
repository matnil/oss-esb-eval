package example.customer_service;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyProcessor implements Processor {
	
	Logger logger = LoggerFactory.getLogger(MyProcessor.class);
	
	private String name;
	
	public MyProcessor(String name) {
		this.name= name; 
	}
	
	public void process(Exchange exch) throws Exception {
		logger.info("MyProcessor " + name + " invoked with exchange: " + exch.toString());
	}

}
