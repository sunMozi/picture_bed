package com.picturebed.controller;


import com.picturebed.common.response.AjaxResult;
import com.picturebed.common.response.RateLimit;
import com.picturebed.common.response.enums.ResponseCodeEnum;
import com.picturebed.exception.BusinessException;
import com.picturebed.model.dto.ImageDto;
import com.picturebed.service.ImageServer;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author moZiA
 * @date 2025/5/10 19:42
 * @description
 */
@Tag(name = "接口")
@RestController
@RequestMapping("img")
public class ImageController {


  @Resource
  private ImageServer imageServer;

  @PutMapping("/update")
  public AjaxResult<String> updateImage(@RequestBody ImageDto imageDto) {
    imageServer.putImage(imageDto);
    return AjaxResult.success();
  }

  @GetMapping("/thumb/{imageName}")
  @RateLimit(key = "user_api", limit = 20, timeout = 10)
  public void getThumbnail(@PathVariable String imageName, HttpServletResponse response) {
    imageServer.getImageByName(imageName, response, 0.1);
  }

  @RateLimit(key = "user_api", limit = 5, timeout = 10)
  @GetMapping("/image/{imageName}")
  public void getOriginalImage(@PathVariable String imageName, HttpServletResponse response) {
    imageServer.getImageByName(imageName, response, 1.0);
  }


  @GetMapping("all")
  public AjaxResult<Map<String, Object>> imageAll(@RequestParam(required = false) String name,
      @RequestParam(defaultValue = "1") int pageNum,
      @RequestParam(defaultValue = "10") int pageSize) {
    return AjaxResult.success(imageServer.imageAll(name, pageNum, pageSize));
  }


  private void validateFileName(String name) {
    if (!name.matches("^[a-zA-Z0-9_-]+\\.(jpg|png|webp)$")) {
      throw new BusinessException(ResponseCodeEnum.CODE_400, "非法的文件名格式");
    }
  }


}