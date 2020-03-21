package es.iecisa.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import es.iecisa.controllers.beans.ScanReceiptRequest;
import es.iecisa.controllers.beans.ScanReceiptRequestParameters;
import es.iecisa.controllers.beans.ScanReceiptRequestReceipt;
import es.iecisa.controllers.beans.ScanReceiptResponse;

public class CallRest {
	
	private static final Logger logger = Logger.getLogger(CallRest.class);

	public String callJustificanteBancario(byte[] content, String contentType) throws IOException {
		
		ScanReceiptRequest request = getRequest(content, contentType);
		
		RestTemplate restTemplate=new RestTemplate();
		HttpEntity<ScanReceiptRequest> entity =
			    new HttpEntity<ScanReceiptRequest>(request, null);
		ScanReceiptResponse response = restTemplate.postForObject("http://localhost:8080/eContratoKyCWS/scanReceipt", entity,ScanReceiptResponse.class);
		
		String iban = response.getReceiptData().getIban();
		logger.info("################   IBAN: "+iban + "    ######################");
		
		return iban;
		
	}
	
	private ScanReceiptRequest getRequest(byte[] content, String contentType) throws IOException {
		ScanReceiptRequest request = new ScanReceiptRequest();
		request.setProcessID("1234565");
		ScanReceiptRequestReceipt receipt = new ScanReceiptRequestReceipt();
		request.setReceipt(receipt);
		receipt.setContent(Base64Utils.encodeToString(content));
		receipt.setContentType(contentType);
		ScanReceiptRequestParameters parameters = new ScanReceiptRequestParameters();
		request.setParameters(parameters);
		parameters.setBrand("1");
		parameters.setDocID("doc_id");
		parameters.setSystemID("PANGEA");
		parameters.setTypeDocID("IDENTITY");
		return request;
	}
	
	private byte[] readFile(String path) throws IOException {
		
		File file = new File(path);		
		byte[] array = new byte[(int) file.length()];

		FileInputStream fis = new FileInputStream(file);
		try {
			if (fis.read(array)<=0) {
				throw new IOException("Archivo vac�o: "+path) ;
			}
		} finally {
			fis.close();
		}
		
		
		return array;		
	}
	
}
