package com.example.switchyard.customer_service;

import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.switchyard.transform.BaseTransformer;

public class HornetQTransformer extends BaseTransformer {
	
	public HornetQTransformer() {
		super();
	}

	public HornetQTransformer(QName from, QName to) {
		super(from, to);
	}

	@Override
	public Object transform(Object input) {
		String to = getTo().toString();
	    String inputAsString = null;
	    if (input instanceof DOMSource) {
	        StringWriter writer = new StringWriter();
	        StreamResult result = new StreamResult(writer);
	        TransformerFactory tf = TransformerFactory.newInstance();
	        try {
		        Transformer transformer = tf.newTransformer();
				transformer.transform((DOMSource)input, result);
			} catch (TransformerException e) {
				e.printStackTrace();
			}
	        inputAsString = writer.toString();
	    }
	    if (inputAsString != null) {
		    if (to.equals("java:byte[]")) {
		    	return inputAsString.getBytes();
		    }
	    }
	    throw new IllegalArgumentException("Cannot convert input [" + input + "] (to = " + getTo() + ", toType = " + getToType() + ")");
	}
	
}
