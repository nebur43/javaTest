
package es.iecisa.controllers.beans;


public class ScanReceiptResponse {

    private String code;
    private String message;
    private String processID;
    private ScanReceiptResponseData receiptData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProcessID() {
        return processID;
    }

    public void setProcessID(String processID) {
        this.processID = processID;
    }

    public ScanReceiptResponseData getReceiptData() {
        return receiptData;
    }

    public void setReceiptData(ScanReceiptResponseData receiptData) {
        this.receiptData = receiptData;
    }

}
