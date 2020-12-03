package es.pruebas.AES_GCM_example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.bouncycastle.util.Arrays;
import org.springframework.security.crypto.codec.Base64;

public class AppHashSHA512 {
	
	
	protected static byte[] SALT = {31, 19, 63, 80, -107, -102, -54, 42, -56, 75, 63, -2, -107, 34, 69, -12};
	
	private static final byte[] SALT_UAT = {31, 19, 63, 80, -107, -102, -54, 42, -56, 75, 63, -2, -107, 34, 69, -12};
	private static final byte[] SALT_PRO = {-78, -6, -69, 61, -96, 11, -113, -69, -77, 38, 69, -10, 20, 23, 43, 91};
	
	public static String getSHA512(String txt) throws NoSuchAlgorithmException {
		
		
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(SALT); // usar semilla es opcional
		
		byte[] hashed = md.digest(txt.getBytes());

		return new String(Base64.encode ( hashed ) );
		
	}
	
	public static boolean validateSHA512(String txt, String hash) throws NoSuchAlgorithmException {
		return hash.equals(getSHA512(txt));
	}
	
	public static String getSHA512WithSalt(String txt) throws NoSuchAlgorithmException {
		
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(salt); // usar semilla es opcional
		
		byte[] hashed = md.digest(txt.getBytes());

		byte[] token = Arrays.concatenate(salt, hashed);
		
		return new String(Base64.encode ( token ) );
		
	}
	
	public static boolean validateSHA512WithSalt(String token, String txtToValidate) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		byte[] tokenBytes = Base64.decode(token.getBytes());
		
		byte[] salt = Arrays.copyOfRange(tokenBytes, 0, 16);
		byte[] hash = Arrays.copyOfRange(tokenBytes, 16, tokenBytes.length);
		
		
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(salt); // usar semilla es opcional
		
		
		byte[] calculatedHash = md.digest(txtToValidate.getBytes());
		
		return Arrays.areEqual(hash, calculatedHash);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
//		String token = getSHA512WithSalt("00|489987897A");
//		System.out.println("SHA512:" + token + " validate:" + validateSHA512WithSalt(token, "00|489987897A"));
//		
//		System.out.println("SHA512:" + token + " validate:" + validateSHA512WithSalt(token, "00|489987897A"));
//		
//		System.out.println("SHA512:" + token + " validate:" + validateSHA512WithSalt(token, "11|489987897A"));
		
		System.out.println("base 64 UAT: " +new String(Base64.encode(SALT_UAT))); 
		System.out.println("base 64 PRO: " +new String(Base64.encode(SALT_PRO)));
		
		
		
		String token = getSHA512("00|489987897A");
		
		System.out.println("SHA512:" + token + " validate:" + validateSHA512( "00|489987897A", token));
		System.out.println("SHA512:" + token + " validate:" + validateSHA512("11|489987897A" , token));
		
		SALT = SALT_PRO;
		token = getSHA512("00|489987897A");
		System.out.println("SHA512:" + token + " validate:" + validateSHA512( "00|489987897A", token));
		System.out.println("SHA512:" + token + " validate:" + validateSHA512("11|489987897A" , token));

	}

}
