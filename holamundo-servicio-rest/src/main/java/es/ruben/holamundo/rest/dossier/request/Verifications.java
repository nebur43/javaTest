
package es.ruben.holamundo.rest.dossier.request;

public class Verifications {

    private Boolean faceComparison;
    
    private Boolean faceLiveness;

    public Boolean getFaceComparison() {
        return faceComparison;
    }

    public void setFaceComparison(Boolean faceComparison) {
        this.faceComparison = faceComparison;
    }

	public Boolean getFaceLiveness() {
		return faceLiveness;
	}

	public void setFaceLiveness(Boolean faceLiveness) {
		this.faceLiveness = faceLiveness;
	}

    
    
}
