package com.picturebed.controller;


import com.picturebed.common.response.AjaxResult;
import com.picturebed.config.AppConfig;
import com.picturebed.service.fileServer;
import jakarta.annotation.Resource;
import java.util.Map;
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


}