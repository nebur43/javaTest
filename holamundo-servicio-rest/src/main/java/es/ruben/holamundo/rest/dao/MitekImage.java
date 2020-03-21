package es.ruben.holamundo.rest.dao;

import java.util.List;
import java.util.Map;

public class MitekImage {
	
	// en request y response
	private String data;
	private String customerReferenceId;
	private MitekEncodedData encodedData;
	
	// solo en response
	private String imageId;
	private MitekExtractedData extractedData;
	private String processingStatus;
	private MitekClassification classification;
	private List derivedImages; 
	private Map<String,String> processingReasons;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCustomerReferenceId() {
		return customerReferenceId;
	}

	public void setCustomerReferenceId(String customerReferenceId) {
		this.customerReferenceId = customerReferenceId;
	}

	public MitekEncodedData getEncodedData() {
		return encodedData;
	}

	public void setEncodedData(MitekEncodedData encodedData) {
		this.encodedData = encodedData;
	}
	
	

}
