package com.picturebed.model.entity;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author moZiA
 * @date 2025/5/8 22:00
 * @description
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

  private Long id;
  private String username;
  private String password;
  private String email;
  private String role;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}