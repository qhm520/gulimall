package com.qian.jwt;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加解密工具类
 */
public class RSAUtils {
    //公钥加密
    public static String encrypt(String content, PublicKey publicKey) {
        try {
           /* Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//java默认"RSA"="RSA/ECB/PKCS1Padding"
            cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(privateKey));
            byte[] output = cipher.doFinal(SecurityBase64.base64ToByteArray(content));
            String s = SecurityBase64.byteArrayToBase64(output);
            return s;*/
            //实例化密钥工厂
            KeyFactory keyFactory=KeyFactory.getInstance("RSA", "SunRsaSign");
            //初始化公钥
            //密钥材料转换
            X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(SecurityBase64.base64ToByteArray(privateKey));
            //产生公钥
            PublicKey pubKey=keyFactory.generatePublic(x509KeySpec);

            //数据加密
            Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //公钥加密
    public static byte[] encrypt(byte[] content, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//java默认"RSA"="RSA/ECB/PKCS1Padding"
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //私钥解密
    public static byte[] decrypt(byte[] content, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //私钥解密
    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        try {

            cipher.init(Cipher.DECRYPT_MODE, privateKey);
        } catch (Exception e) {
            e.printStackTrace();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) getPublicKey(publicKey);
            RSAPrivateKeySpec spec = new RSAPrivateKeySpec(
                    rsaPublicKey.getModulus(), rsaPublicKey.getPublicExponent());
            Key fakePrivateKey = KeyFactory.getInstance("RSA").generatePrivate(
                    spec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(2, fakePrivateKey);
            return null;
        }
        byte[] cipherBytes = SecurityBase64.base64ToByteArray(cipherText);
        byte[] plainBytes = cipher.doFinal(cipherBytes);

        return new String(plainBytes);
    }

    /**
     * String转公钥PublicKey
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes= SecurityBase64
                .base64ToByteArray(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "SunRsaSign");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * String转私钥PrivateKey
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = SecurityBase64
                .base64ToByteArray(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "SunRsaSign");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2drZFx6RSHytD8sHzhTdi06C8h+L5t35tTCP3pEpSEyjGmlPHGDb+lXId3aTpFDEo2QTLEvEVahRvVIl4GzFYZMMU9WNvAci0t/TB+tda6YUqcXlJJ6G/nu53nIBn07WeCKbM0ECX0MGKvgYlHkl5whElGaZmQG/Xzg614fJRbwIDAQAB";

    public static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALZ2tkXHpFIfK0PywfOFN2LToLyH4vm3fm1MI/ekSlITKMaaU8cYNv6Vch3dpOkUMSjZBMsS8RVqFG9UiXgbMVhkwxT1Y28ByLS39MH611rphSpxeUknob+e7necgGfTtZ4IpszQQJfQwYq+BiUeSXnCESUZpmZAb9fODrXh8lFvAgMBAAECgYBQQWLmymsyW9dR6cL5c8zqwR7gDfz9zYHkXSssz6yI8ThsMpFK7xGxZNZu4ICarHD7AImlMM2tl50pYsuccol/Eq/JQLy6J6YAo7KXZNzZKCjGVwZvAgiMkeLwvDSkTBOZmmCYAEgEZ68b3TCqmer+J1mFnbfYm3DVsC4Q6fM3MQJBAO7QsYy0v7OG4ZY32oxd+21ce0e945WLdyPiEVnl1ZXegXVVajVyU305UuXTXoIYxuNObdXDfX9ANZfxG1+H2esCQQDDl/Iw4AloXZDmojdYsjFQMv/WsGJaBGRGH288yJHYLEgy0Q2f8E9gcv8zHbHJxT1R5SW25f8xrReyQTA5GCGNAkEAopvXSUeJgGRHFlHRreIQYNan2qGhZSIhbX1w8xVb6UAoRgxy1RGTZ6CwoWza3Rqh0gSFJRiHSy4dT+gMC3AlIQJAF4kbHYJ331US8I2od0XJ5UqdummzC/3nSwDRU9EWq5p4fZTmPM6f3o035CC4mfzf6hDWvvFiCco4RbzOXrBmqQJAEDyk9BuCvsMxRukh5KBqMxlgPYKBBBj01LuLJ1736Y9gb+UocAQs3dAAiXJqhN12+VEFzVbh1zctP75cKoM9KA==";


    public static void main(String[] args) {
        try {
            String encrypt = encrypt("12345678", getPublicKey(publicKey));
            System.out.println(encrypt);
            String decrypt = decrypt(encrypt, getPrivateKey(privateKey));
            System.out.println(decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
