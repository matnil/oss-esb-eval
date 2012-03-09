package example.customer_service;

import org.apache.camel.LoggingLevel;
import org.apache.camel.processor.RedeliveryPolicy;
import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.camel.spring.spi.TransactionErrorHandlerBuilder;

public class CustomerServiceRouteBuilder extends SpringRouteBuilder {

	@Override
	public void configure() throws Exception {
		// let camel do redelivery first. if it fails the xa transaction will do an extra redelivery from jms persistent store (see connection factory config).
		TransactionErrorHandlerBuilder transactionErrorHandler = transactionErrorHandler();
		RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
		redeliveryPolicy.setRedeliveryDelay(10000);
		redeliveryPolicy.setMaximumRedeliveries(2);
		transactionErrorHandler.setRedeliveryPolicy(redeliveryPolicy);
		  
		errorHandler(transactionErrorHandler);
		
		// incoming flow
		from("cxf://http://localhost:9090/customerService"
		+ "?wsdlURL=classpath:CustomerService.wsdl"
		+ "&dataFormat=PAYLOAD")
		.log(LoggingLevel.INFO, "Incoming message: ${body}")
		.to("xslt:/in-to-canonical.xsl")
		.log(LoggingLevel.INFO, "Canonical message: ${body}")
		.process(new MyProcessor("incomingFlow"))
		.transacted("PROPAGATION_REQUIRES_NEW") // start a new transaction before sending to outgoing flow
		.inOnly("activemq:queue:customerQueue"); // sends without waiting for reply
		// outgoing flow
		from("activemq:queue:customerQueue")
		// run outgoing flow in a new transaction
		.transacted("PROPAGATION_REQUIRES_NEW")
		.process(new MyProcessor("outgoingFlowAfterTransacted"))
		.log(LoggingLevel.INFO, "Outgoing message: ${body}")
		.multicast().stopOnException().to("direct:route-to-jms", "direct:route-to-db")
		.end();
		// route to db
		from("direct:route-to-db")
		.process(new MyProcessor("routeToDbBeforeSplit"))
		.split(xpath("//customerList/customer")).stopOnException().shareUnitOfWork() //
		.process(new MyProcessor("routeToDbAfterSplit"))
		.setHeader("customerId", xpath("//customer/id/text()").stringResult())
		.setHeader("customerName", xpath("//customer/fullName/text()").stringResult())
		.setHeader("CamelVelocityTemplate").constant("INSERT INTO CUSTOMER (ID, NAME) VALUES (${headers.customerId}, '${headers.customerName}')")
		.to("velocity:dummy")
		.log(LoggingLevel.INFO, "JDBC statement: ${body}")
		.setHeader("CamelSqlQuery", body())
		.log(LoggingLevel.INFO, "SQL query: ${headers.CamelSqlQuery}")
		.process(new MyProcessor("routeToDbBeforeInsertSQL"))
		.to("sql:#?dataSourceRef=atomikosXaDataSourceBean")
		.end();
		// route to jms
		from("direct:route-to-jms")
		.process(new MyProcessor("routeToJMS"))
		.to("xslt:/canonical-to-csv.xsl")
		.log(LoggingLevel.INFO, "CSV message: ${body}")
		.inOnly("activemq:queue:csvQueue")
		.end();
	}

}
