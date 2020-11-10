package com.peter.xiao.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * @author Eweee
 */
public class JwtUtil {

  /**
   * 定义过期时间
   */
  public static final long EXPIRATION_TIME = 30 * 60 * 1000;

  /**
   * jwt密钥
   */
  public static final String SERCET = "UhjIrUa*JiWCEPGQkX0E*Q%PUFS%v5rW";

  /**
   * 获得token
   *
   * @param userId 用户id
   * @return 成功信息
   */
  public static String sign(Integer userId) {
    try {
      // 设置过期时间
      Date date = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
      // 加密算法
      Algorithm algorithm = Algorithm.HMAC256(SERCET);
      // 反或token
      return JWT.create().withAudience(String.valueOf(userId)).withExpiresAt(date).sign(algorithm);
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
      return Integer.parseInt(token);
    } catch (JWTDecodeException e) {
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
    String token = sign(1222);
    System.out.println();
    Integer userId = getUserId(token);
  }
}
