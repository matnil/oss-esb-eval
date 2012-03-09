package helloservice;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;
import org.mule.component.simple.LogComponent;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * This could be refactored into a XSLT transform instead of custom java code...
 * 
 * @author mattias
 */
public class CSVProducer implements Callable {

	Log log = LogFactory.getLog(getClass()); 
	
	@Override
	public Object onCall(MuleEventContext ctxt) throws Exception {
		MuleMessage m = ctxt.getMessage();
		String payload = m.getPayloadAsString();
		m.setOutboundProperty("originalPayload", payload);
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression exprId = xpath.compile("//id");
		XPathExpression exprFullName = xpath.compile("//fullName");
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(false);
		DocumentBuilder docBuilder = domFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new ByteArrayInputStream(payload.getBytes()));
		NodeList ids = (NodeList)exprId.evaluate(doc, XPathConstants.NODESET);
		NodeList fullNames = (NodeList)exprFullName.evaluate(doc, XPathConstants.NODESET);
		String result = "";
		for (int i=0;i<ids.getLength(); i++) {
			result += ids.item(i).getTextContent() + "," + fullNames.item(i).getTextContent() + "\n";
		}
		m.setPayload(result);
		return m;
	}

}
