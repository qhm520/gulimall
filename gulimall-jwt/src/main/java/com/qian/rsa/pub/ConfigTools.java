package com.qian.rsa.pub;

import com.qian.rsa.Base64;

import javax.crypto.Cipher;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * @author well
 * @ClassName:ConfigTools
 * @Description:需要分发到客户端，解密配置工具，根据公钥进行解密
 * @date:2017年11月13日
 */
public class ConfigTools {

    /**
     * 根据公钥进行解密
     *
     * @param publicKeyText 公钥的纯文本
     * @param cipherText    密文
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
     * @param publicKey  公钥，Java中的PublicKey类对象
     * @param cipherText 密文
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
     * @param publicKeyText 公钥的纯文本
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKeyText) throws Exception {
        if ((publicKeyText == null) || (publicKeyText.length() == 0)) {
            throw new RuntimeException("publicKeyText, the public key can not be null.");
        }
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
