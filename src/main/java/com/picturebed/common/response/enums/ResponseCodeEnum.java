package com.picturebed.common.response.enums;


import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author moZiA
 * @date 2025/5/8 22:49
 * @description
 */
// 错误码枚举
@Getter
public enum ResponseCodeEnum {
  // 成功状态
  CODE_200(200, "请求成功", HttpStatus.OK),

  // 客户端错误
  CODE_400(400, "请求参数错误", HttpStatus.BAD_REQUEST),
  CODE_401(401, "未授权访问", HttpStatus.UNAUTHORIZED),
  CODE_403(403, "权限校验错误", HttpStatus.FORBIDDEN),
  CODE_404(404, "请求地址不存在", HttpStatus.NOT_FOUND),
  CODE_409(409, "数据冲突", HttpStatus.CONFLICT),

  // 业务错误（6xx系列）
  CODE_601(601, "信息已经存在", HttpStatus.CONFLICT),
  CODE_602(602, "发送失败", HttpStatus.INTERNAL_SERVER_ERROR),

  // 服务端错误
  CODE_500(500, "服务器内部错误", HttpStatus.INTERNAL_SERVER_ERROR);

  private final int code;
  private final String msg;
  // 2. 直接通过枚举值获取HTTP状态码
  private final HttpStatus httpStatus;

  ResponseCodeEnum(int code, String msg, HttpStatus httpStatus) {
    this.code = code;
    this.msg = msg;
    this.httpStatus = httpStatus;
  }

}