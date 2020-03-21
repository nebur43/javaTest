
package es.ruben.holamundo.rest.dossier.response;

import java.util.HashMap;
import java.util.Map;

public class Image {

    private String imageId;
    private String customerReferenceId;
    private ExtractedData extractedData;
    private String processingStatus;
    private Classification classification;
    private DerivedImages derivedImages;
    private Map<String, Object> processingReasons = new HashMap<String, Object>();
    
    
    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getCustomerReferenceId() {
        return customerReferenceId;
    }

    public void setCustomerReferenceId(String customerReferenceId) {
        this.customerReferenceId = customerReferenceId;
    }

    public ExtractedData getExtractedData() {
        return extractedData;
    }

    public void setExtractedData(ExtractedData extractedData) {
        this.extractedData = extractedData;
    }

    public String getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(String processingStatus) {
        this.processingStatus = processingStatus;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public DerivedImages getDerivedImages() {
        return derivedImages;
    }

    public void setDerivedImages(DerivedImages derivedImages) {
        this.derivedImages = derivedImages;
    }

    public Map<String, Object> getProcessingReasons() {
        return this.processingReasons;
    }

    public void setProcessingReasons(String name, Object value) {
        this.processingReasons.put(name, value);
    }

}
