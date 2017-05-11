/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.codec;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESEncrypt {

	public static final String ALGORITHM = "DES";

	private byte desKey[];

	public DESEncrypt(byte desKey[]) {
		this.desKey = desKey;
	}

	public byte[] doEncrypt(byte data[]) throws Exception {
		Key k = getKey(desKey);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	public static Key getKey(byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}
}
