
package utils;


import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.*;


public class Inventory {

	private static final String ALGO = "AES";
	private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', '<', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e',
			't', 'K', 'e', 'y' };


	// Call this method and get the output and save the output in XML 
	public static String store(String Data) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(Data.getBytes());
		String encryptedValue = new BASE64Encoder().encode(encVal);
		return encryptedValue;
	}


	public static String retrieve(String encryptedData) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
		try {
			byte[] decValue = c.doFinal(decordedValue);
			String decryptedValue = new String(decValue);
			return decryptedValue;
		} catch (IllegalBlockSizeException e) {
			// TODO: handle exception
			return encryptedData;
		}catch (BadPaddingException e) {
			// TODO: handle exception
			return encryptedData;
		}
		
	}


	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}
	
	public static String encrypt4DotNet(String data) throws Exception {
		byte[] keyValue = "MAKV2SPBNI992121".getBytes();
		byte[] sessionKey = keyValue; //Where you get this from is beyond the scope of this post
		byte[] iv = keyValue ; //Ditto
		byte[] plaintext = data.getBytes(); //Whatever you want to encrypt/decrypt
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		//You can use ENCRYPT_MODE or DECRYPT_MODE
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sessionKey, "AES"), new IvParameterSpec(iv));
		byte[] ciphertext = cipher.doFinal(plaintext);
		String encryptedValue = new BASE64Encoder().encode(ciphertext);
		System.out.println("encryptedValue Text : " + encryptedValue);
		return encryptedValue;
	}


	public static void main(String[] args) throws Exception {

		String password = "afs";
		String passwordEnc = Inventory.store(password);
		String passwordDec = Inventory.retrieve(passwordEnc);

		//System.out.println("Plain Text Password : " + password);
		System.out.println("Encrypted Text : " + passwordEnc);
		System.out.println("Decrypted Text : " + passwordDec);
	}
}
