package es.pruebas.AES_GCM_example;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.bouncycastle.util.Arrays;
import org.springframework.security.crypto.codec.Base64;

public class AppHashPBKDF2 {
	
	// https://www.baeldung.com/java-password-hashing
	
	// sha256
	
	 // autentication|docIdentidad
	
	 // actualizar IC (scandocid ) si faltan parámetros (en develop y alani)
	
	public static String getPBKDF2Hash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		
		
		byte[] hash = factory.generateSecret(spec).getEncoded();
		
		byte[] totalKey = Arrays.concatenate(salt, hash);
		
		return new String(Base64.encode(totalKey));

	}
	
	public static boolean validatePBKDF2Hash(String totalKeyS, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		byte[] totalKey = Base64.decode(totalKeyS.getBytes());
		
		byte[] salt = Arrays.copyOfRange(totalKey, 0, 16);
		byte[] hash = Arrays.copyOfRange(totalKey, 16, totalKey.length);
		
		
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		
		
		byte[] calculatedHash = factory.generateSecret(spec).getEncoded();
		
		return Arrays.areEqual(hash, calculatedHash);
	}
	
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
	
		
		String token = getPBKDF2Hash("password");
		System.out.println("PBKDF2:" + token + " validate:" + validatePBKDF2Hash(token, "password"));
		
		token = getPBKDF2Hash("password");
		System.out.println("PBKDF2:" + token + " validate:" + validatePBKDF2Hash(token, "password"));
		
		token = getPBKDF2Hash("password");
		System.out.println("PBKDF2:" + token + " validate:" + validatePBKDF2Hash(token, "password2"));
		
	}

}
