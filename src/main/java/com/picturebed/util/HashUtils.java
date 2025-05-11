package com.picturebed.util;


import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author moZiA
 * @date 2025/5/10 20:19
 * @description
 */
public class HashUtils {

  public static String calculateSHA256(MultipartFile file) throws IOException {
    try (InputStream inputStream = file.getInputStream()) {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] buffer = new byte[8192];
      int bytesRead;
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        digest.update(buffer, 0, bytesRead);
      }
      byte[] hashBytes = digest.digest();
      return bytesToHex(hashBytes);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("SHA-256 algorithm not found", e);
    }
  }

  private static String bytesToHex(byte[] bytes) {
    StringBuilder hexString = new StringBuilder();
    for (byte b : bytes) {
      String hex = String.format("%02x", b);
      hexString.append(hex);
    }
    return hexString.toString();
  }
}