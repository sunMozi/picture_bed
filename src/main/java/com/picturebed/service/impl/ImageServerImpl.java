package com.picturebed.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.picturebed.common.response.enums.ResponseCodeEnum;
import com.picturebed.config.AppConfig;
import com.picturebed.exception.BusinessException;
import com.picturebed.mapper.ImageMapper;
import com.picturebed.model.dto.ImageDto;
import com.picturebed.model.entity.Image;
import com.picturebed.service.ImageServer;
import com.picturebed.util.ThumbnailUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * @author moZiA
 * @date 2025/5/10 19:44
 * @description
 */
@Service
public class ImageServerImpl implements ImageServer {


  @Resource
  private ImageMapper imageMapper;

  @Resource
  private AppConfig appConfig;


  @Override
  public Map<String, Object> imageAll(String name, Integer pageNum, Integer pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<Image> images = imageMapper.selectImageList(name);
    PageInfo<Image> imagePageInfo = new PageInfo<>(images);
    Map<String, Object> map = new HashMap<>();
    map.put("total", imagePageInfo.getTotal());
    map.put("list", imagePageInfo.getList());
    return map;
  }

  @Override
  public void getImageByName(String imageName, HttpServletResponse response, Double thumb) {
    Image image = imageMapper.selectImageByName(imageName);
    System.out.println(image);
    if (image == null) {
      throw new BusinessException(ResponseCodeEnum.CODE_404, "图片不存在");
    }
    Path imagePath = Paths.get(
        appConfig.getImage_dir() + image.getStoredName() + "." + image.getFileType());

    System.out.println(imagePath);

    if (Files.notExists(imagePath)) {
      throw new BusinessException(ResponseCodeEnum.CODE_404, "文件不存在");
    }
    byte[] processed = null;
    try {
      processed = ThumbnailUtil.generateThumbnail(Files.readAllBytes(imagePath), 2048, 2048, true,
                                                  thumb);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    response.setContentType("image/jpeg");
    response.setHeader("Cache-Control", "public, max-age=604800"); // 7天缓存
    try {
      response.getOutputStream().write(processed);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void putImage(ImageDto imageDto) {
    Image image = imageMapper.selectImageById(imageDto.getId());
    if (image == null) {
      throw new BusinessException(ResponseCodeEnum.CODE_404, "图片不存在");
    }
    imageDto.setUpdateDate(LocalDateTime.now());
    imageMapper.updateImage(imageDto);

  }
}