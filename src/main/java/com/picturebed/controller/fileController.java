package com.picturebed.controller;


import com.picturebed.common.response.AjaxResult;
import com.picturebed.common.response.enums.ResponseCodeEnum;
import com.picturebed.config.AppConfig;
import com.picturebed.exception.BusinessException;
import com.picturebed.service.fileServer;
import com.picturebed.util.ThumbnailUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author moZiA
 * @date 2025/5/9 11:28
 * @description
 */
@RestController
@RequestMapping("file")
public class fileController {

  @Resource
  private AppConfig appConfig;

  @Resource
  private fileServer fileServer;

  @PostMapping("/upload")
  public AjaxResult<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file,
      @RequestParam(value = "hashSha256") String hashSha256,
      @RequestParam(value = "isPublic", defaultValue = "false") boolean isPublic

  ) {
    //TODO 获取前端穿过来的hash
    return AjaxResult.success(fileServer.uploadImage(file, hashSha256, isPublic));
  }


  @GetMapping("/image/{imageName}")
  public void getOriginalImage(@PathVariable String imageName, HttpServletResponse response)
      throws IOException {
    // 1. 文件名格式校验
    validateFileName(imageName);

    // 2. 构建安全路径
    Path basePath = Paths.get(appConfig.getImage_dir()).normalize().toAbsolutePath();
    Path imagePath = basePath.resolve(imageName).normalize();

    // 3. 路径安全检查
    if (!imagePath.startsWith(basePath)) {
      throw new BusinessException(ResponseCodeEnum.CODE_403, "非法路径访问");
    }

    // 4. 检查文件存在性
    if (!Files.exists(imagePath)) {
      throw new BusinessException(ResponseCodeEnum.CODE_404, "图片不存在");
    }

    // 5. 自动识别MIME类型
    String mimeType = Files.probeContentType(imagePath);
    if (mimeType == null) {
      mimeType = determineContentType(imageName);
    }

    // 6. 流式传输文件
    response.setContentType(mimeType);
    response.setHeader(HttpHeaders.CACHE_CONTROL, "public, max-age=604800");
    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageName + "\"");

    try (InputStream is = Files.newInputStream(imagePath)) {
      is.transferTo(response.getOutputStream());
    }
  }


  @GetMapping("/thumb/{imageName}")
  public void getThumbnail(@PathVariable String imageName,
      @RequestParam(defaultValue = "200") int width, @RequestParam(defaultValue = "200") int height,
      @RequestParam(defaultValue = "true") boolean keepRatio, HttpServletResponse response)
      throws IOException {

    // 安全校验
    validateFileName(imageName);
    width = Math.min(width, 2048);
    height = Math.min(height, 2048);

    Path imagePath = Paths.get(appConfig.getImage_dir(), imageName);
    if (Files.notExists(imagePath)) {
      throw new BusinessException(ResponseCodeEnum.CODE_404, "文件不存在");
    }
    byte[] processed = ThumbnailUtil.generateThumbnail(Files.readAllBytes(imagePath), width, height,
                                                       keepRatio);
    response.setContentType("image/jpeg");
    response.setHeader("Cache-Control", "public, max-age=604800"); // 7天缓存
    response.getOutputStream().write(processed);
  }

  // 文件名安全校验
  private void validateFileName(String name) {
    if (!name.matches("^[a-zA-Z0-9_-]+\\.(jpg|png|webp)$")) {
      throw new BusinessException(ResponseCodeEnum.CODE_400, "非法的文件名格式");
    }
  }


  // MIME类型判断方法
  private String determineContentType(String filename) {
    String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    switch (extension) {
      case "jpg":
      case "jpeg":
        return "image/jpeg";
      case "png":
        return "image/png";
      case "webp":
        return "image/webp";
      default:
        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }
  }

}