package es.ruben.holamundo.rest.dao;

import java.util.List;

public class MitekResponseDossier {
	
	private String type;
	private List<MitekImage> images;
	private String evidenceId;
	private MitekExtractedData extractedData;
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
	public String getEvidenceId() {
		return evidenceId;
	}
	public void setEvidenceId(String evidenceId) {
		this.evidenceId = evidenceId;
	}
	public MitekExtractedData getExtractedData() {
		return extractedData;
	}
	public void setExtractedData(MitekExtractedData extractedData) {
		this.extractedData = extractedData;
	}
	
	
	

}
