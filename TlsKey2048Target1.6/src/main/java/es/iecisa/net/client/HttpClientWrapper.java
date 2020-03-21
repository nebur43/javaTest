/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.iecisa.net.client;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author XY6743GO
 */
public class HttpClientWrapper {
    
     private static final String PREFIX_HTPPS = "https";
    
    public static InputStream execute(String url) throws Exception {
         return supportHttps(url) ? 
                 executeWayHttps(new URL(url)) : exucuteWayHttp(new URL(url));
    }
    
    private static InputStream exucuteWayHttp(URL url) throws Exception {
         URLConnection urlConnection = url.openConnection();
         return urlConnection.getInputStream();
    }
    
    private static InputStream executeWayHttps(URL url)throws Exception {
        HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
        
        System.setProperty("ssl.SocketFactory.provider", "es.iecisa.net.client.SocketFactoryAxisWrapper");
//		System.setProperty("org.apache.axis.components.net.SecureSocketFactory","es.orange.econtrato.net.SocketFactoryAxisWrapper");
        
        //urlConnection.setSSLSocketFactory(new HelperCiphersExclude().socketFactory());
        return urlConnection.getInputStream();
    }
    
    private static Boolean supportHttps(String url) {
        return url.startsWith(PREFIX_HTPPS) ||
                url.startsWith(PREFIX_HTPPS.toUpperCase());
    }
    
}
