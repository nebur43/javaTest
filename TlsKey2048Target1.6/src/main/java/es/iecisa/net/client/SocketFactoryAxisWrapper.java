/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.iecisa.net.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author XY6743GO
 */
public class SocketFactoryAxisWrapper extends SSLSocketFactory {

    private SSLSocketFactory sf = null;
    
    public SocketFactoryAxisWrapper() throws Exception {
    	super();
    	this.sf = new HelperCiphersExclude().socketFactory();    	
    }

    

    @Override
    public Socket createSocket(Socket s, String host, int port,
            boolean autoClose) throws IOException {
        return sf.createSocket(s, host, port, autoClose);
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return sf.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
            return sf.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException,
            UnknownHostException {
        return sf.createSocket(host, port);
    }

    @Override
    public Socket createSocket(InetAddress address, int port)
            throws IOException {
        return sf.createSocket(address, port);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localAddress,
            int localPort) throws IOException, UnknownHostException {
        return sf.createSocket(host, port, localAddress, localPort);
    }

    @Override
    public Socket createSocket(InetAddress address, int port,
            InetAddress localaddress, int localport) throws IOException {
        return sf.createSocket(address, port, localaddress, localport);
    }

}
