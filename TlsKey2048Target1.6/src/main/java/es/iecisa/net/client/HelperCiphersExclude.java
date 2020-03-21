/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.iecisa.net.client;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 *
 * @author XY6743GO
 */
public class HelperCiphersExclude {

    private static final String PATH_KEY_STORE = System.getProperty("user.dir") + "\\keystore.jks";

    // algoritmos que debemos quitar para que funcione java 6 en SSL (si molesta alguno más se puede quitar aquí)
    private String[] exludedCipherSuites = {"_DHE_", "_DH_"};
    private TrustManagerFactory tmf;

    public void setExludedCipherSuites(String[] exludedCipherSuites) {
        this.exludedCipherSuites = exludedCipherSuites;
    }

    public HelperCiphersExclude() throws Exception {
       this.initTrustManager();
    }

    private void initTrustManager() throws Exception {
        KeyStore keyStore = keyStore();
        tmf = TrustManagerFactory.
                getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);
    }

    public SSLSocketFactory socketFactory() throws Exception {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);
        
        /*SSLParameters params = context.getSupportedSSLParameters();
        List<String> enabledCiphers = new ArrayList<String>();
        for (String cipher : params.getCipherSuites()) {
            boolean exclude = false;
            if (exludedCipherSuites != null) {
                for (int i = 0; i < exludedCipherSuites.length && !exclude; i++) {
                    exclude = cipher.indexOf(exludedCipherSuites[i]) >= 0; } }
            if (!exclude) { enabledCiphers.add(cipher); }
        }
        String[] ciphers = new String[enabledCiphers.size()];
        enabledCiphers.toArray(ciphers);*/
        
        String[] ciphers = enabledCiphers(context);
        SSLSocketFactory sf = context.getSocketFactory();
        return new SocketFactoryWrapper(sf, ciphers);
    }
    
    private String[] enabledCiphers(SSLContext context) throws Exception {
        SSLParameters params = context.getSupportedSSLParameters();
        List<String> enabledCiphers = new ArrayList<String>();
        for (String cipher : params.getCipherSuites()) {
            boolean exclude = false;
            if (exludedCipherSuites != null) {
                for (int i = 0; i < exludedCipherSuites.length && !exclude; i++) {
                    exclude = cipher.indexOf(exludedCipherSuites[i]) >= 0; } }
            if (!exclude) { enabledCiphers.add(cipher); }
        }
        String[] ciphers = new String[enabledCiphers.size()];
        return enabledCiphers.toArray(ciphers);
    }
    
    public String[] enabledCiphers() throws Exception {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);
        return enabledCiphers(context);
    }

    private KeyStore keyStore() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("jks");
        char[] password = "password".toCharArray();
        FileInputStream fis = new FileInputStream(PATH_KEY_STORE);
        keyStore.load(fis, password);
        return keyStore;
    }

}
