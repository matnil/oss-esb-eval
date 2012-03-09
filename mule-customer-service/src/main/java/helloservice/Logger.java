package helloservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;
import org.mule.component.simple.LogComponent;

public class Logger implements Callable {

	Log log = LogFactory.getLog(getClass()); 
	
	@Override
	public Object onCall(MuleEventContext ctxt) throws Exception {
		MuleMessage m = ctxt.getMessage();
		log.info("message: " + m.getPayloadAsString());
		/*
		log.info("inbound properties");
		for (String prop : m.getPropertyNames(PropertyScope.INBOUND)) {
			log.info(prop + "=" + m.getProperty(prop, PropertyScope.INBOUND));			
		}
		log.info("invocation properties");
		for (String prop : m.getPropertyNames(PropertyScope.INVOCATION)) {
			log.info(prop + "=" + m.getProperty(prop, PropertyScope.INVOCATION));			
		}
		log.info("message: " + m.getPayloadAsString());
		 */
		return m;
	}

}
