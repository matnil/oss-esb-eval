package com.example.switchyard.customer_service;

import javax.inject.Inject;

import org.switchyard.Context;
import org.switchyard.Property;
import org.switchyard.component.bean.Service;

@Service(InToCanonical.class)
public class InToCanonicalBean implements InToCanonical {

    @Inject
    private Context context;
    
	@Override
	public String transform(String input) {
		System.out.println("InToCanonicalBean got context: ");
		for (Property p : context.getProperties()) {
			System.out.println("  " + p.getName() + " = " + p.getValue());			
		}
		System.out.println("input: " + input);
		return input;
	}

}
