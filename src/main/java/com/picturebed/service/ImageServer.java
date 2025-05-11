package com.picturebed.service;


import com.github.pagehelper.PageInfo;
import com.picturebed.model.entity.Image;
import java.util.List;

/**
 * @author moZiA
 * @date 2025/5/10 19:43
 * @description
 */
public interface ImageServer {


  PageInfo<Image> imageAll(Integer pageNum, Integer pageSize);
}