/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.iecisa.tests;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.KeyStore.Entry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;

/**
 * Para generar keystore para certificados de 1024 y 2048 
 *
 * @author XY6743GO
 */
public class KeyStoreManager {

    private static final String PATH_SAVE = System.getProperty("user.dir") + "\\icar.wsdl";
//    private static final String PATH_KEY_STORE = System.getProperty("user.dir") + "\\keystore.jks";
    private static final String PATH_KEY_STORE = "C:\\tmp\\pro\\237" + "\\keystoreTLS.jks";
    private static final String PATH_CERT = "C:\\_PROYECTOS\\KyC\\certificados\\icarpro.cer";
    private static final String URL = "https://demoidcloud.icarvision.com:50061/iCarSAAS.WsPublic/WsDocument.asmx?wsdl";
    private static final String PASSWORD = "password";
    
    private static final String clavePublicaPkcs8 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnEzdW2JkmbrjhjtO8mxWC522ID7UkfdzwHh8dH+LTR31bS5oF4GjoDOXqvJ2KrVtWhtzJ40GTFnBbCVXl+z1jBmA6lmEzQDP9GYJcE+pKLyw7ga5eJdbk6C/j4f4CnGXt02HPHFKjhqn8HBp4q6AbhYEU3qjaoDX7sVoYbcCK7543Ze6mSk6gnSd++h2RFhMvVxEcPDfmvzaMC1tEgx44HzPbEH0twjYoSg2hIFolsoccZv7xoZnZglxZsRIQwZ39vulT9La3XFfSu/H5uD0zJlci5yRaU3E8TGC1mMJnCxm+BwNKYv5RuUV0E7f9qd8GsFKTo09XESXcWOVYPUA6wIDAQAB";

