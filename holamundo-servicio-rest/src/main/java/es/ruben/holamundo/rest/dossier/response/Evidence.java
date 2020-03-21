
package es.ruben.holamundo.rest.dossier.response;

import java.util.List;

public class Evidence {

    private String type;
    private List<Image> images = null;
    private String evidenceId;
    private ExtractedData extractedData;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getEvidenceId() {
        return evidenceId;
    }

    public void setEvidenceId(String evidenceId) {
        this.evidenceId = evidenceId;
    }

    public ExtractedData getExtractedData() {
        return extractedData;
    }

    public void setExtractedData(ExtractedData extractedData) {
        this.extractedData = extractedData;
    }

}
