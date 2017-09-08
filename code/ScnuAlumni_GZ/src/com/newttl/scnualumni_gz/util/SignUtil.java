package com.newttl.scnualumni_gz.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 校验工具类
 * @author lgc
 *
 */
public class SignUtil {
	
	/**
	 * Token可由开发者可以任意填写，用作生成签名（该Token会和接口URL中包含的Token进行比对，从而验证安全性）
	 * 比如这里我将Token设置为lgc1
	 */
	private final String TOKEN="lgc1";

	/**
	 * 排序方法
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	 public static String sort(String token, String timestamp, String nonce) {
     	String[] strArray = {token, timestamp, nonce};
     	Arrays.sort(strArray);
     	StringBuilder sb = new StringBuilder();
     	for (String str : strArray) {
             sb.append(str);
         }
         return sb.toString();
     }
	 
	 /**
	  * 将字符串进行sha1加密
   	  * @param str 需要加密的字符串
	  * @return 加密后的内容
	  */
      public static String sha1(String str) {
          try {
              MessageDigest digest = MessageDigest.getInstance("SHA-1");
              digest.update(str.getBytes());
              byte messageDigest[] = digest.digest();
              // Create Hex String
              StringBuffer hexString = new StringBuffer();
              // 字节数组转换为 十六进制 数
              for (int i = 0; i < messageDigest.length; i++) {
                  String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                  if (shaHex.length() < 2) {
                      hexString.append(0);
                  }
                  hexString.append(shaHex);
              }
              return hexString.toString();
  
          } catch (NoSuchAlgorithmException e) {
              e.printStackTrace();
          }
         return "";
     }
}
