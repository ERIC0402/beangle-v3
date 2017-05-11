/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.codec;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 加密工具
 * 
 * @author beangle
 */
public class EncryptUtil {
	private final static Logger log = LoggerFactory.getLogger(EncryptUtil.class);

	public static String encode(String password) {
		return encode(password, "MD5");
	}

	/**
	 * Encode a string using algorithm specified in web.xml and return the
	 * resulting encrypted password. If exception, the plain credentials string
	 * is returned
	 * 
	 * @param password
	 *            Password or other credentials to use in authenticating this
	 *            username
	 * @param algorithm
	 *            Algorithm used to do the digest
	 * @return encypted password based on the algorithm.
	 */
	public static String encode(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();

		return encode(unencodedPassword, algorithm);
	}
	
	public static String encode(byte[] unencoded){
		return encode(unencoded,"MD5");
	}
	
	/**
	 * md5加密字节数组
	 * @param unencodedPassword
	 * @param algorithm
	 * @return
	 */
	public static String encode(byte[] unencoded, String algorithm){
		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			log.error("Exception:{}", e);
			return null;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencoded);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuilder buf = new StringBuilder();

		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}


	public static String encodeDES(String password) {
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec("8532679F93F5D7467A79B83E08E0564B".getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			byte[] decryptedData = cipher.doFinal(password.getBytes());
			return new BASE64Encoder().encode(decryptedData);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;

	}

	public static String dencodeDES(String encodedPassword) {
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] buf = decoder.decodeBuffer(encodedPassword);
			if (buf == null || buf.length == 0) {
				throw new IllegalArgumentException("encodedPassword is invalide");
			}

			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec("8532679F93F5D7467A79B83E08E0564B".getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			byte[] decryptedData = cipher.doFinal(buf);
			return new String(decryptedData);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		System.out.println(encode("Login").toUpperCase());
		System.out.println(encode("99DEA78007133396A7B8ED70578AC6AE").toUpperCase());
	}
}
