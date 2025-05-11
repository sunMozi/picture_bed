package com.picturebed.common.response;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author moZiA
 * @date 2025/5/8 22:21
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

  private int code;        // 业务错误码
  private String message;  // 错误描述
  private String path;    // 请求路径
  @Builder.Default
  private LocalDateTime timestamp = LocalDateTime.now();
}