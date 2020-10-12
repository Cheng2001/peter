package com.peter.xiao.common.utils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/** @author 10311 处理用户密码加密类 */
public class Encrypted {

  /** 私有静态对象，项目加载时直接实例化 */
  private static final Encrypted ENCRYPTED_PASSWORD = new Encrypted();

  /** 避免手动实例化 */
  private Encrypted() {}

  /**
   * 提供单例方法
   *
   * @return 返回单例对象
   */
  public static Encrypted getInstance() {
    return ENCRYPTED_PASSWORD;
  }

  /**
   * 要加密的字符串
   *
   * @param oldStr 电话号码
   * @return 加密过的Token
   * @throws NoSuchAlgorithmException 需要调用处处理异常信息
   */
  public synchronized String encryption(String oldStr, int salt) throws NoSuchAlgorithmException {
    // 初始化Token
    String token = oldStr;
    // 加密盐次数
    for (int i = 0; i < salt; i++) {
      token = changeMD5(token);
    }
    return token;
  }

  /**
   * 执行加密发方法
   *
   * @param oldStr 加密前字符串
   * @return 加密后的字符串
   * @throws NoSuchAlgorithmException 调用处处理异常
   */
  private synchronized String changeMD5(String oldStr) throws NoSuchAlgorithmException {
    // 生成加密util工具类
    MessageDigest md = MessageDigest.getInstance("MD5");
    // 将要加密的字符串传唤为字节数组
    byte[] secretBytes = oldStr.getBytes();
    md.update(secretBytes);
    // 转换并返回结果，（字节数组，16位）
    byte[] resultBytes = md.digest();
    // 转换为String并返回
    return byteToString(resultBytes);
  }

  /**
   * 将字节数组转换成16进制字符串
   *
   * @param byteArray 要转换的字节数组
   * @return 转换成的16进制字符串
   */
  private synchronized String byteToString(byte[] byteArray) {
    // 首先初始化一个字符数组，用来存放每个16进制字符
    char[] hexDigits = {
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };
    // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
    char[] resultCharArray = new char[byteArray.length * 2];
    // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
    int index = 0;
    for (byte b : byteArray) {
      resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
      resultCharArray[index++] = hexDigits[b & 0xf];
    }
    // 字符数组组合成字符串返回
    return new String(resultCharArray);
  }

/**
 * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
 * @param encryptText 被签名的字符串
 * @param encryptKey  密钥
 * @return
 * @throws Exception
 */
  public String hmacSHA1Encrypt(String encryptText, String encryptKey)throws Exception{
    final String MAC_HOME = "HmacSHA1";
    final String ENCODING = "UTF-8";
    byte[] data = encryptKey.getBytes(ENCODING);
    //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
    SecretKey secretKey = new SecretKeySpec(data, MAC_HOME);
    // 生成一个指定Mac算法的Mac对象
    Mac mac = Mac.getInstance(MAC_HOME);
    // 用给定密钥初始化Mac对象
    mac.init(secretKey);
    byte[] text = encryptText.getBytes(ENCODING);
    // 完成mac操作
    return Arrays.toString(mac.doFinal(text));
  }
}
