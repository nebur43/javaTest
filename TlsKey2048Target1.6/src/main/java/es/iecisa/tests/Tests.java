/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.iecisa.tests;

import es.iecisa.net.client.HttpClientWrapper;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.encoders.Base64Encoder;

/**
 * Para generar keystore para certificados de 1024 y 2048 
 *
 * @author XY6743GO
 */
public class Tests {

    private static final String PATH_SAVE = System.getProperty("user.dir") + "\\icar.wsdl";
//    private static final String PATH_KEY_STORE = System.getProperty("user.dir") + "\\keystore.jks";
    private static final String PATH_KEY_STORE = "C:\\tmp\\pro\\237" + "\\keystoreTLS.jks";
    private static final String PATH_CERT = "C:\\_PROYECTOS\\KyC\\certificados\\icarpro.cer";
    private static final String URL = "https://demoidcloud.icarvision.com:50061/iCarSAAS.WsPublic/WsDocument.asmx?wsdl";

    public static void main(String[] args) {
        try {
//            createKeyStore();
        	showKeyStore("C:\\tmp\\pro\\pre\\keystoreTLS.jks");
        	System.out.println(" ----------------------------------------- ");
//            updateKeyStore("icarcapro",PATH_KEY_STORE,PATH_CERT);
        	showKeyStore(PATH_KEY_STORE);
//        	removeKey("icardemo", PATH_KEY_STORE);
//        	showKeyStore(PATH_KEY_STORE);
//            InputStream inputStream = HttpClientWrapper.execute(URL);
//            saveInFile(inputStream);
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
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
            char[] password = "password".toCharArray();
            keyStore.load(null, password);
            keyStore.setCertificateEntry("ca", ca);
            FileOutputStream fos = new FileOutputStream(PATH_KEY_STORE);
            keyStore.store(fos, password);
            fos.close();
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void showKeyStore(String pathKeyStorey) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
    	 KeyStore keyStore = KeyStore.getInstance("jks");
         char[] password = "password".toCharArray();
         InputStream jksInput = new BufferedInputStream(new FileInputStream(pathKeyStorey));
         keyStore.load(jksInput, password);
         Enumeration<String> enumeration = keyStore.aliases();
         while (  enumeration.hasMoreElements() ) {
        	 String key = enumeration.nextElement();
         	System.out.println("key:" + key);
         	System.out.println("datos:" + Base64.encodeBase64String(keyStore.getCertificate(key).getEncoded()));
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
            char[] password = "password".toCharArray();
            InputStream jksInput = new BufferedInputStream(new FileInputStream(pathKeyStore));
            keyStore.load(jksInput, password);
            keyStore.setCertificateEntry(key, ca);
            FileOutputStream fos = new FileOutputStream(pathKeyStore);
            keyStore.store(fos, password);
            fos.close();
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    private static void removeKey(String key, String pathKeyStore) {
        try {
            KeyStore keyStore = KeyStore.getInstance("jks");
            char[] password = "password".toCharArray();
            InputStream jksInput = new BufferedInputStream(new FileInputStream(pathKeyStore));
            keyStore.load(jksInput, password);
            keyStore.deleteEntry(key);
            FileOutputStream fos = new FileOutputStream(pathKeyStore);
            keyStore.store(fos, password);
            fos.close();
        } catch (Exception ex) {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
