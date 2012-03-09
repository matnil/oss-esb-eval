package com.example.switchyard.customer_service;

import org.switchyard.annotations.OperationTypes;

public interface InToCanonical {

	@OperationTypes(in="{http://customerservice.example.com/}canonical", out="{http://customerservice.example.com/}canonical")
	String transform(String input);
	
}
