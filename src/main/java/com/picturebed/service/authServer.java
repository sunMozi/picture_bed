package com.picturebed.service;


import com.picturebed.model.dto.LoginDto;
import com.picturebed.model.dto.RegisterDto;

/**
 * @author moZiA
 * @date 2025/5/8 22:06
 * @description
 */
public interface authServer {

  void register(RegisterDto registerDto);

  void login(LoginDto loginDto);
}