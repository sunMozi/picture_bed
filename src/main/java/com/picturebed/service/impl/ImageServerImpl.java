package com.picturebed.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.picturebed.mapper.ImageMapper;
import com.picturebed.model.entity.Image;
import com.picturebed.service.ImageServer;
import jakarta.annotation.Resource;
import java.util.List;
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


  @Override
  public PageInfo<Image> imageAll(Integer pageNum, Integer pageSize) {
    PageHelper.startPage(pageNum, pageSize);
    List<Image> images = imageMapper.selectImageList();
    return new PageInfo<>(images);
  }
}