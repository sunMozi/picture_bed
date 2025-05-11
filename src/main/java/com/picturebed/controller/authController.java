package com.picturebed.controller;


import com.picturebed.common.response.AjaxResult;
import com.picturebed.model.dto.LoginDto;
import com.picturebed.model.dto.RegisterDto;
import com.picturebed.service.authServer;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author moZiA
 * @date 2025/5/8 21:53
 * @description
 */
@RestController
@RequestMapping("/auth")
public class authController {

  @Resource
  private authServer authServer;


  @PostMapping("/register")
  public AjaxResult<String> register(@Valid @RequestBody RegisterDto registerDto) {
    authServer.register(registerDto);
    return AjaxResult.success("注册成功");
  }

  @PostMapping("/login")
  public AjaxResult<String> login(@Valid @RequestBody LoginDto loginDto) {
    authServer.login(loginDto);
    return AjaxResult.success("登录成功");
  }

}