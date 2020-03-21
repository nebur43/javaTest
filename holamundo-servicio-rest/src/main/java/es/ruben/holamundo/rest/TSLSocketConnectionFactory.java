package es.ruben.holamundo.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.Principal;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.security.cert.X509Certificate;

import org.bouncycastle.crypto.tls.Certificate;
import org.bouncycastle.crypto.tls.CertificateRequest;
import org.bouncycastle.crypto.tls.DefaultTlsClient;
import org.bouncycastle.crypto.tls.ExtensionType;
import org.bouncycastle.crypto.tls.TlsAuthentication;
import org.bouncycastle.crypto.tls.TlsClientProtocol;
import org.bouncycastle.crypto.tls.TlsCredentials;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * This Class enables TLS V1.2  connection based on BouncyCastle Providers.
 * Just to use: 
 * URL myurl = new URL( "http:// ...URL tha only Works in TLS 1.2);
   HttpsURLConnection  con = (HttpsURLConnection )myurl.openConnection();
   con.setSSLSocketFactory(new TSLSocketConnectionFactory());  
 * @author AZIMUTS
 *
 */
public class TSLSocketConnectionFactory extends SSLSocketFactory {  


//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Adding Custom BouncyCastleProvider
/////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
   static {
    if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null)
        Security.addProvider(new BouncyCastleProvider());
    }   
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//HANDSHAKE LISTENER
/////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
    public class TLSHandshakeListener implements HandshakeCompletedListener {
        @Override
        public void handshakeCompleted(HandshakeCompletedEvent event) { 

        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//SECURE RANDOM
/////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
    private SecureRandom _secureRandom = new SecureRandom();

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Adding Custom BouncyCastleProvider
/////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
    @Override
    public Socket createSocket(Socket socket, final String host, int port, boolean arg3)
            throws IOException {
        if (socket == null) {
            socket = new Socket();
        }
        if (!socket.isConnected()) {
            socket.connect(new InetSocketAddress(host, port));
        }

        final TlsClientProtocol tlsClientProtocol = new TlsClientProtocol(socket.getInputStream(), socket.getOutputStream(), _secureRandom);
        return _createSSLSocket(host, tlsClientProtocol);


      }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// SOCKET FACTORY  METHODS  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public String[] getDefaultCipherSuites() {      
        return null;
    }

    @Override
    public String[] getSupportedCipherSuites(){ 
        return null;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException,UnknownHostException{  
        return null;
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException { 
        return null;
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost,
            int localPort) throws IOException, UnknownHostException {   
        return null;
    }

    @Override
    public Socket createSocket(InetAddress address, int port,
            InetAddress localAddress, int localPort) throws IOException{    
        return null;
    }

    
    private Socket __createSocket(String host, String port, String proxyHost, String proxyPort) throws IOException {
		// Socket object connecting to proxy
		Socket sock = new Socket(proxyHost, Integer.parseInt(proxyPort));

		/***********************************
		 * HTTP CONNECT protocol RFC 2616
		 ***********************************/
		String proxyConnect = "CONNECT " + host + ":" + port + " HTTP/1.1\n";
		proxyConnect = proxyConnect.concat("Host: " + host + ":" + port + "\n");
		proxyConnect = proxyConnect.concat("Proxy-Connection: keep-alive\n");
		proxyConnect = proxyConnect.concat(
				"User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36\n");
		proxyConnect = proxyConnect.concat("\n");

		// Add Proxy Authorization if proxyUser and proxyPass is set
		/*
		 * try { String proxyUserPass = String.format("%s:%s",
		 * System.getProperty("http.proxyUser"), System.getProperty("http.proxyPass"));
		 * 
		 * proxyConnect.concat(" HTTP/1.0\nProxy-Authorization:Basic " +
		 * Base64.encode(proxyUserPass.getBytes()));* } catch (Exception e) { } finally
		 * { proxyConnect.concat("\n\n"); }
		 */

		sock.getOutputStream().write(proxyConnect.getBytes());
		/***********************************/

		/***************************
		 * validate HTTP response.
		 ***************************/
		byte[] tmpBuffer = new byte[512];
		InputStream socketInput = sock.getInputStream();

		int len = socketInput.read(tmpBuffer, 0, tmpBuffer.length);

		if (len <= 0) {
			throw new SocketException("Invalid response from proxy: " + proxyConnect);
		}

		String proxyResponse = new String(tmpBuffer, 0, len, "UTF-8");

		// Expecting HTTP/1.x 200 OK
		if (proxyResponse.indexOf("200") != -1) {

			// Flush any outstanding message in buffer
			if (socketInput.available() > 0)
				socketInput.skip(socketInput.available());

			// Proxy Connect Successful, return the socket for IO
			return sock;
		} else {
			throw new SocketException("Fail to create Socket " + proxyResponse);
		}
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//SOCKET CREATION
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private SSLSocket _createSSLSocket(final String host , final TlsClientProtocol tlsClientProtocol) {
     return new SSLSocket() {            
        private java.security.cert.Certificate[] peertCerts;

         @Override
          public InputStream getInputStream() throws IOException {
              return tlsClientProtocol.getInputStream();
          }

          @Override
          public OutputStream getOutputStream() throws IOException {
              return tlsClientProtocol.getOutputStream();
          }

          @Override
          public synchronized void close() throws IOException {         
             tlsClientProtocol.close();
          }

           @Override
           public void addHandshakeCompletedListener(HandshakeCompletedListener arg0) {         

           }

            @Override
            public boolean getEnableSessionCreation() {         
                return false;
            }

            @Override
            public String[] getEnabledCipherSuites() {          
                return null;
            }

            @Override
            public String[] getEnabledProtocols() {
                return null;
            }

            @Override
            public boolean getNeedClientAuth(){         
                return false;
            }

            @Override
            public SSLSession getSession() {
                   return new SSLSession() {

                    @Override
                    public int getApplicationBufferSize() {                 
                        return 0;
                    }

                    @Override
                    public String getCipherSuite() {
                         throw new UnsupportedOperationException();
                    }

                    @Override
                    public long getCreationTime() {
                         throw new UnsupportedOperationException();
                    }

                    @Override
                    public byte[] getId() {
                         throw new UnsupportedOperationException();
                    }

                    @Override
                    public long getLastAccessedTime() {
                         throw new UnsupportedOperationException();
                    }

                    @Override
                    public java.security.cert.Certificate[] getLocalCertificates() {
                         throw new UnsupportedOperationException();
                    }

                    @Override
                    public Principal getLocalPrincipal() {
                         throw new UnsupportedOperationException();
                    }

                    @Override
                    public int getPacketBufferSize() {
                         throw new UnsupportedOperationException();
                    }

                    @Override
                    public X509Certificate[] getPeerCertificateChain()
                            throws SSLPeerUnverifiedException {
                        return null;
                    }

                    @Override
                    public java.security.cert.Certificate[] getPeerCertificates()throws SSLPeerUnverifiedException {
                         return peertCerts;
                    }

                    @Override
                    public String getPeerHost() {
                         throw new UnsupportedOperationException();
                    }

                    @Override
                    public int getPeerPort() {                      
                        return 0;
                    }

                    @Override
                    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
                      return null;
                         //throw new UnsupportedOperationException();

                    }

                    @Override
                    public String getProtocol() {
                         throw new UnsupportedOperationException();
                    }

                    @Override
                    public SSLSessionContext getSessionContext() {
                         throw new UnsupportedOperationException();
                    }

                    @Override
                    public Object getValue(String arg0) {
                         throw new UnsupportedOperationException();
                    }

                    @Override
                    public String[] getValueNames() {
                         throw new UnsupportedOperationException();
                    }

                    @Override
                    public void invalidate() {
                         throw new UnsupportedOperationException();

                    }

                    @Override
                    public boolean isValid() {
                         throw new UnsupportedOperationException();
                    }

                    @Override
                    public void putValue(String arg0, Object arg1) {
                         throw new UnsupportedOperationException();

                    }

                    @Override
                    public void removeValue(String arg0) {
                         throw new UnsupportedOperationException();

                    }

                   };
            }


            @Override
            public String[] getSupportedProtocols() {       
                return null;
            }

            @Override
            public boolean getUseClientMode() {             
                return false;
            }

            @Override
            public boolean getWantClientAuth() {

                return false;
            }

            @Override
            public void removeHandshakeCompletedListener(HandshakeCompletedListener arg0) {             

            }

            @Override
            public void setEnableSessionCreation(boolean arg0) {


            }

            @Override
            public void setEnabledCipherSuites(String[] arg0) {         

            }

            @Override
            public void setEnabledProtocols(String[] arg0) {


            }

            @Override
            public void setNeedClientAuth(boolean arg0) {           

            }

            @Override
            public void setUseClientMode(boolean arg0) {            

            }

            @Override
            public void setWantClientAuth(boolean arg0) {               

            }

            @Override
            public String[] getSupportedCipherSuites() {            
                return null;
            }
            @Override
            public void startHandshake() throws IOException {
                  tlsClientProtocol.connect(new DefaultTlsClient() {                       
                         @Override
                          public Hashtable<Integer, byte[]> getClientExtensions() throws IOException {
                                Hashtable<Integer, byte[]> clientExtensions = super.getClientExtensions();
                                if (clientExtensions == null) {
                                    clientExtensions = new Hashtable<Integer, byte[]>();
                                }

                                //Add host_name
                                byte[] host_name = host.getBytes();

                                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                final DataOutputStream dos = new DataOutputStream(baos);
                                dos.writeShort(host_name.length + 3); // entry size
                                dos.writeByte(0); // name type = hostname
                                dos.writeShort(host_name.length);
                                dos.write(host_name);
                                dos.close();
                                clientExtensions.put(ExtensionType.server_name, baos.toByteArray());
                                return clientExtensions;
                        }

                        @Override
                        public TlsAuthentication getAuthentication()
                                throws IOException {
                            return new TlsAuthentication() {


                                @Override
                                public void notifyServerCertificate(Certificate serverCertificate) throws IOException {

                                  try {
                                        CertificateFactory cf = CertificateFactory.getInstance("X.509");
                                        List<java.security.cert.Certificate> certs = new LinkedList<java.security.cert.Certificate>();
                                        for ( org.bouncycastle.asn1.x509.Certificate c : serverCertificate.getCertificateList()) {                                          
                                            certs.add(cf.generateCertificate(new ByteArrayInputStream(c.getEncoded())));
                                        }
                                        peertCerts = certs.toArray(new java.security.cert.Certificate[0]);
                                    } catch (CertificateException e) {                                   
                                       //System.out.println( "Failed to cache server certs"+ e);
                                       throw new IOException(e);
                                    }

                                }

                                @Override
                                public TlsCredentials getClientCredentials(CertificateRequest arg0)
                                        throws IOException {                                    
                                    return null;
                                }

                            };

                        }

                   });



            }




     };//Socket

    }
}