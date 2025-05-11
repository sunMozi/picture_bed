package com.picturebed.util;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * @author moZiA
 * @date 2025/5/9 11:37
 * @description
 */
public class ThumbnailUtil {

  // 通用缩略图生成方法
  public static byte[] generateThumbnail(byte[] original, int width, int height, boolean keepAspect)
      throws IOException {
    try (
        InputStream is = new ByteArrayInputStream(original);
        ByteArrayOutputStream os = new ByteArrayOutputStream()
    ) {

      Thumbnails.Builder<?> builder = Thumbnails.of(is).outputFormat("jpg").outputQuality(0.85);

      if (keepAspect) {
        builder.size(width, height).keepAspectRatio(true);
      } else {
        builder.forceSize(width, height);
      }

      builder.toOutputStream(os);
      return os.toByteArray();
    }
  }

  // 带水印的缩略图
  public static byte[] watermarkImage(byte[] original, String watermarkPath) throws IOException {
    try (
        InputStream is = new ByteArrayInputStream(original);
        InputStream watermarkStream = new FileInputStream(watermarkPath);
        ByteArrayOutputStream os = new ByteArrayOutputStream()
    ) {

      BufferedImage watermark = ImageIO.read(watermarkStream);

      Thumbnails.of(is)
                .scale(1)
                .watermark(Positions.BOTTOM_RIGHT, watermark, 0.5f)
                .toOutputStream(os);

      return os.toByteArray();
    }
  }
}