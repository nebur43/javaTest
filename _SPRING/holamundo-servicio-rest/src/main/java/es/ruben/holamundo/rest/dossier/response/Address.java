
package es.ruben.holamundo.rest.dossier.response;

import java.util.HashMap;
import java.util.Map;

public class Address {

    private String addressLine1;
    private String country;
    private String city;
    private String stateProvince;
    private String postalCode;
    private Map<String, Object> dynamicProperties = new HashMap<String, Object>();

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Map<String, Object> getDynamicProperties() {
    	return this.dynamicProperties;
    }

    public void setDynamicProperties(String name, Object value) {
    	this.dynamicProperties.put(name, value);
    }

}
