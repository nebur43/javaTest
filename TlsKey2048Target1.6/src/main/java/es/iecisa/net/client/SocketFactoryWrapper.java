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
public class SocketFactoryWrapper extends SSLSocketFactory {

    private SSLSocketFactory sf = null;
    private String[] enabledCiphers = null;

    public SocketFactoryWrapper(SSLSocketFactory sf, String[] enabledCiphers) {
        super();
        this.sf = sf;
        this.enabledCiphers = enabledCiphers;
    }

    private Socket getSocketWithEnabledCiphers(Socket socket) {
        if (enabledCiphers != null && socket != null && socket instanceof SSLSocket) {
            ((SSLSocket) socket).setEnabledCipherSuites(enabledCiphers);
        }
        return socket;
    }

    @Override
    public Socket createSocket(Socket s, String host, int port,
            boolean autoClose) throws IOException {
        return getSocketWithEnabledCiphers(sf.createSocket(s, host, port, autoClose));
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return sf.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        if (enabledCiphers == null) {
            return sf.getSupportedCipherSuites();
        } else {
            return enabledCiphers;
        }
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException,
            UnknownHostException {
        return getSocketWithEnabledCiphers(sf.createSocket(host, port));
    }

    @Override
    public Socket createSocket(InetAddress address, int port)
            throws IOException {
        return getSocketWithEnabledCiphers(sf.createSocket(address, port));
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localAddress,
            int localPort) throws IOException, UnknownHostException {
        return getSocketWithEnabledCiphers(sf.createSocket(host, port, localAddress, localPort));
    }

    @Override
    public Socket createSocket(InetAddress address, int port,
            InetAddress localaddress, int localport) throws IOException {
        return getSocketWithEnabledCiphers(sf.createSocket(address, port, localaddress, localport));
    }

}
