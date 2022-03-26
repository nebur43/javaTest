
package es.iecisa.controllers.beans;


public class ScanReceiptRequest {

    private String processID;
    private ScanReceiptRequestParameters parameters;
    private ScanReceiptRequestReceipt receipt;

    public String getProcessID() {
        return processID;
    }

    public void setProcessID(String processID) {
        this.processID = processID;
    }

    public ScanReceiptRequestParameters getParameters() {
        return parameters;
    }

    public void setParameters(ScanReceiptRequestParameters parameters) {
        this.parameters = parameters;
    }

    public ScanReceiptRequestReceipt getReceipt() {
        return receipt;
    }

    public void setReceipt(ScanReceiptRequestReceipt receipt) {
        this.receipt = receipt;
    }

}
