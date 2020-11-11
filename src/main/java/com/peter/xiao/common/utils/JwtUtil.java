package com.peter.xiao.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @author Eweee
 */
public class JwtUtil {

  /**
   * 定义过期时间
   */
  private static final long EXPIRATION_TIME = 30 * 60 * 1000;

  /** 签发者 */
  private static String ISSUER = "daiAdmin";

  /**
   * jwt密钥
   */
  private static final String SERCET = "UhjIrUa*JiWCEPGQkX0E*Q%PUFS%v5rW";

  /**
   * 获得token
   *
   * @param userId 用户id
   * @return 成功信息
   */
  public static String sign(Integer userId) {
    try {
      // 设置过期时间
      Long expirationTime = System.currentTimeMillis() + EXPIRATION_TIME;
      // 加密算法
      SignatureAlgorithm sign = SignatureAlgorithm.HS256;
      // 获得byte数组的密钥
      byte[] secretBytes = DatatypeConverter.parseBase64Binary(SERCET);
      String enUserId = EncryptUtils.aesEncrypt(String.valueOf(userId));
      // 创建JWT工厂
      JwtBuilder jwtBuilder =
          Jwts.builder()
              .setIssuer(ISSUER)
              .setExpiration(new Date(expirationTime))
              .setIssuedAt(new Date())
              .setAudience(enUserId)
              .signWith(sign,secretBytes);
      // 返回token
      return jwtBuilder.compact();
    } catch (Exception e) {
      // 出现异常返回null
      return null;
    }
  }

  /**
   * 根据token获得用户id
   *
   * @param token token信息
   * @return 用户id
   */
  public static Integer getUserId(String token) {
    try {
      // 获得用户id
      String userId = JWT.decode(token).getAudience().get(0);
      userId = EncryptUtils.aesDecrypt(userId);
      return Integer.parseInt(userId);
    } catch (JWTDecodeException e) {
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 校验Token
   * @param token token
   * @return 校验结果
   */
  public static boolean checkSign(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(SERCET);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(token);
      return true;
    } catch (JWTVerificationException exception) {
      throw new RuntimeException("token无效，请重新获取");
    }
  }

  public static void main(String[] args) {
    Integer id = 222;
    System.out.println(sign(222));
    System.out.println(getUserId(sign(222)));
  }
}
