
package es.ruben.holamundo.rest.dossier.response;

import java.util.HashMap;
import java.util.Map;

public class Name {

    private String givenNames;
    private String surname;
    private String fullName;
    private Map<String, Object> dynamicProperties = new HashMap<String, Object>();

    public String getGivenNames() {
        return givenNames;
    }

    public void setGivenNames(String givenNames) {
        this.givenNames = givenNames;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Map<String, Object> getDynamicProperties() {
    	return this.dynamicProperties;
    }

    public void setDynamicProperties(String name, Object value) {
    	this.dynamicProperties.put(name, value);
    }

}