    public static void main(String[] args) {
        try {
        	
        	
        	
//            createKeyStore();
        	showKeyStore("C://properties//Orange//certificados//uat//keystoreTLS.jks","password");
        	showKeyStore("C://properties//Orange//certificados//uat//keystoreCifrado.jks","changeit");
        	showKeyStore("C://properties//Orange//certificados//uat//keystoreFirmar-pre.jks","29100365");
        	showKeyStore("C://properties//Orange//certificados//pro//keystoreTLS.jks","password");
        	showKeyStore("C://properties//Orange//certificados//pro//keystoreCifrado.jks","changeit");
        	showKeyStore("C://properties//Orange//certificados//pro//keystoreFirmar.jks","29100365");
        	System.out.println(" ----------------------------------------- ");
        	
        	Certificate certificado = getCertificate("icardemo", "C://properties//Orange//certificados//uat//keystoreTLS.jks", "password");
        	createKeyStore("pruebaAlias.pro", "C://properties//Orange//eContratoKeyStore.jks", certificado, "29100365");
        	
        	System.out.println(" ");
        	System.out.println(" ");
        	System.out.println(" %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%  ");
        	showKeyStore("C://properties//Orange//eContratoKeyStore.jks","29100365");
        	
//            updateKeyStore("apigeeOrange",PATH_KEY_STORE,PATH_CERT);
//        	showKeyStore(PATH_KEY_STORE);
//        	removeKey("icardemo", PATH_KEY_STORE);
//        	showKeyStore(PATH_KEY_STORE);
//            InputStream inputStream = HttpClientWrapper.execute(URL);
//            saveInFile(inputStream);
        } catch (Exception ex) {
            Logger.getLogger(KeyStoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void saveInFile(InputStream stream) {
        try {
            File targetFile = new File(PATH_SAVE);
            OutputStream outStream = new FileOutputStream(targetFile);
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = stream.read(bytes)) != -1) {
                outStream.write(bytes, 0, read);
            }
            System.out.println("Copy.");
        } catch (IOException ex) {
            System.out.println("No Copy.");
            ex.printStackTrace();
        }
    }

    /**
     * 
     * 
     * este método crea el keystore.jks a partir del certificado de [icar]. Este jks es necesario para ejecutar llamadas 
     * con java 6 a axis 1.4
     * 
     */
    private static void createKeyStore() {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = new BufferedInputStream(new FileInputStream(PATH_CERT));
            Certificate ca = null;
            try { ca = cf.generateCertificate(caInput);} 
            finally { caInput.close(); }
             KeyStore keyStore = KeyStore.getInstance("jks");
            char[] password = PASSWORD.toCharArray();
            keyStore.load(null, password);
            keyStore.setCertificateEntry("ca", ca);
            FileOutputStream fos = new FileOutputStream(PATH_KEY_STORE);
            keyStore.store(fos, password);
            fos.close();
        } catch (Exception ex) {
            Logger.getLogger(KeyStoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void showKeyStore(String pathKeyStorey, String passw) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableEntryException {
    	System.out.println("**** PATH_KEY_STORE:     " +pathKeyStorey + "     ******");
    	System.out.println();
    	 KeyStore keyStore = KeyStore.getInstance("jks");
         char[] password = passw.toCharArray();
         InputStream jksInput = new BufferedInputStream(new FileInputStream(pathKeyStorey));
         keyStore.load(jksInput, password);
         Enumeration<String> enumeration = keyStore.aliases();
         while (  enumeration.hasMoreElements() ) {
        	 String key = enumeration.nextElement();
        	 System.out.println("key:" + key );
        	 Entry entry = null;
        	 try {
        		 entry = keyStore.getEntry(key, null);
        	 } catch (UnrecoverableEntryException e) {
        		 entry = keyStore.getEntry(key, new KeyStore.PasswordProtection(passw.toCharArray()));
        	 }
         	
         	System.out.println("keystore.entry:" + entry.getClass().getSimpleName());
         	Certificate certificate = null;
         	Certificate[] certificatesChain = keyStore.getCertificateChain(key);
         	if (certificatesChain != null) {
         		System.out.println("CHAIN FOUND !!!!");
         		String t="";
         		for (Certificate ce : certificatesChain) {
         			if (ce instanceof X509Certificate) {
         				System.out.println(t+" -> name: "+ ((X509Certificate)ce).getSubjectX500Principal().getName() );
         			} else {
         				System.out.println(t+" -> name unknow");
         			}
         			t+="\t";
         		}
         		certificate = certificatesChain[0];
         	} else {
         		certificate = keyStore.getCertificate(key);
         	}
         	System.out.println("certificado:" + Base64.encodeBase64String(certificate.getEncoded()));
         	if (keyStore.getKey(key, password) != null) {
         		System.out.println("privateKey:" + Base64.encodeBase64String(keyStore.getKey(key, password).getEncoded()));
         	} else {
         		System.out.println("privateKey: null" );
         	}
         	
         	System.out.println("publicKey:" + Base64.encodeBase64String(certificate.getPublicKey().getEncoded()));
         	System.out.println();
         }
    }
    
    
    private static Certificate getCertificate(String key, String pathKeyStore,String passw) throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException {
             KeyStore keyStore = KeyStore.getInstance("jks");
            char[] password = passw.toCharArray();
            InputStream jksInput = new BufferedInputStream(new FileInputStream(pathKeyStore));
            keyStore.load(jksInput, password);
            return keyStore.getCertificate(key);
    }
    
    
    
    private static void createKeyStore(String key, String pathKeyStore,Certificate certificado,String passw) {
        try {
            Certificate ca = certificado;
            
             KeyStore keyStore = KeyStore.getInstance("jks");
            char[] password = passw.toCharArray();
            keyStore.load(null, password);
            keyStore.setCertificateEntry(key, ca);
            FileOutputStream fos = new FileOutputStream(pathKeyStore);
            keyStore.store(fos, password);
            fos.close();
        } catch (Exception ex) {
            Logger.getLogger(KeyStoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void updateKeyStore(String key, String pathKeyStore,Certificate certificado,String passw) {
        try {
            Certificate ca = certificado;
            
             KeyStore keyStore = KeyStore.getInstance("jks");
            char[] password = passw.toCharArray();
            InputStream jksInput = new BufferedInputStream(new FileInputStream(pathKeyStore));
            keyStore.load(jksInput, password);
            keyStore.setCertificateEntry(key, ca);
            FileOutputStream fos = new FileOutputStream(pathKeyStore);
            keyStore.store(fos, password);
            fos.close();
        } catch (Exception ex) {
            Logger.getLogger(KeyStoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void updateKeyStore(String key, String pathKeyStore,String certificado) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = new BufferedInputStream(new FileInputStream(certificado));
            Certificate ca = null;
            try { 
            	ca = cf.generateCertificate(caInput);
            	} finally { 
            		caInput.close(); 
            	}
             KeyStore keyStore = KeyStore.getInstance("jks");
            char[] password = PASSWORD.toCharArray();
            InputStream jksInput = new BufferedInputStream(new FileInputStream(pathKeyStore));
            keyStore.load(jksInput, password);
            keyStore.setCertificateEntry(key, ca);
            FileOutputStream fos = new FileOutputStream(pathKeyStore);
            keyStore.store(fos, password);
            fos.close();
        } catch (Exception ex) {
            Logger.getLogger(KeyStoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    private static void removeKey(String key, String pathKeyStore) {
        try {
            KeyStore keyStore = KeyStore.getInstance("jks");
            char[] password = PASSWORD.toCharArray();
            InputStream jksInput = new BufferedInputStream(new FileInputStream(pathKeyStore));
            keyStore.load(jksInput, password);
            keyStore.deleteEntry(key);
            FileOutputStream fos = new FileOutputStream(pathKeyStore);
            keyStore.store(fos, password);
            fos.close();
        } catch (Exception ex) {
            Logger.getLogger(KeyStoreManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
