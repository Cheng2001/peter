package com.peter.xiao.common.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @author Eweee
 */
public class Aes {

  /**
   * 加密需要的密码
   */
  private static final String PASSWORD = "UhjIrUa*JiWCEPGQ";

  /**
   * 使用Aes加密数据
   * @param content 要加密的内容
   * @return 加密后的信息
   */
  public static String encrypt(String content){
    try{
      // AES生产者
      KeyGenerator kgen = KeyGenerator.getInstance("AES");
      // 生成256位长度key
      kgen.init(256,new SecureRandom(PASSWORD.getBytes()));
      // 生成密钥
      SecretKey secretKey = kgen.generateKey();
      // 返回基本格式密钥
      byte[] enCode = secretKey.getEncoded();
      // 转换成AES的专用密钥
      SecretKeySpec secretKeySpec = new SecretKeySpec(enCode, "AES");
      // 创建密码器
      Cipher cipher = Cipher.getInstance("AES");
      byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
      // 设置为加密模式
      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
      // 加密密文
      byte[] result = cipher.doFinal(byteContent);
      return Arrays.toString(result);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 解密AES加密过的字符串
   * @param content 加密过的字符串
   * @return 明文
   */
  public static String decrypt(byte[] content){
    try{
      // 创建AES的key生产者
      KeyGenerator kgen = KeyGenerator.getInstance("AES");
      kgen.init(256,new SecureRandom(PASSWORD.getBytes()));
      // 根据用户密码生成一个密钥
      SecretKey secretKey = kgen.generateKey();
      // 返回基本编码格式的密钥
      byte[] enCodeFormat = secretKey.getEncoded();
      // 转换为AES专用密钥
      SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
      // 创建密码器
      Cipher cipher = Cipher.getInstance("AES");
      // 初始化为解密模式的密码器
      cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
      // 解码
      byte[] result = cipher.doFinal(content);
      // 返回数据
      return Arrays.toString(result);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
      e.printStackTrace();
    }
    return null;
  }

}
