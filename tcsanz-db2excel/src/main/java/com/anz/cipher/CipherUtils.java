/**
 * 
 */
package com.anz.cipher;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Administrator
 *
 */
public class CipherUtils {
	private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = "ADBSJHJS12547896".getBytes();
    private static Key key = new SecretKeySpec(keyValue, ALGORITHM); 

    public static void main(String args[]) throws Exception {
        String encriptValue = encrypt("tcs_owner");
        System.out.println("encrypt:" + encriptValue);
        System.out.println("decrypt:" + decrypt(encriptValue));
    }

    /**
     * @param args
     * @throws Exception
     */

    public static String encrypt(String valueToEnc) throws Exception {
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);

        byte[] encValue = c.doFinal(valueToEnc.getBytes());
        byte[] encryptedByteValue = new Base64().encode(encValue);
        String encryptedValue = new String(encryptedByteValue);

        return encryptedValue;
    }

    public static String decrypt(String encryptedValue) throws Exception {
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = new Base64().decode(encryptedValue.getBytes());
        return new String(c.doFinal(decodedValue));
    }

}
