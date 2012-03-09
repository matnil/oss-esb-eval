
package helloservice.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the helloservice.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UpdateCustomer_QNAME = new QName("http://customerservice.example.com/", "updateCustomer");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: helloservice.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Customer }
     * 
     */
    public Customer createCustomer() {
        return new Customer();
    }

    /**
     * Create an instance of {@link UpdateCustomer }
     * 
     */
    public UpdateCustomer createUpdateCustomer() {
        return new UpdateCustomer();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCustomer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://customerservice.example.com/", name = "updateCustomer")
    public JAXBElement<UpdateCustomer> createUpdateCustomer(UpdateCustomer value) {
        return new JAXBElement<UpdateCustomer>(_UpdateCustomer_QNAME, UpdateCustomer.class, null, value);
    }

}
