package com.picturebed.service;


import com.picturebed.model.dto.LoginDto;
import com.picturebed.model.dto.RegisterDto;
import java.util.HashMap;

/**
 * @author moZiA
 * @date 2025/5/8 22:06
 * @description
 */
public interface authServer {

  void register(RegisterDto registerDto);

  HashMap<String, Object> login(LoginDto loginDto);
}