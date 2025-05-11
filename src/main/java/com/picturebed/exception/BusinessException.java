package com.picturebed.exception;


import com.picturebed.common.response.enums.ResponseCodeEnum;
import lombok.Getter;

/**
 * @author moZiA
 * @date 2025/5/8 22:20
 * @description
 */
@Getter
public class BusinessException extends RuntimeException {

  private final ResponseCodeEnum errorCode;

  public BusinessException(ResponseCodeEnum errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public static BusinessException of(ResponseCodeEnum errorCode, String message) {
    return new BusinessException(errorCode, message);
  }

}