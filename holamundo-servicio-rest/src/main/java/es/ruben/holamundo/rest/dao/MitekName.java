package es.ruben.holamundo.rest.dao;

import java.util.Map;

public class MitekName {

	private String givenNames;
	private String surname;
	private String fullName;
	private Map<String,String> dynamicProperties;
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
	public Map<String, String> getDynamicProperties() {
		return dynamicProperties;
	}
	public void setDynamicProperties(Map<String, String> dynamicProperties) {
		this.dynamicProperties = dynamicProperties;
	}
	
	
	
}
