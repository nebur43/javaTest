package es.pruebas.AES_GCM_example;

import java.util.Date;

import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Base64;

/**
 * https://confluence.si.orange.es/display/TWKSOC/Arquitectura+APP+vs+Colectivos+VIP
 * 
 * @author 67760769 - Rubén Aguado
 *
 */
public class AppDirectBouncy
{
	
    public static final String INITIALIZATION_VECTOR = "710d805acf0d25086d9181a03e773484";
//	public static final String INITIALIZATION_VECTOR = new String((new Date().getTime()));
    public static final String KEY = "e5a885172b758539489a077fe6f8df60";
	
    public static final int AES_KEY_SIZE = 128;

    public static void main(String[] args) throws Exception
    {
    	
    	KeyParameter keyParameter = new KeyParameter(KEY.getBytes());
		
        GCMBlockCipher blockCipher = new GCMBlockCipher(new AESEngine());
   	 	blockCipher.init(true, new AEADParameters(keyParameter,AES_KEY_SIZE,INITIALIZATION_VECTOR.getBytes()));
   	 	
   	 	String texto = "HOLA MUNDO";
   	 	System.out.println("Texto original:" + texto);
   	 	
   	 	int outputLength = blockCipher.getOutputSize(texto.length());
		byte[] output = new byte[outputLength];
   	 	
   	 	int off = blockCipher.processBytes(texto.getBytes(), 0, texto.length(), output, 0);
   	 	blockCipher.doFinal(output, off);
   	 	
   	 	
   	 	System.out.println("codificado:" + Base64.encode(output) );
   	 	
   	 	
   	 	//////////////////////
   	 	
   	 	GCMBlockCipher blockCipher2 = new GCMBlockCipher(new AESEngine());
   	 	blockCipher2.init(false, new AEADParameters(keyParameter,AES_KEY_SIZE,INITIALIZATION_VECTOR.getBytes()));
   	 	int outputLength2 = blockCipher.getOutputSize(output.length);
   	 	byte[] output2 = new byte[outputLength2];
   	 	int off2= blockCipher2.processBytes(output, 0, output.length, output2, 0);
   	 	blockCipher2.doFinal(output2, off2);
   	 	
   	 	System.out.println("descodificado:" + new String(output2));
		
    }
}