package com.qian.jwt;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sun.misc.BASE64Encoder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@SpringBootApplication
public class GulimallJwtApplication {

	public static void main(String[] args) {
/*		Map <String, Object> map = new HashMap <String, Object>();
		map.put("province", "898765");
		map.put("city", "898765");
		map.put("appkey", "HMu1H/cmyKDOiHv41Y9lLROuOlOo+PPG8F4/RotRmNc=");
		map.put("timestamp", new Date().getTime());

		//密钥
		String keyt = "79e7c69681b8270162386e6daa53d1dc";
		String token=createJWT(map, keyt);
		System.out.println("JWT加密的结果："+ token);
		System.out.println("JWT解密的结果："+ parseJWT(token, keyt));*/


		String ass = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCIsInJlYWQiLCJ3cml0ZSJdLCJjb21wYW55IjoiZ3VsaW1hbGwiLCJleHAiOjE1OTAxMTA0MTIsImF1dGhvcml0aWVzIjpbInN5czpzY2hlZHVsZTppbmZvIiwic3lzOm1lbnU6dXBkYXRlIiwic3lzOmNvbmZpZzppbmZvIiwic3lzOmNvbmZpZzpzYXZlIiwic3lzOm1lbnU6bGlzdCIsInN5czpjb25maWc6dXBkYXRlIiwic3lzOnNjaGVkdWxlOnJlc3VtZSIsInN5czp1c2VyOmRlbGV0ZSIsIlJPTEVfVVNFUiIsInN5czpjb25maWc6bGlzdCIsInN5czptZW51OmluZm8iLCJzeXM6cm9sZTpsaXN0Iiwic3lzOnVzZXI6dXBkYXRlIiwic3lzOm1lbnU6c2VsZWN0Iiwic3lzOnNjaGVkdWxlOnVwZGF0ZSIsInN5czpzY2hlZHVsZTpzYXZlIiwic3lzOnJvbGU6c2VsZWN0Iiwic3lzOnVzZXI6bGlzdCIsInN5czptZW51OnNhdmUiLCJzeXM6b3NzOmFsbF0iLCJzeXM6cm9sZTpzYXZlIiwic3lzOnNjaGVkdWxlOmxvZyIsInN5czpyb2xlOmluZm8iLCJzeXM6c2NoZWR1bGU6ZGVsZXRlIiwiW3N5czptZW51OmRlbGV0ZSIsInN5czpyb2xlOnVwZGF0ZSIsInN5czpzY2hlZHVsZTpsaXN0Iiwic3lzOnVzZXI6aW5mbyIsInN5czpjb25maWc6ZGVsZXRlIiwic3lzOnNjaGVkdWxlOnJ1biIsInN5czpyb2xlOmRlbGV0ZSIsIlJPTEVfQURNSU4iLCJzeXM6dXNlcjpzYXZlIiwic3lzOnNjaGVkdWxlOnBhdXNlIiwic3lzOmxvZzpsaXN0Il0sImp0aSI6IjYyYzZlMzE0LTA2OTEtNGRjOS1iZmU2LTBhNmZmMGRhNGNiZiIsImNsaWVudF9pZCI6Imd1bGltYWxsIn0.Rj1v3WDoblAW-lhTgdkEvzp2ljCI4cpcg2q-fo6DegM";
//		System.out.println("JWT解密的结果："+ parseJWT("gu", ass));

//		base64EncodedKeyBytes
		BASE64Encoder base64Encoder = new BASE64Encoder();
		String encode = base64Encoder.encode("gulimall".getBytes());

		Claims claims = Jwts.parser() // 得到DefaultJwtParser
				.setSigningKey(encode) // 设置签名的秘钥
				.parseClaimsJws(ass).getBody();// 设置需要解析的jwt
//		if (claims.getExpiration() - LocalTime.)
		Integer exp = (Integer) claims.get("exp");
//		LocalDateTime.now().plusSeconds(expireIn);
//
//		new LocalDateTime().isBefore(claims.getExpiration());
//		1587876667937
		System.out.println(System.currentTimeMillis());
		System.out.println("************************");


	}

	/**
	 * 解密
	 * @param jsonWebToken
	 * @param base64Security
	 * @return
	 */
	public static Claims parseJWT(String jsonWebToken, String base64Security) {
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(base64Security.getBytes())
					.parseClaimsJws(jsonWebToken).getBody();
			return claims;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 前三个参数为自己用户token的一些信息比如id，权限，名称等。不要将隐私信息放入（大家都可以获取到）
	 * @param map
	 * @param base64Security
	 * @return
	 */
	public static String createJWT(Map<String, Object> map, String base64Security) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		//添加构成JWT的参数
		JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
				.setPayload(new Gson().toJson(map))
				.signWith(signatureAlgorithm,base64Security.getBytes()); //估计是第三段密钥
		//生成JWT
		return builder.compact();
	}


}
