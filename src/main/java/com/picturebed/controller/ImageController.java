package com.picturebed.controller;


import com.github.pagehelper.PageInfo;
import com.picturebed.common.response.AjaxResult;
import com.picturebed.model.entity.Image;
import com.picturebed.service.ImageServer;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author moZiA
 * @date 2025/5/10 19:42
 * @description
 */
@RestController
@RequestMapping("img")
public class ImageController {


  @Resource
  private ImageServer imageServer;

  @GetMapping("all")
  public AjaxResult<PageInfo<Image>> imageAll(@RequestParam(defaultValue = "1") int pageNum,
      @RequestParam(defaultValue = "10") int pageSize) {
    return AjaxResult.success(imageServer.imageAll(pageNum, pageSize));
  }


}