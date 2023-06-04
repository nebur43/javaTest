
package es.ruben.holamundo.rest.dossier.response;

import java.util.HashMap;
import java.util.Map;

public class ExtractedData {

    private Name name;
    private Address address;
    private String documentNumber;
    private String dateOfExpiry;
    private String dateOfBirth;
    private String dateOfIssue;
    private Map<String, Object> dynamicProperties = new HashMap<String, Object>();

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDateOfExpiry() {
        return dateOfExpiry;
    }

    public void setDateOfExpiry(String dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }
    
    public Map<String, Object> getDynamicProperties() {
    	return this.dynamicProperties;
    }

    public void setDynamicProperties(String name, Object value) {
    	this.dynamicProperties.put(name, value);
    }


}
