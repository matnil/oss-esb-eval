package com.example.switchyard.customer_service;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.switchyard.component.camel.Route;

@Route(CustomerService.class)
public class CustomerServiceRouteBuilder extends RouteBuilder {
		
	@Override
	public void configure() throws Exception {
        from("switchyard://CustomerService")
		.log(LoggingLevel.INFO, "Incoming message: ${body}")
		//.to("switchyard://InToCanonical") // wanted this call to CDI bean to trigger a transform. but camel (SwitchYardProducer) always sets the invoker input type to the same as the CDI bean input type, so no transform ever happens.  
		.inOnly("switchyard://CustomerQueue");
		/* cannot have more than one flow per route builder in SwitchYard
		// outgoing flow
		from("jms:queue:customerQueue")
		.to("xslt:/in-to-canonical.xsl") //  xslt component not included in SwitchYard AS7
		// run outgoing flow in a new transaction
		.transacted("PROPAGATION_REQUIRES_NEW")
		.log(LoggingLevel.INFO, "Outgoing message: ${body}")
		.multicast().stopOnException().to("direct:route-to-jms", "direct:route-to-db")
		.end();
		// route to db
		from("direct:route-to-db")
		.split(xpath("//customerList/customer")).stopOnException().shareUnitOfWork() //
		.setHeader("customerId", xpath("//customer/id/text()").stringResult())
		.setHeader("customerName", xpath("//customer/fullName/text()").stringResult())
		.setHeader("CamelVelocityTemplate").constant("INSERT INTO CUSTOMER (ID, NAME) VALUES (${headers.customerId}, '${headers.customerName}')")
		.to("velocity:dummy")
		.log(LoggingLevel.INFO, "JDBC statement: ${body}")
		.setHeader("CamelSqlQuery", body())
		.log(LoggingLevel.INFO, "SQL query: ${headers.CamelSqlQuery}")
		.to("sql:#?dataSourceRef=PostgresXADS")
		.end();
		// route to jms
		from("direct:route-to-jms")
		.to("xslt:/canonical-to-csv.xsl")
		.log(LoggingLevel.INFO, "CSV message: ${body}")
		.inOnly("jms:queue:csvQueue")
		.end();
		*/
        
	}

}
