package es.pruebas.AES_GCM_example;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;

public class AppSpring {
	
    public static final String INITIALIZATION_VECTOR = "710d805acf0d25086d9181a03e773484";

    public static final String KEY = "e5a885172b758539489a077fe6f8df60";
    
    public static void main(String[] args) {
		
	BouncyCastleProvider bouncyProvider = new BouncyCastleProvider();
    	
    if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
		System.out.println("CARGANDO PROVIDER");
		System.out.println("pòsicion:" + Security.addProvider(bouncyProvider));
	}
	
	if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
		System.out.println("NO SE HA CARGADO");
	}
    	
    	BytesEncryptor bytesEncryptor = Encryptors.stronger(KEY, INITIALIZATION_VECTOR);
    	byte[] txtEncriptado = bytesEncryptor.encrypt("hola mundo".getBytes());
    	
    	System.out.println("texto encriptado: " + txtEncriptado);
    	
    	String txtDesencriptado = bytesEncryptor.decrypt(txtEncriptado).toString();
    	
    	System.out.println("texto desencriptado: " + txtDesencriptado);
    	
	}

}
