package com.picturebed.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author moZiA
 * @date 2025/5/8 22:03
 * @description
 */
@Getter
public class LoginDto {

  @NotBlank
  @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
  private String email;

  @NotBlank
  @Size(min = 6, max = 40)
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).+$", message = "密码必须包含字母和数字")
  @Setter(AccessLevel.NONE)
  private String password;
}