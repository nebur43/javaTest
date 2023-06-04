package es.ruben.holamundo.rest.dao;

import java.util.List;

public class MitekConfiguration {
	
	public static final String RESPONSE_IMAGE_PORTAIT = "CroppedPortait";
	public static final String RESPONSE_IMAGE_SIGNATURE = "CroppedSignature";
	public static final String RESPONSE_IMAGE_DOCUMENT = "CroppedDocument";
	
	private MitekVerifications verifications;
	
	private List<String> responseImages;

	public MitekVerifications getVerifications() {
		return verifications;
	}

	public void setVerifications(MitekVerifications verifications) {
		this.verifications = verifications;
	}

	public List<String> getResponseImages() {
		return responseImages;
	}

	public void setResponseImages(List<String> responseImages) {
		this.responseImages = responseImages;
	}

	
	
}
