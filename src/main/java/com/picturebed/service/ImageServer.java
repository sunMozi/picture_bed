package com.picturebed.service;


import com.picturebed.model.dto.ImageDto;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author moZiA
 * @date 2025/5/10 19:43
 * @description
 */
public interface ImageServer {


  Map<String, Object> imageAll(String name, Integer pageNum, Integer pageSize);


  void getImageByName(String imageName, HttpServletResponse response, Double thumb);

  void putImage(ImageDto imageDto);
}