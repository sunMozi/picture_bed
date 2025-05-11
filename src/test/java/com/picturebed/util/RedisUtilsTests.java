package com.picturebed.util;

import com.picturebed.model.entity.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisUtilsTests {

  @Resource
  private RedisUtils<User> redisUtils;


  @Test
  void set() {
    User user = User.builder()
                    .id(1L)
                    .role("管理员")
                    .password("123456")
                    .username("admin")
                    .email("2138844072@qq.com")
                    .build();
    redisUtils.set("user", user);
  }

  @Test
  void setWithExpire() {
    User user = User.builder()
                    .id(1L)
                    .role("管理员")
                    .password("123456")
                    .username("admin")
                    .email("2138844072@qq.com")
                    .build();
    redisUtils.setWithExpire("user", user ,20);
  }

  @Test
  void testSetWithExpire() {
  }

  @Test
  void delete() {
    redisUtils.delete("user");
  }

  @Test
  void get() {
  }
}