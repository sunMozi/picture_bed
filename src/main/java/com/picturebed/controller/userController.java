package com.picturebed.controller;


import com.picturebed.common.response.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author moZiA
 * @date 2025/5/8 21:54
 * @description
 */
@RestController

public class userController {

  @GetMapping("/userinfo")
  public AjaxResult<String> userinfo() {
    return AjaxResult.success("userInfo");
  }
}