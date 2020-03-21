package es.ruben.holamundo.rest.dao;

import java.util.List;

public class MitekEvidence {

	// en request y response
	private String type;
	private List<MitekImage> images;
	
	// solo en respuestas
	private String evidenceId;
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<MitekImage> getImages() {
		return images;
	}

	public void setImages(List<MitekImage> images) {
		this.images = images;
	}
	
	
	
}
