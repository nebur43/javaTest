
package es.ruben.holamundo.rest.dossier.request;

public class Image {

    private String data;
    private String customerReferenceId;
    private EncodedData encodedData;

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

    public EncodedData getEncodedData() {
        return encodedData;
    }

    public void setEncodedData(EncodedData encodedData) {
        this.encodedData = encodedData;
    }

}
