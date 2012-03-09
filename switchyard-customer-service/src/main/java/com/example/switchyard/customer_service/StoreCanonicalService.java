package com.example.switchyard.customer_service;

import org.switchyard.annotations.OperationTypes;

public interface StoreCanonicalService {

	@OperationTypes(in="{http://customerservice.example.com/}canonical")
	void storeCanonicalMessage(String input);
	
}
