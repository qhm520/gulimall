package com.qian.rsa;

import com.qian.rsa.pri.ConfigTools;

/**
 * @ClassName:RSATest
 * @Description:测试
 * @author well
 * @date:2017年11月13日
 *
 */
public class RSATest {

	/**
	 * 测试加解密
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// 1、运行 SecurityKeysUtil 工具类，生成公钥和密钥
		// SecurityKeysUtil.main
//		String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALZ2tkXHpFIfK0PywfOFN2LToLyH4vm3fm1MI/ekSlITKMaaU8cYNv6Vch3dpOkUMSjZBMsS8RVqFG9UiXgbMVhkwxT1Y28ByLS39MH611rphSpxeUknob+e7necgGfTtZ4IpszQQJfQwYq+BiUeSXnCESUZpmZAb9fODrXh8lFvAgMBAAECgYBQQWLmymsyW9dR6cL5c8zqwR7gDfz9zYHkXSssz6yI8ThsMpFK7xGxZNZu4ICarHD7AImlMM2tl50pYsuccol/Eq/JQLy6J6YAo7KXZNzZKCjGVwZvAgiMkeLwvDSkTBOZmmCYAEgEZ68b3TCqmer+J1mFnbfYm3DVsC4Q6fM3MQJBAO7QsYy0v7OG4ZY32oxd+21ce0e945WLdyPiEVnl1ZXegXVVajVyU305UuXTXoIYxuNObdXDfX9ANZfxG1+H2esCQQDDl/Iw4AloXZDmojdYsjFQMv/WsGJaBGRGH288yJHYLEgy0Q2f8E9gcv8zHbHJxT1R5SW25f8xrReyQTA5GCGNAkEAopvXSUeJgGRHFlHRreIQYNan2qGhZSIhbX1w8xVb6UAoRgxy1RGTZ6CwoWza3Rqh0gSFJRiHSy4dT+gMC3AlIQJAF4kbHYJ331US8I2od0XJ5UqdummzC/3nSwDRU9EWq5p4fZTmPM6f3o035CC4mfzf6hDWvvFiCco4RbzOXrBmqQJAEDyk9BuCvsMxRukh5KBqMxlgPYKBBBj01LuLJ1736Y9gb+UocAQs3dAAiXJqhN12+VEFzVbh1zctP75cKoM9KA==";
//		String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2drZFx6RSHytD8sHzhTdi06C8h+L5t35tTCP3pEpSEyjGmlPHGDb+lXId3aTpFDEo2QTLEvEVahRvVIl4GzFYZMMU9WNvAci0t/TB+tda6YUqcXlJJ6G/nu53nIBn07WeCKbM0ECX0MGKvgYlHkl5whElGaZmQG/Xzg614fJRbwIDAQAB";
		// 货机配置加解密
		//公钥
		String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDD2+OS9v4n03scHuyVMByDF9MnZoIfloyZWwtW20FeChRv6TA9Qm9a4QNamRyWnAURe6nw15Ehkv2lrFX9LTqZbWNCCFr5B3CKez5qqmLlHcKEkrkzaftrMUaLTBixQJcGvXra+BWTNktWHcLRUtR3eltFV4dk9ztPwgDk6/HA+wIDAQAB";
		//私钥
		String priKey = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMPb45L2/ifTexwe7JUwHIMX0ydmgh+WjJlbC1bbQV4KFG/pMD1Cb1rhA1qZHJacBRF7qfDXkSGS/aWsVf0tOpltY0IIWvkHcIp7PmqqYuUdwoSSuTNp+2sxRotMGLFAlwa9etr4FZM2S1YdwtFS1Hd6W0VXh2T3O0/CAOTr8cD7AgMBAAECgYEAmnGnx/kqu38BYlBkYNRad2lvIW+tBnmyDzADBg/JQP2T1he86WGc673p5iQ8resD+CBbBscF88K0b1MxT3696GNDalCWKJLb2gJhABL0dypFFKARny7wIFmtBS6JdMiJbihioXcaOueR+5tjstQbRc3fwnd1GA2LH+Rnv5YBG7kCQQDn64UbKTqcf61dz3fDQcA3qxqs2QAJWE15yQIAWtiUmjlQTWxdBBmZkBW3BIiR87YzSjZZIaduKwF7tRKjCtrNAkEA2DHb3fgpZtocu8/H2dD6gHN83+pR3AlCULradrYd+TgKLxlrhqBDn5IJSP140dlCs3g7TFSc8TBMnMx3yaOa5wJBAMbW8gdIgPz+pqhAoYDiFahk5fj+D/7zG3Hn+fRN71TxKWSFDPxu8HftxgBlPaZNGpC89v3SUdetvZbPt4vcWZ0CQQC/iTyvpQKZGrfGNMlsUlb8olSJ1ID9nj5gY4rVDXf0qg9rtuuqdCY1Ix6Sye3WjNMpSWXPtE7u58mYSRX/ULEVAkEA3ZAcDu3wEAQYRgU2uuziktiy/YZNhQbDfj3EYPpExGqvWFOObzpyVecTqO45AYd2rbPDNuH8+EfMU2nhBY4ijg==";
//
		String [] plainTexts = {"PWBS"};
		for (String plainText : plainTexts) {
			String cipherText = null;
			// 2、利用私钥对明文数据进行加密
			try {
				cipherText = ConfigTools.encrypt(priKey, plainText);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("密文：" + cipherText);
			// 3、利用公钥对密文数据进行解密
			try {
				plainText = ConfigTools.decrypt(pubKey, cipherText);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("明文：" + plainText);
		}
		///////////////////////////////////////////

		// 利用私钥对明文数据进行加密
/*		String[] plainTexts = {
				// h2
				"jdbc:h2:lps;CIPHER=AES",
        		"lps",
        		"filepwd pwd1234",
        		// oracle
        		"jdbc:oracle:thin:@10.108.19.69:1521:orcl",
        		"lps",
        		"pwd1234",
        		// config.properties
				"10.95.15.26",
				"1296",
				"10.95.15.26:8281"
		};
		try {
			for (String plainText : plainTexts) {
				System.out.println(plainText);
				System.out.println(ConfigTools.encrypt(priKey, plainText));
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/

	}

}
