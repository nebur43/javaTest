package es.ruben.holamundo.rest.dao;

import java.util.Map;

public class MitekExtractedData {

	private MitekName name;
	private MitekAddress address;
	private String documentNumber;
	private String dateOfExpiry;
	private String dateOfBirth;
	private String dateOfIssue;
	private Map<String,String> dynamicProperties;
	public MitekName getName() {
		return name;
	}
	public void setName(MitekName name) {
		this.name = name;
	}
	public MitekAddress getAddress() {
		return address;
	}
	public void setAddress(MitekAddress address) {
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
	public Map<String, String> getDynamicProperties() {
		return dynamicProperties;
	}
	public void setDynamicProperties(Map<String, String> dynamicProperties) {
		this.dynamicProperties = dynamicProperties;
	}
	
	
	
}
