package com.picturebed.service.impl;


import com.picturebed.common.response.enums.ResponseCodeEnum;
import com.picturebed.exception.BusinessException;
import com.picturebed.mapper.userMapper;
import com.picturebed.model.dto.LoginDto;
import com.picturebed.model.dto.RegisterDto;
import com.picturebed.model.entity.User;
import com.picturebed.service.authServer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author moZiA
 * @date 2025/5/8 22:06
 * @description
 */
@Service
public class authServerImpl implements authServer {

  @Resource
  private userMapper userMapper;

  @Override
  public void register(RegisterDto registerDto) {
    System.out.println(registerDto);
    User user = userMapper.selectUserByEmail(registerDto.getEmail());
    if (user != null) {
      throw new BusinessException(ResponseCodeEnum.CODE_601, "邮箱已经存在");
    }
    User buildUser = User.builder()
                         .username(registerDto.getUsername())
                         .password(registerDto.getPassword())
                         .email(registerDto.getEmail())
                         .role("user")
                         .build();
    // TODO 密码md5 加密
    userMapper.insertUser(buildUser);

  }

  @Override
  public void login(LoginDto loginDto) {

    // TODO 登录md5 密码 加密
    User user = userMapper.selectUserByEmailAndPassWord(loginDto.getEmail(),
                                                        loginDto.getPassword());
    if (user == null) {
      throw new BusinessException(ResponseCodeEnum.CODE_400, "账户或密码错误");
    }

    //TODO 登录成功返回 Token , 将 token 保存在 redis
  }
}