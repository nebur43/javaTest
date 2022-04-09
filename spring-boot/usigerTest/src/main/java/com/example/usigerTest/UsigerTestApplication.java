package com.example.usigerTest;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.orange.econtrato.pki.asf.ConectorPKIASF;
import es.orange.econtrato.pki.asf.exception.ConectorPKIASFException;

@SpringBootApplication
public class UsigerTestApplication implements CommandLineRunner, ExitCodeGenerator{

	public static void main(String[] args) {
		SpringApplication.run(UsigerTestApplication.class, args);
	}

	@Override
	public int getExitCode() {
		return 0;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("ejecutando comando");
		pruebaSecurizacion();
		
	}
	
	public void pruebaSecurizacion() throws ConectorPKIASFException, IOException {
		
//		String entorno = "usigner_ent1"; // j8
//		String entorno = "usigner_ent2"; // j8
		String entorno = "usigner_aseg"; // j8
//		String entorno = "usigner_pro"; // j8		
//		String entorno = "asf_ent1"; // j8
//		String entorno = "asf_ent2"; // j8
//		String entorno = "asf_uat"; // antiguo j6
//		String entorno = "asf_aseg"; // j8
//		String entorno = "asf_pro"; // j8

		
		System.out.println("Inicio pruebas en " + entorno);
		System.out.println("");
		InputStream firmar = UsigerTestApplication.class.getResourceAsStream("/factura.pdf");
		System.out.println(" inputstream firmar: " + firmar);
		ConectorPKIASF conectorPKIASF = new ConectorPKIASF("econtrato", "firma", entorno+".properties", "orange");
		byte[] docAsf = conectorPKIASF.securizar(firmar);
		System.out.println("bytes salida:" + docAsf.length);
//		assertNotNull(docAsf);
//		assertTrue(docAsf.length>0);
//		EcontratoUtils.writeImageFile(docAsf, new File("c://tmp/facturaAsf1_selloTiempo.pdf"));
	}

}
