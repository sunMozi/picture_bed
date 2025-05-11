package com.picturebed.mapper;


import com.picturebed.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author moZiA
 * @date 2025/5/8 22:08
 * @description
 */
@Mapper
public interface userMapper {

  User selectUserByUsernameAndPassword(@Param("username") String username,
      @Param("password") String password);

  User selectUserByEmail(String email);

  void insertUser(User user);

  User selectUserByEmailAndPassWord(@Param("email") String email,
      @Param("password") String password);


}