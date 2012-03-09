package com.example.switchyard.customer_service;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.switchyard.component.camel.Route;

@Route(StoreCanonicalService.class)
public class StoreCanonicalServiceRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("switchyard://StoreCanonicalService")
		.log(LoggingLevel.INFO, "Store canonical received message: ${body}")
		//.to("switchyard://InToCanonical") // cannot call CDI bean here due to https://issues.jboss.org/browse/SWITCHYARD-626
		//.to("xslt:/in-to-canonical.xsl") // could use this if we install Camel xslt component as a module in JBoss AS 7 (https://community.jboss.org/wiki/SwitchYardAS7CamelIntegration)
		.log(LoggingLevel.INFO, "Canonical message: ${body}")
		//.inOnly("switchyard://CanonicalCustomerQueue"); // sends to JMS queue without waiting for reply
		
		.split(xpath("//customer")).stopOnException().shareUnitOfWork()
		.setHeader("customerId", xpath("//customer/customerId/text()").stringResult())
		.setHeader("customerName", xpath("//customer/name/text()").stringResult())
		.setHeader("CamelSqlQuery", simple("INSERT INTO CUSTOMER (ID, NAME) VALUES (${header.customerId}, '${header.customerName}')"))
		.log(LoggingLevel.INFO, "SQL query: ${headers.CamelSqlQuery}")
		//.to("sql:#?dataSourceRef=PostgresXADS"); // sql component not installed in switchyard as7
		.end();

	}

}
