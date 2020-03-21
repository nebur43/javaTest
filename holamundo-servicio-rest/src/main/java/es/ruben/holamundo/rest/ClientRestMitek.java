package es.ruben.holamundo.rest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.ruben.holamundo.rest.dao.MitekResponseToken;
import es.ruben.holamundo.rest.dossier.request.Configuration;
import es.ruben.holamundo.rest.dossier.request.Evidence;
import es.ruben.holamundo.rest.dossier.request.Image;
import es.ruben.holamundo.rest.dossier.request.MitekRequestDossier;
import es.ruben.holamundo.rest.dossier.request.Verifications;
import es.ruben.holamundo.rest.dossier.response.MitekResponseDossier;

public class ClientRestMitek {

	public static void main(String[] args) throws Exception {

//		// usamos protocolo SSL TLSv1.2
		HttpsURLConnection.setDefaultSSLSocketFactory(new TSLSocketConnectionFactory());

		pruebasDNI();
//		pruebasFace();
		
//		procesarDirectorioDeImagenes("C:\\Users\\67760769\\Pictures\\especiales");
		
	}
	
	public static void pruebasDNI() throws IOException {
		RestTemplate restTemplate=new RestTemplate();
		setHttpInterceptor(restTemplate);
		
			MitekResponseToken mitekResponseToken = getToken(restTemplate);
			String token = mitekResponseToken.getAccess_token();
		
		
		
		String typeToken = "Bearer";
		MitekResponseDossier mitekResponseDossier;
		
			System.out.println(token);
			
			mitekResponseDossier=procesarDNI(restTemplate,typeToken, token,"C:\\Users\\67760769\\Pictures\\Camera Roll","front21.jpg","Vicente_back.jpg");
			printDNIinfo(mitekResponseDossier);
			System.out.println();
		
		
	}
	
	public static void pruebasFace() throws IOException {
		RestTemplate restTemplate=new RestTemplate();
		setHttpInterceptor(restTemplate);
		
			MitekResponseToken mitekResponseToken = getToken(restTemplate);
			String token = mitekResponseToken.getAccess_token();
		
		
		
		String typeToken = "Bearer";
		MitekResponseDossier mitekResponseDossier;
		
			System.out.println(token);
			
//			mitekResponseDossier=procesarBiometria(restTemplate,typeToken, token,"C:\\Users\\67760769\\Pictures\\Camera Roll","front21.jpg","back22.jpg","rubenCara.jpg");
			mitekResponseDossier=procesarBiometria(restTemplate,typeToken, token,"C:\\Users\\67760769\\Pictures\\Camera Roll","Vicente_front.jpg","Vicente_back.jpg","vicenteFace.jpg");
			printDNIinfo(mitekResponseDossier);
			System.out.println();
		
		
	}
	
	public static void procesarDirectorioDeImagenes(String directorio) throws IOException {
		
		RestTemplate restTemplate=new RestTemplate();
		MitekResponseToken mitekResponseToken = getToken(restTemplate);
		System.out.println("token="+mitekResponseToken.getAccess_token());
		String token = mitekResponseToken.getAccess_token();
		String typeToken = "Bearer";
		
		File fdirectorio = new File(directorio);
		if (fdirectorio.isDirectory()) {
			procesarDirectorioDeImagenes(fdirectorio, restTemplate, typeToken, token);
			
		}
		
	}
	
	public static void procesarDirectorioDeImagenes(File directorio , RestTemplate restTemplate, String typeToken, String token) throws IOException {
		System.out.println("dir:" + directorio.getAbsolutePath());
		String file1=null;
		String file2=null;
		for (File file:directorio.listFiles()) {
			if (file.isDirectory() && !file.getName().endsWith("mitek") && !file.getName().endsWith("svn")) {
				procesarDirectorioDeImagenes(file, restTemplate, typeToken, token);
			}
			if (file.getName().toUpperCase().endsWith(".JPG") ||
					file.getName().toUpperCase().endsWith(".JPEG")) {
				if (file1==null) {
					file1 = file.getName();
				} else {
					file2 = file.getName();
				}
			}
		}
		if (file1!=null)
			procesarDNI(restTemplate,typeToken, token,directorio.getAbsolutePath(),file1,file2);
	}
	
