package es.ruben.holamundo.rest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PruebaPuertos {

	

		
		    /**
		     * @param args the command line arguments
		     */
		    public static void main(String[] args) throws IOException {
//		     for(int i=6881;i<=6999;i++){
		    	 int i= 443;
		         ServerSocket SS=new ServerSocket(i);
		         System.out.println("Abriendo el puerto: "+i);
		         System.out.println("Servidor escuchando en el puerto: "+i);
		         Socket so=SS.accept();
		         System.out.println("se ha conectado la ip: "+so.getRemoteSocketAddress().toString()+" en el puerto: "+so.getPort());
//		       }//fin for
		    }//fin main 
		

}
