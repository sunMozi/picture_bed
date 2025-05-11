package com.picturebed.common.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author moZiA
 * @date 2025/5/8 21:46
 * @description
 */

@Data
public class AjaxResult<T> {

  // 状态码（默认200表示成功）
  private int code;

  // 是否成功标识
  private boolean success;

  // 返回消息
  private String msg;

  // 返回数据
  private T data;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime timestamp = LocalDateTime.now();

  private AjaxResult() {
  }


  public static <T> AjaxResult<T> success() {
    AjaxResult<T> result = new AjaxResult<>();
    result.setSuccess(true);
    result.setCode(200);
    result.setMsg("操作成功");
    return result;
  }

  public static <T> AjaxResult<T> success(T data) {
    AjaxResult<T> result = success();
    result.setData(data);
    return result;
  }


  public static <T> AjaxResult<T> error(String message) {
    return error(500, message);
  }


  public static <T> AjaxResult<T> error(int code, String message) {
    AjaxResult<T> result = new AjaxResult<>();
    result.setSuccess(false);
    result.setCode(code);
    result.setMsg(message);
    return result;
  }


  public AjaxResult<T> message(String message) {
    this.msg = message;
    return this;
  }


  public AjaxResult<T> code(int code) {
    this.code = code;
    return this;
  }


}