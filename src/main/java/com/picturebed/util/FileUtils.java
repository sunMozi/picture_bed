package com.picturebed.util;


/**
 * @author moZiA
 * @date 2025/5/9 12:03
 * @description
 */
public class FileUtils {

  /**
   * 安全获取文件扩展名
   * @param filename 原始文件名（需已通过安全校验）
   * @return 小写的文件扩展名（不含点），无扩展名返回空字符串
   */
  public static String getFileExtension(String filename) {
    if (filename == null || filename.isEmpty()) {
      return "";
    }

    // 处理包含路径的情况
    int lastSeparator = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));
    String name = (lastSeparator != -1) ? filename.substring(lastSeparator + 1) : filename;

    int dotIndex = name.lastIndexOf('.');
    if (dotIndex == -1 || dotIndex == 0 || dotIndex == name.length() - 1) {
      return "";
    }

    return name.substring(dotIndex + 1).toLowerCase();
  }
}