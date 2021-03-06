package com.qian.jwt;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class TestRSA {
    //非对称密钥算法
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 密钥长度，DH算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数，在512到65536位之间
     */
    private static final int KEY_SIZE = 512;
//    //公钥
//    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2drZFx6RSHytD8sHzhTdi06C8h+L5t35tTCP3pEpSEyjGmlPHGDb+lXId3aTpFDEo2QTLEvEVahRvVIl4GzFYZMMU9WNvAci0t/TB+tda6YUqcXlJJ6G/nu53nIBn07WeCKbM0ECX0MGKvgYlHkl5whElGaZmQG/Xzg614fJRbwIDAQAB";
//    //私钥
//    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALZ2tkXHpFIfK0PywfOFN2LToLyH4vm3fm1MI/ekSlITKMaaU8cYNv6Vch3dpOkUMSjZBMsS8RVqFG9UiXgbMVhkwxT1Y28ByLS39MH611rphSpxeUknob+e7necgGfTtZ4IpszQQJfQwYq+BiUeSXnCESUZpmZAb9fODrXh8lFvAgMBAAECgYBQQWLmymsyW9dR6cL5c8zqwR7gDfz9zYHkXSssz6yI8ThsMpFK7xGxZNZu4ICarHD7AImlMM2tl50pYsuccol/Eq/JQLy6J6YAo7KXZNzZKCjGVwZvAgiMkeLwvDSkTBOZmmCYAEgEZ68b3TCqmer+J1mFnbfYm3DVsC4Q6fM3MQJBAO7QsYy0v7OG4ZY32oxd+21ce0e945WLdyPiEVnl1ZXegXVVajVyU305UuXTXoIYxuNObdXDfX9ANZfxG1+H2esCQQDDl/Iw4AloXZDmojdYsjFQMv/WsGJaBGRGH288yJHYLEgy0Q2f8E9gcv8zHbHJxT1R5SW25f8xrReyQTA5GCGNAkEAopvXSUeJgGRHFlHRreIQYNan2qGhZSIhbX1w8xVb6UAoRgxy1RGTZ6CwoWza3Rqh0gSFJRiHSy4dT+gMC3AlIQJAF4kbHYJ331US8I2od0XJ5UqdummzC/3nSwDRU9EWq5p4fZTmPM6f3o035CC4mfzf6hDWvvFiCco4RbzOXrBmqQJAEDyk9BuCvsMxRukh5KBqMxlgPYKBBBj01LuLJ1736Y9gb+UocAQs3dAAiXJqhN12+VEFzVbh1zctP75cKoM9KA==";


    //公钥
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2drZFx6RSHytD8sHzhTdi06C8h+L5t35tTCP3pEpSEyjGmlPHGDb+lXId3aTpFDEo2QTLEvEVahRvVIl4GzFYZMMU9WNvAci0t/TB+tda6YUqcXlJJ6G/nu53nIBn07WeCKbM0ECX0MGKvgYlHkl5whElGaZmQG/Xzg614fJRbwIDAQAB";
    //私钥
    private static final String PRIVATE_KEY = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMPb45L2/ifTexwe7JUwHIMX0ydmgh+WjJlbC1bbQV4KFG/pMD1Cb1rhA1qZHJacBRF7qfDXkSGS/aWsVf0tOpltY0IIWvkHcIp7PmqqYuUdwoSSuTNp+2sxRotMGLFAlwa9etr4FZM2S1YdwtFS1Hd6W0VXh2T3O0/CAOTr8cD7AgMBAAECgYEAmnGnx/kqu38BYlBkYNRad2lvIW+tBnmyDzADBg/JQP2T1he86WGc673p5iQ8resD+CBbBscF88K0b1MxT3696GNDalCWKJLb2gJhABL0dypFFKARny7wIFmtBS6JdMiJbihioXcaOueR+5tjstQbRc3fwnd1GA2LH+Rnv5YBG7kCQQDn64UbKTqcf61dz3fDQcA3qxqs2QAJWE15yQIAWtiUmjlQTWxdBBmZkBW3BIiR87YzSjZZIaduKwF7tRKjCtrNAkEA2DHb3fgpZtocu8/H2dD6gHN83+pR3AlCULradrYd+TgKLxlrhqBDn5IJSP140dlCs3g7TFSc8TBMnMx3yaOa5wJBAMbW8gdIgPz+pqhAoYDiFahk5fj+D/7zG3Hn+fRN71TxKWSFDPxu8HftxgBlPaZNGpC89v3SUdetvZbPt4vcWZ0CQQC/iTyvpQKZGrfGNMlsUlb8olSJ1ID9nj5gY4rVDXf0qg9rtuuqdCY1Ix6Sye3WjNMpSWXPtE7u58mYSRX/ULEVAkEA3ZAcDu3wEAQYRgU2uuziktiy/YZNhQbDfj3EYPpExGqvWFOObzpyVecTqO45AYd2rbPDNuH8+EfMU2nhBY4ijg==";

    /**
     * 初始化密钥对
     *
     * @return Map 甲方密钥的Map
     */
    public static Map <String, Object> initKey() throws Exception {
        //实例化密钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥生成器
        keyPairGenerator.initialize(KEY_SIZE);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //甲方公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        System.out.println("系数：" + publicKey.getModulus() + "  加密指数：" + publicKey.getPublicExponent());
        //甲方私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        System.out.println("系数：" + privateKey.getModulus() + "解密指数：" + privateKey.getPrivateExponent());
        //将密钥存储在map中
        Map <String, Object> keyMap = new HashMap <String, Object>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;

    }

    /**
     * 私钥加密
     *
     * @param data 待加密数据
     * @param key       密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception {

        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥加密
     *
     * @param data 待加密数据
     * @param key       密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap 密钥map
     * @return byte[] 私钥
     */
    public static byte[] getPrivateKey(Map <String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }

    /**
     * 取得公钥
     *
     * @param keyMap 密钥map
     * @return byte[] 公钥
     */
    public static byte[] getPublicKey(Map <String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //初始化密钥
        //生成密钥对
        Map <String, Object> keyMap = RSACoder.initKey();
        //公钥
        byte[] publicKey = RSACoder.getPublicKey(keyMap);
        //byte[] publicKey = b;
        //私钥
        byte[] privateKey = RSACoder.getPrivateKey(keyMap);
        System.out.println("公钥：" + Base64.encode(publicKey));
        System.out.println("私钥：" + Base64.encode(privateKey));

        System.out.println("================密钥对构造完毕,甲方将公钥公布给乙方，开始进行加密数据的传输=============");
        String str = "aattaggcctegthththfef/aat.mp4";
        System.out.println("===========甲方向乙方发送加密数据==============");
        System.out.println("原文:" + str);
        //甲方进行数据的加密
        byte[] code1 = RSACoder.encryptByPublicKey(str.getBytes(), publicKey);
        System.out.println("甲方 使用乙方公钥加密后的数据：" + Base64.encode(code1));
        System.out.println("===========乙方使用甲方提供的公钥对数据进行解密==============");
        //乙方进行数据的解密
        //byte[] decode1=RSACoder.decryptByPublicKey(code1, publicKey);
        byte[] decode1 = RSACoder.decryptByPrivateKey(code1, privateKey);
        System.out.println("乙方解密后的数据：" + new String(decode1) + "");

        System.out.println("===========反向进行操作，乙方向甲方发送数据==============");

        str = "乙方向甲方发送数据RSA算法";

        System.out.println("原文:" + str);

        //乙方使用公钥对数据进行加密
        byte[] code2 = RSACoder.encryptByPublicKey(str.getBytes(), publicKey);
        System.out.println("===========乙方使用公钥对数据进行加密==============");
        System.out.println("加密后的数据：" + Base64.encode(code2));

        System.out.println("=============乙方将数据传送给甲方======================");
        System.out.println("===========甲方使用私钥对数据进行解密==============");

        //甲方使用私钥对数据进行解密
        byte[] decode2 = RSACoder.decryptByPrivateKey(code2, privateKey);

        System.out.println("甲方解密后的数据：" + new String(decode2));


    }
}
