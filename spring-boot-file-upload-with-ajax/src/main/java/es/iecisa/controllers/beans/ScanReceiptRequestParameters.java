
package es.iecisa.controllers.beans;

public class ScanReceiptRequestParameters {
	
	public static final String BRAND_ORANGE = "1";
	public static final String BRAND_SIMYO = "3";
	public static final String BRAND_AMENA = "4";
	public static final String BRAND_JAZZTEL = "5";

    private String sFID;
    private String systemID;
    private String petitionID;
    private String brand;
    private String docID;
    private String typeDocID;

    public String getSFID() {
        return sFID;
    }

    public void setSFID(String sFID) {
        this.sFID = sFID;
    }

    public String getSystemID() {
        return systemID;
    }

    public void setSystemID(String systemID) {
        this.systemID = systemID;
    }

    public String getPetitionID() {
        return petitionID;
    }

    public void setPetitionID(String petitionID) {
        this.petitionID = petitionID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getTypeDocID() {
        return typeDocID;
    }

    public void setTypeDocID(String typeDocID) {
        this.typeDocID = typeDocID;
    }

}
