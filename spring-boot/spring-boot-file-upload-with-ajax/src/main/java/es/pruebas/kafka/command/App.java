package es.pruebas.kafka.command;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App {
	
	private static final Logger log = LoggerFactory.getLogger(App.class);

	/**
	 * para leer los mensajes generados:
	 * 
	 * 	kafka-console-consumer --topic cola1 --bootstrap-server localhost:9092 --from-beginning
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		log.debug("empezamos prueba comandos 3");
		
		String msg = "{\"processID\":\"bec02939-ac30-4100-838a-P2\",\"petitionID\":null,\"scanID\":\"6542\",\"brand\":1,\"systemID\":\"NEWPANGEA\",\"context\":null,\"document\":{\"authenticity\":\"20\",\"front\":{\"url\":null},\"back\":{\"url\":null},\"status\":\"1\",\"docNumber\":\"48998237A\",\"documentModel\":\"IDESPC2\",\"validFor\":\"02/01/2021\",\"idNumber\":\"38840645Q\",\"issuingCountry\":\"ESP\",\"issuingTeam\":null,\"mrz\":\"IDESPANA118368438840645Q<<<<<< 8102047M2210252ESP<<<<<<<<<<<8 AMORES<TOMAS<<JOSE<ANTONIO<<<<\",\"type\":\"2\",\"Individual\":{\"placeOfBirth\":null,\"birthdate\":\"04/02/1981\",\"givenName\":\"JOSE ANTONIO\",\"nationality\":\"ESP\",\"gender\":\"H\",\"familyName\":\"AMORES\",\"familyName2\":\"TOMAS\",\"streetName\":null,\"city\":null,\"residencePlace\":null,\"stateOrProvince\":null,\"postcode\":null}}}";
		
//		Process process = Runtime.getRuntime().exec("./enviarMensaje.sh 12 "+msg,null,null);
		
		
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.directory(new File("/logs/econtprep/KyC/kafka/kafka_2.12-2.1.1/bin"));
		
		processBuilder.command("./enviarMensaje.sh", "1212" , msg);
		
//		processBuilder.command("kafka-console-producer", "--topic", "cola1", "--broker-list", "localhost:9092");
//		
		Process process = processBuilder.start();
//		
//		PrintWriter p = new PrintWriter(process.getOutputStream() );
//		p.println("mi mensaje final ");
//		p.println("mi mensaje2 final");
//		p.println();
//		p.flush();
//		p.close();
		

		
//		Process process2 = Runtime.getRuntime().exec(
//				"kafka-console-producer --topic cola1 --broker-list localhost:9092", null, null);
//				"echo mensaje100 > hola.txt", null, null);
		

//		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//		String line;
//		while ((line = reader.readLine()) != null) {
//			output.append(line + "\n");
//		}
		
		 
		 
//		 PrintWriter p = new PrintWriter(process2.getOutputStream() );
//	    p.println("mensaje1 x");
//	    p.println("mensaje2 x");
//	    p.println("mensaje3 x");
//	    p.flush();
//	    p.close();
//		
	    
		int result = process.waitFor();
		
		
		
//		if (result == 0) {
//			log.debug("Success!");
//			log.debug("output: " + output);
//			System.exit(0);
//		} else {
//			log.debug("NO Success :(");
//			log.debug("output: "+output);
//			System.exit(1);
//		}
	    log.debug("fin... result:" + result);
		
	}

}
