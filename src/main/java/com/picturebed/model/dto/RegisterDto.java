package com.picturebed.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * @author moZiA
 * @date 2025/5/8 22:03
 * @description
 */
@Data
public class RegisterDto {

  @NotBlank
  @Size(min = 3, max = 20, message = "用户名长度需在3-20个字符之间")
  private String username;

  @NotBlank
  @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
  private String email;

  @NotBlank
  @Size(min = 6, max = 40)
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).+$",
      message = "密码必须包含字母和数字")
  @Setter(AccessLevel.NONE)
  private String password;
}