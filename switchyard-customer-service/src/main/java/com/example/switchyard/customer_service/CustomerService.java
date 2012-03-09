package com.example.switchyard.customer_service;

import org.switchyard.annotations.OperationTypes;

public interface CustomerService {

	@OperationTypes(in="{http://customerservice.example.com/}updateCustomer", out="{http://customerservice.example.com/}updateCustomer")
	String updateCustomer(String[] input);
	
}
