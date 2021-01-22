package es.pruebas.multipart;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class Cliente {

	/**
	 * multipart es para enviar ficheros, no array de bytes[],
	 * es decir, necesitamos al menos un nombre de fichero también (getResourceFile())
	 */
	public void enviarFichero() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file",  getResourceFile("nombreFichero.txt", "prueba multipart nebur".getBytes()));
		body.add("requestJson", getRequest());
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		String serverUrl = "http://localhost:8080/multipart/upload";

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
		
		System.out.println("Respuesta: " + response.getBody());
		
	}
	
	private RequestBean getRequest() {
		RequestBean r = new RequestBean();
		r.setIdOperacion("XXUNXXX");
		r.setNumeroOperaciones(44);
		return r;
	}
	
	public static Resource getTestFile() throws IOException {
        Path testFile = Files.createTempFile("test-file", ".txt");
        System.out.println("Creating and Uploading Test File: " + testFile);
        Files.write(testFile, "Hello World !!, This is a test file.".getBytes());
        return new FileSystemResource(testFile.toFile());
    }
	
	public Resource getResourceFile(String fileName, byte[] b) {
		return new ByteArrayResource(b) {
			@Override
			public String getFilename() {
				return fileName;
			};
		};
	}
	
	public static void main(String[] args) throws IOException {
		
		Cliente cliente = new Cliente();
		cliente.enviarFichero();
		
		
	}
}