	public void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	        }
	    }
	}
	
	private static ArrayList<File> getArhivos(File directorio) {
		ArrayList<File> r = new ArrayList<File>();
		if (!directorio.getName().endsWith("mitek")) {
			for (final File fileEntry : directorio.listFiles()) {
				if (fileEntry.isDirectory()) {
					r.addAll(getArhivos(fileEntry));
		        } else {
		            r.add(fileEntry);
		        }
			}
		}
		return r;
	}
	
	
	public static void eliminarImagenesDevueltas(MitekResponseDossier mitekResponseDossier) {
		
		for (es.ruben.holamundo.rest.dossier.response.Evidence evidence:mitekResponseDossier.getEvidence()) {
			for (es.ruben.holamundo.rest.dossier.response.Image image:evidence.getImages() ) {
				if (image.getDerivedImages() != null ) {
					if (image.getDerivedImages().getCroppedDocument()!=null
							&& image.getDerivedImages().getCroppedDocument().getData()!=null
							&& !image.getDerivedImages().getCroppedDocument().getData().isEmpty()) {
						image.getDerivedImages().getCroppedDocument().setData("CONTENIDO_ELIMINADO");
					}
					if (image.getDerivedImages().getCroppedPortrait()!=null
							&& image.getDerivedImages().getCroppedPortrait().getData()!=null
							&& !image.getDerivedImages().getCroppedPortrait().getData().isEmpty()) {
						image.getDerivedImages().getCroppedPortrait().setData("CONTENIDO_ELIMINADO");
					}
					if (image.getDerivedImages().getCroppedSignature()!=null
							&& image.getDerivedImages().getCroppedSignature().getData()!=null
							&& !image.getDerivedImages().getCroppedSignature().getData().isEmpty()) {
						image.getDerivedImages().getCroppedSignature().setData("CONTENIDO_ELIMINADO");
					}
				}
			}
		}
		
	}
	
	private static void printDNIinfo(MitekResponseDossier mitekResponseDossier) {
		es.ruben.holamundo.rest.dossier.response.Image imageFront=null;
		es.ruben.holamundo.rest.dossier.response.Image imageBack=null;
		for (es.ruben.holamundo.rest.dossier.response.Image image :mitekResponseDossier.getEvidence().get(0).getImages() ) {
			if (image.getClassification() != null) {
				if (image.getClassification().getImageType().equals("IdentificationCardFront")) {
					imageFront = image;
				}else if (image.getClassification().getImageType().equals("IdentificationCardBack")) {
					imageBack = image;
				}
			}
		}
		
//		System.out.println(imageFront.getExtractedData().getDynamicProperties()); 
		System.out.println(mitekResponseDossier.getEvidence().get(0).getType());
		
//		// crear imagenes con la response
//		try {
//			if (mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages() != null
//					&& mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages().getCroppedDocument()!= null) {
//				EcontratoUtils.writeImageFile(EcontratoUtils.stringBase64ToByte(mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages().getCroppedDocument().getData()), new File ("c:\\tmp\\croppedDocument_"+mitekResponseDossier.getEvidence().get(0).getImages().get(0).getImageId()+".jpg"));
//			}
//
//			if (mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages() != null
//					&& mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages().getCroppedDocument()!= null) {
//				EcontratoUtils.writeImageFile(EcontratoUtils.stringBase64ToByte(mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages().getCroppedDocument().getData()), new File ("c:\\tmp\\croppedDocument_"+mitekResponseDossier.getEvidence().get(0).getImages().get(1).getImageId()+".jpg"));
//			}
//
//			if (mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages() != null
//					&& mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages().getCroppedPortrait()!= null) {
//				EcontratoUtils.writeImageFile(EcontratoUtils.stringBase64ToByte(mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages().getCroppedPortrait().getData()), new File ("c:\\tmp\\croppedPortrait_"+mitekResponseDossier.getEvidence().get(0).getImages().get(0).getImageId()+".jpg"));
//			}
//
//			if (mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages() != null
//					&& mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages().getCroppedPortrait()!= null) {
//				EcontratoUtils.writeImageFile(EcontratoUtils.stringBase64ToByte(mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages().getCroppedPortrait().getData()), new File ("c:\\tmp\\croppedPortrait_"+mitekResponseDossier.getEvidence().get(0).getImages().get(1).getImageId()+".jpg"));
//			}
//			
//			if (mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages() != null
//					&& mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages().getCroppedSignature()!= null) {
//				EcontratoUtils.writeImageFile(EcontratoUtils.stringBase64ToByte(mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages().getCroppedSignature().getData()), new File ("c:\\tmp\\roppedSignature_"+mitekResponseDossier.getEvidence().get(0).getImages().get(0).getImageId()+".jpg"));
//			}
//
//			if (mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages() != null
//					&& mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages().getCroppedSignature()!= null) {
//				EcontratoUtils.writeImageFile(EcontratoUtils.stringBase64ToByte(mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages().getCroppedSignature().getData()), new File ("c:\\tmp\\roppedSignature_"+mitekResponseDossier.getEvidence().get(0).getImages().get(1).getImageId()+".jpg"));
//			}
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	
	private static MitekResponseToken getToken(RestTemplate restTemplate ) {
//		RestTemplate restTemplate = new RestTemplate();
		

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map =
		    new LinkedMultiValueMap<String, String>();
		map.add("grant_type", "client_credentials");
		map.add("client_id", "orange.sand.eu");
		map.add("client_secret", "p5PjZO9pDgqG!");
		map.add("scope", "dossier.creator global.identity.api");

		HttpEntity<MultiValueMap<String, String>> entity =
		    new HttpEntity<MultiValueMap<String, String>>(map, headers);
		
		// usamos protocolo SSL TLSv1.2
//		HttpsURLConnection.setDefaultSSLSocketFactory(new TSLSocketConnectionFactory());
		
		return restTemplate.postForObject("https://api.sandbox.west-1.eu.mitekcloud.com/connect/token", entity,MitekResponseToken.class);
		 
	}
	
	
	private static MitekResponseDossier procesarBiometria(RestTemplate restTemplate ,String tokenType, String token, String directorio , String file1, String file2, String fileCara) throws IOException {
		
//		RestTemplate restTemplate = new RestTemplate();
		
		System.out.println("procesando en directorio:" + directorio);
		System.out.println("	imagen1:" + file1);
		System.out.println("	imagen2:" + file2);
		System.out.println("	cara:" + fileCara);
		

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", tokenType + " " + token);
		
		MitekRequestDossier mitekRequestDossier = new MitekRequestDossier();
		mitekRequestDossier.setEvidence( new ArrayList<Evidence>());
		Evidence evidenceDocument = new Evidence();
		mitekRequestDossier.getEvidence().add(evidenceDocument);
		evidenceDocument.setType("IdDocument");
		evidenceDocument.setImages(new ArrayList<Image>());
		
		
		Image imageFront = new Image();
		evidenceDocument.getImages().add(imageFront);
		imageFront.setData(Base64Utils.encodeToString(readImageFile(directorio+"\\"+file1)));
//		imageFront.setData(Base64Utils.encodeToString(readImageFile("C:\\Users\\67760769\\Pictures\\Camera Roll\\Vicente_front.jpg")));		
		
		if (file2 != null) {
			Image imageBack = new Image();
			evidenceDocument.getImages().add(imageBack);
			imageBack.setData(Base64Utils.encodeToString(readImageFile(directorio+"\\"+file2)));
//			imageBack.setData(Base64Utils.encodeToString(readImageFile("C:\\Users\\67760769\\Pictures\\Camera Roll\\Vicente_back.jpg")));
		}
		
		
		Evidence evidenceBiometric = new Evidence();
		mitekRequestDossier.getEvidence().add(evidenceBiometric);
		evidenceBiometric.setType("Biometric");
		evidenceBiometric.setBiometricType("Selfie");
		evidenceBiometric.setData(Base64Utils.encodeToString(readImageFile(directorio+"\\"+fileCara)));

		
		Configuration configuration = new Configuration();
		mitekRequestDossier.setConfiguration(configuration);
		configuration.setResponseImages( new ArrayList<String>());
		configuration.getResponseImages().add("CroppedDocument");
//		configuration.getResponseImages().add("CroppedPortrait");
//		configuration.getResponseImages().add("CroppedSignature");
		configuration.setVerifications(new Verifications());
		configuration.getVerifications().setFaceComparison(true);
		configuration.getVerifications().setFaceLiveness(false); // no tenemos permisos
		
		HttpEntity<MitekRequestDossier> entity =
		    new HttpEntity<MitekRequestDossier>(mitekRequestDossier, headers);

		MitekResponseDossier mitekResponseDossier = null;
		try {
			long t1 = System.currentTimeMillis();
			mitekResponseDossier = restTemplate.postForObject("https://api.sandbox.west-1.eu.mitekcloud.com/api/verify/v2/dossier", entity,MitekResponseDossier.class);
			long t2 = System.currentTimeMillis();
			System.out.println("tiempo de procesado mitek:" + (t2-t1)/1000 + "s" );
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
				System.out.println(" volver a coger token ");
			}
			System.err.println(e);
		}
		
		
		new File(directorio+"\\mitek").mkdirs();
		
		
		if (mitekResponseDossier.getEvidence().get(0).getImages().size() > 0
				&& mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages() != null
				&& mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages().getCroppedDocument()!= null
				&& !mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages().getCroppedDocument().getData().isEmpty()) {
			EcontratoUtils.writeImageFile(EcontratoUtils.stringBase64ToByte(mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages().getCroppedDocument().getData()), new File (directorio+"\\mitek\\"+file1+".jpg"));
		}

		if (mitekResponseDossier.getEvidence().get(0).getImages().size()> 1
				&& mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages() != null
				&& mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages().getCroppedDocument()!= null
				&& !mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages().getCroppedDocument().getData().isEmpty()) {
			EcontratoUtils.writeImageFile(EcontratoUtils.stringBase64ToByte(mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages().getCroppedDocument().getData()), new File (directorio+"\\mitek\\"+file2+".jpg"));
		}
		
//		eliminarImagenesDevueltas(mitekResponseDossier);
		new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File(directorio+"\\mitek\\"+file1+".json"), mitekResponseDossier);

		
		return mitekResponseDossier;
	}
	
	private static MitekResponseDossier procesarDNI(RestTemplate restTemplate ,String tokenType, String token, String directorio , String file1, String file2) throws IOException {
		
//		RestTemplate restTemplate = new RestTemplate();
		
		System.out.println("procesando en directorio:" + directorio);
		System.out.println("	imagen1:" + file1);
		System.out.println("	imagen2:" + file2);
		

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", tokenType + " " + token);
		
		MitekRequestDossier mitekRequestDossier = new MitekRequestDossier();
		mitekRequestDossier.setEvidence( new ArrayList<Evidence>());
		Evidence evidence = new Evidence();
		mitekRequestDossier.getEvidence().add(evidence);
		evidence.setType("IdDocument");
		evidence.setImages(new ArrayList<Image>());
		Image imageFront = new Image();
		evidence.getImages().add(imageFront);
		imageFront.setData(Base64Utils.encodeToString(readImageFile(directorio+"\\"+file1)));
//		imageFront.setData(Base64Utils.encodeToString(readImageFile("C:\\Users\\67760769\\Pictures\\Camera Roll\\Vicente_front.jpg")));		
		
		if (file2 != null) {
			Image imageBack = new Image();
			evidence.getImages().add(imageBack);
			imageBack.setData(Base64Utils.encodeToString(readImageFile(directorio+"\\"+file2)));
//			imageBack.setData(Base64Utils.encodeToString(readImageFile("C:\\Users\\67760769\\Pictures\\Camera Roll\\Vicente_back.jpg")));
		}
		
		Configuration configuration = new Configuration();
		mitekRequestDossier.setConfiguration(configuration);
		configuration.setResponseImages( new ArrayList<String>());
		configuration.getResponseImages().add("CroppedDocument");
		configuration.getResponseImages().add("CroppedPortrait");
//		configuration.getResponseImages().add("CroppedSignature");

		HttpEntity<MitekRequestDossier> entity =
		    new HttpEntity<MitekRequestDossier>(mitekRequestDossier, headers);

		MitekResponseDossier mitekResponseDossier = null;
		try {
			long t1 = System.currentTimeMillis();
			mitekResponseDossier = restTemplate.postForObject("https://api.sandbox.west-1.eu.mitekcloud.com/api/verify/v2/dossier", entity,MitekResponseDossier.class);
			long t2 = System.currentTimeMillis();
			System.out.println("tiempo de procesado mitek:" + (t2-t1)/1000 + "s" );
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
				System.out.println(" volver a coger token ");
			}
			System.err.println(e);
		}
		
		
		new File(directorio+"\\mitek").mkdirs();
		
		
		if (mitekResponseDossier.getEvidence().get(0).getImages().size() > 0
				&& mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages() != null
				&& mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages().getCroppedDocument()!= null
				&& !mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages().getCroppedDocument().getData().isEmpty()) {
			EcontratoUtils.writeImageFile(EcontratoUtils.stringBase64ToByte(mitekResponseDossier.getEvidence().get(0).getImages().get(0).getDerivedImages().getCroppedDocument().getData()), new File (directorio+"\\mitek\\"+file1+".jpg"));
		}

		if (mitekResponseDossier.getEvidence().get(0).getImages().size()> 1
				&& mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages() != null
				&& mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages().getCroppedDocument()!= null
				&& !mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages().getCroppedDocument().getData().isEmpty()) {
			EcontratoUtils.writeImageFile(EcontratoUtils.stringBase64ToByte(mitekResponseDossier.getEvidence().get(0).getImages().get(1).getDerivedImages().getCroppedDocument().getData()), new File (directorio+"\\mitek\\"+file2+".jpg"));
		}
		
		eliminarImagenesDevueltas(mitekResponseDossier);
		new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File(directorio+"\\mitek\\"+file1+".json"), mitekResponseDossier);

		
		return mitekResponseDossier;
	}
	
	public static void setHttpInterceptor(RestTemplate restTemplate ) {
		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new ClientHttpRequestInterceptor() {
			
			@Override
			public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
					throws IOException {
				
				System.out.println("*** REQUEST ****");
				System.out.println(request.getMethod() + " " + request.getURI());
				for (Entry<String, List<String>> e:request.getHeaders().entrySet()) {
					System.out.print("   " +e.getKey() + ": ");
					for (String v :e.getValue()) {
						System.out.print(v+ " ");
					}
					System.out.println();
				}
				
				System.out.println();
				System.out.println("Body:");
				ClientHttpRequest clientHttpRequest = (ClientHttpRequest) request;
				String jsonBody = ((ByteArrayOutputStream)clientHttpRequest.getBody()).toString();
				System.out.println( jsonPrettyPrint(jsonBody) ); // json pretty
				
				
				System.out.println();
				System.out.println();
				
				ClientHttpResponse response = execution.execute(request, body);
				
				System.out.println("*** RESPONSE ****");
				System.out.println(response.getRawStatusCode() + " " + response.getStatusText());
				for (Entry<String, List<String>> e:response.getHeaders().entrySet()) {
					System.out.print("   " + e.getKey() + ": ");
					for (String v :e.getValue()) {
						System.out.print(v+ " ");
					}
					System.out.println();
				}
				
				System.out.println();
				System.out.println("Body:");
				String bodyResponse = inputStreamToString(response.getBody());
				ClientHttpResponseKyC clientHttpResponseKyC = new ClientHttpResponseKyC(response);
				clientHttpResponseKyC.setBody(stringToInputStream(bodyResponse));
				System.out.println(jsonPrettyPrint(bodyResponse));
				
				
				
				System.out.println();
				
				return clientHttpResponseKyC;
			}
		});
		restTemplate.setInterceptors(interceptors);
	}
	
	public static String jsonPrettyPrint(String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Object jsonObject = mapper.readValue(json, Object.class);
			String jsonPrettyprint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
			return jsonPrettyprint;
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return json;
	}
	
	public static String inputStreamToString(InputStream inputStream) throws IOException {
		 
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		while ((line = bufferedReader.readLine()) != null) {
			stringBuilder.append(line);
		}
			 
		return stringBuilder.toString();
	}
	
	public static InputStream stringToInputStream(String string) {
		InputStream stream = new ByteArrayInputStream(string.getBytes());
		return stream;
	}
	
	private static byte[] readImageFile(String path) throws IOException {
		
		File file = new File(path);		
		byte[] array = new byte[(int) file.length()];

		FileInputStream fis = new FileInputStream(file);
		try {
			if (fis.read(array)<=0) {
				throw new IOException("Archivo vacío: "+path) ;
			}
		} finally {
			fis.close();
		}
		
		
		return array;		
	}
	
	
	// HTTP POST request
	private static void sendPost() throws Exception {

		String url = "https://api.sandbox.west-1.eu.mitekcloud.com/connect/token";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setSSLSocketFactory(new TSLSocketConnectionFactory()); 		// usamos protocolo SSL TLSv1.2
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		String urlParameters = "grant_type=client_credentials&client_id=orange.sand.eu&client_secret=p5PjZO9pDgqG!&scope=dossier.creator global.identity.api";
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		System.out.println(response.toString());

	}
	
	
    private static RestTemplate createRestTemplateProxy() throws Exception {
        final String username = "myusername";
        final String password = "myPass";
        final String proxyUrl = "proxy.nyc.bigtower.com";
        final int port = 8080;

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials( 
                new AuthScope(proxyUrl, port), 
                new UsernamePasswordCredentials(username, password));

        HttpHost myProxy = new HttpHost(proxyUrl, port);
        HttpClientBuilder clientBuilder = HttpClientBuilder.create();

        clientBuilder.setProxy(myProxy).setDefaultCredentialsProvider(credsProvider).disableCookieManagement();

        HttpClient httpClient = clientBuilder.build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);
        
        

        return new RestTemplate(factory);
    }



}
