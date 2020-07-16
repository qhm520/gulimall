package com.qian.rsa.pri;

import com.qian.rsa.Base64;

import javax.crypto.Cipher;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * @ClassName:ConfigTools
 * @Description:不要分发到客户端，加解密配置工具，根据公钥进行解密，根据密钥进行加密
 * @author well
 * @date:2017年11月13日
 *
 */
public class ConfigTools {

	/**
	 * 根据密钥进行加密
	 *
	 * @param key
	 *            密钥的纯文本
	 * @param plainText
	 *            明文
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String key, String plainText) throws Exception {
		if ((key == null) || (key.length() == 0)) {
			throw new RuntimeException("key, the private key can not be null.");
		}

		byte[] keyBytes = Base64.base64ToByteArray(key);
		return encrypt(keyBytes, plainText);
	}

	/**
	 * 根据密钥进行加密
	 *
	 * @param keyBytes
	 *            密钥
	 * @param plainText
	 *            明文
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(byte[] keyBytes, String plainText) throws Exception {
		if (keyBytes == null) {
			throw new RuntimeException("keyBytes, the private key can not be null.");
		}

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory factory = KeyFactory.getInstance("RSA", "SunRsaSign");
		PrivateKey privateKey = factory.generatePrivate(spec);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		try {
			cipher.init(1, privateKey);
		} catch (InvalidKeyException e) {
			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
			RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPrivateExponent());
			Key fakePublicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
			cipher = Cipher.getInstance("RSA");
			cipher.init(1, fakePublicKey);
		}

		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
		String encryptedString = Base64.byteArrayToBase64(encryptedBytes);

		return encryptedString;
	}

	/**
	 * 根据公钥进行解密
	 *
	 * @param publicKeyText
	 *            公钥的纯文本
	 * @param cipherText
	 *            密文
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String publicKeyText, String cipherText) throws Exception {
		if ((publicKeyText == null) || (publicKeyText.length() == 0)) {
			throw new RuntimeException("publicKeyText, the public key can not be null.");
		}
		PublicKey publicKey = getPublicKey(publicKeyText);
		return decrypt(publicKey, cipherText);
	}

	/**
	 * 根据公钥进行解密
	 *
	 * @param publicKey
	 *            公钥，Java中的PublicKey类对象
	 * @param cipherText
	 *            密文
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(PublicKey publicKey, String cipherText) throws Exception {
		if (publicKey == null) {
			throw new RuntimeException("publicKey, the public key can not be null.");
		}

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		try {
			cipher.init(2, publicKey);
		} catch (InvalidKeyException e) {
			RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
			RSAPrivateKeySpec spec = new RSAPrivateKeySpec(rsaPublicKey.getModulus(), rsaPublicKey.getPublicExponent());
			Key fakePrivateKey = KeyFactory.getInstance("RSA").generatePrivate(spec);
			cipher = Cipher.getInstance("RSA");
			cipher.init(2, fakePrivateKey);
		}

		if ((cipherText == null) || (cipherText.length() == 0)) {
			return cipherText;
		}

		byte[] cipherBytes = Base64.base64ToByteArray(cipherText);
		byte[] plainBytes = cipher.doFinal(cipherBytes);

		return new String(plainBytes);
	}

	/**
	 * 根据公钥字符串获取公钥Java中的PublicKey类对象
	 *
	 * @param publicKeyText
	 *            公钥的纯文本
	 * @return
	 * @throws Exception
	 */
	private static PublicKey getPublicKey(String publicKeyText) throws Exception {
		try {
			byte[] publicKeyBytes = Base64.base64ToByteArray(publicKeyText);
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);

			KeyFactory keyFactory = KeyFactory.getInstance("RSA", "SunRsaSign");
			return keyFactory.generatePublic(x509KeySpec);
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to get public key", e);
		}
	}

}
