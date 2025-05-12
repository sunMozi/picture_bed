package com.picturebed.service.impl;


import com.picturebed.common.response.enums.ResponseCodeEnum;
import com.picturebed.config.AppConfig;
import com.picturebed.exception.BusinessException;
import com.picturebed.mapper.ImageMapper;
import com.picturebed.model.entity.Image;
import com.picturebed.service.fileServer;
import jakarta.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author moZiA
 * @date 2025/5/9 12:26
 * @description
 */
@Service
public class fileServerImpl implements fileServer {

  @Resource
  private AppConfig appConfig;

  @Resource
  private ImageMapper imageMapper;


  @Override
  public Map<String, String> uploadImage(MultipartFile file, String hashSha256, Boolean isPublic) {
    //  基础校验
    if (file.isEmpty()) {
      throw new BusinessException(ResponseCodeEnum.CODE_400, "上传文件不能为空");
    }

    // 文件 安全 校验
    String fileType = validateFile(file);

    //  文件名安全处理
    String original = file.getOriginalFilename();

    try {
      // 构建存储路径
      String uuid = UUID.randomUUID().toString();

      Path uploadDir = Paths.get(appConfig.getImage_dir()).normalize().toAbsolutePath();
      Path targetPath = Path.of(uploadDir.resolve(uuid).normalize() + "." + fileType);

      //  路径安全检查
      if (!targetPath.startsWith(uploadDir)) {
        throw new BusinessException(ResponseCodeEnum.CODE_403, "非法存储路径");
      }
      // 处理文件重名
      Image originalImage = imageMapper.selectImageByOriginalName(original);
      if (originalImage != null) {
        throw new BusinessException(ResponseCodeEnum.CODE_403, "文件名称重复");
      }

      //  保存文件
      Files.createDirectories(uploadDir); // 确保目录存在
      file.transferTo(targetPath);
      //TODO 根据token 获取 user bean

      Image image = Image.builder()
                         .userId(1L)
                         .originalName(original)
                         .storedName(uuid.toString())
                         .fileSize(file.getSize())
                         .fileType(fileType)
                         .hashSha256(hashSha256)
                         .isPublic(isPublic)
                         .createDate(LocalDateTime.now())
                         .updateDate(LocalDateTime.now())
                         .storagePath(uploadDir.toString())
                         .build();

      //TODO 更新数据库数据
      Integer i = imageMapper.insertImage(image);

      Map<String, String> map = new HashMap<>();
      map.put("filename", original);
      map.put("size", String.valueOf(file.getSize()));
      map.put("path", targetPath.toString());
      map.put("url", "/file/image/" + original);

      return map;
    } catch (IOException e) {
      throw new BusinessException(ResponseCodeEnum.CODE_500, "文件存储失败");
    }

  }

  private String validateFile(MultipartFile file) {
    String filename = file.getOriginalFilename();
    assert FilenameUtils.getExtension(filename) != null;
    String extension = FilenameUtils.getExtension(filename).toLowerCase();
    return extension;
  }


}