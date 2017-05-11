package org.beangle.security.codec;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

public class RSAUtil {

	private static final int KEY_SIZE = 1024;
	private static KeyPairGenerator keyPairGen = null;
	private static KeyPair keyPair;

	static {
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA", new BouncyCastleProvider());
			keyPair = generateKeyPair();
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 生成并返回RSA密钥对。
	 */
	private static synchronized KeyPair generateKeyPair() {
		try {
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair oneKeyPair = keyPairGen.generateKeyPair();
			return oneKeyPair;
		} catch (InvalidParameterException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * * 生成公钥 *
	 * 
	 * @param modulus
	 *            *
	 * @param publicExponent
	 *            *
	 * @return RSAPublicKey *
	 * @throws Exception
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
		try {
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 生成私钥 *
	 * 
	 * @param modulus
	 *            *
	 * @param privateExponent
	 *            *
	 * @return RSAPrivateKey *
	 * @throws Exception
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 加密 *
	 * 
	 * @param key
	 *            加密的密钥 *
	 * @param data
	 *            待加密的明文数据 *
	 * @return 加密后的数据 *
	 * @throws Exception
	 */
	public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize)
					cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
				else
					cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
				// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
				// ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
				// OutputSize所以只好用dofinal方法。
				i++;
			}
			return raw;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * * 解密 *
	 * 
	 * @param key
	 *            解密的密钥 *
	 * @param raw
	 *            已经加密的数据 *
	 * @return 解密后的明文 *
	 * @throws Exception
	 */
	public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;
			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return bout.toByteArray();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/** 返回已初始化的默认的公钥。 */
	public static RSAPublicKey getDefaultPublicKey() {
		return (RSAPublicKey) keyPair.getPublic();
	}

	/**
	 * 使用默认的私钥解密由JS加密（使用此类提供的公钥加密）的字符串。
	 * 
	 * @param encrypttext
	 *            密文。
	 * @return {@code encrypttext} 的原文字符串。
	 */
	public static String decryptStringByJs(String encrypttext) {
		String text = decryptString(encrypttext);
		if (text == null) {
			return null;
		}
		return StringUtils.reverse(text);
	}

	/**
	 * 使用默认的私钥解密给定的字符串。
	 * <p />
	 * 若{@code encrypttext} 为 {@code null}或空字符串则返回 {@code null}。 私钥不匹配时，返回
	 * {@code null}。
	 * 
	 * @param encrypttext
	 *            密文。
	 * @return 原文字符串。
	 */
	public static String decryptString(String encrypttext) {
		if (StringUtils.isBlank(encrypttext)) {
			return null;
		}
		try {
			byte[] en_data = Hex.decode(encrypttext);
			byte[] data = decrypt((RSAPrivateKey) keyPair.getPrivate(), en_data);
			return new String(data);
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * * *
	 * 
	 * @param args
	 *            *
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		KeyPair keyPair = generateKeyPair();
		String test = "我是中文";
		byte[] en_test = encrypt(keyPair.getPublic(), test.getBytes());
		System.out.println("encrypt:" + new String(en_test));
		byte[] de_test = decrypt(keyPair.getPrivate(), en_test);
		System.out.println("decrypt:" + new String(de_test));
	}
}
