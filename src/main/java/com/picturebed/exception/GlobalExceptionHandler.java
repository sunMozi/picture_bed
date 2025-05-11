package com.picturebed.exception;


import com.picturebed.common.response.ErrorResponse;
import com.picturebed.common.response.enums.ResponseCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

/**
 * @author moZiA
 * @date 2025/5/8 22:21
 * @description
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex,
      HttpServletRequest request) {

    ResponseCodeEnum codeEnum = ex.getErrorCode();
    System.out.println();
    String path = request.getRequestURI();

    ErrorResponse response = ErrorResponse.builder()
                                          .code(codeEnum.getCode())
                                          .message(ex.getMessage())
                                          .path(path)
                                          .build();

    log.warn("业务异常 ==> 路径: {} | 错误码: {} | 原因: {}", path, codeEnum.getCode(),
             codeEnum.getMsg());
    return ResponseEntity.status(codeEnum.getHttpStatus()).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex,
      HttpServletRequest request) {

    // 提取校验失败的具体信息
    String errorMsg = ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(fieldError -> fieldError.getField() + ": " +
                            fieldError.getDefaultMessage())
                        .collect(Collectors.joining("; "));

    String path = request.getRequestURI();
    ResponseCodeEnum codeEnum = ResponseCodeEnum.CODE_400;

    ErrorResponse response = ErrorResponse.builder()
                                          .message("参数校验失败: " + errorMsg)
                                          .path(path)
                                          .build();

    log.warn("参数校验异常 ==> 路径: {} | 错误详情: {}", path, errorMsg);
    return ResponseEntity.status(codeEnum.getHttpStatus()).body(response);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex,
      HttpServletRequest request) {

    ResponseCodeEnum codeEnum = ResponseCodeEnum.CODE_401;
    String path = request.getRequestURI();

    ErrorResponse response = ErrorResponse.builder()
                                          .code(codeEnum.getCode())
                                          .message("无权访问该资源")
                                          .path(path)
                                          .build();

    log.warn("权限异常 ==> 路径: {} | 原因: {}", path, ex.getMessage());
    return ResponseEntity.status(codeEnum.getHttpStatus()).body(response);
  }

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<ErrorResponse> handleSizeExceed(MaxUploadSizeExceededException ex,
      HttpServletRequest request) {
    ResponseCodeEnum codeEnum = ResponseCodeEnum.CODE_400;
    String path = request.getRequestURI();
    ErrorResponse response = ErrorResponse.builder()
                                          .code(codeEnum.getCode())
                                          .message("文件大小超过限制")
                                          .path(path)
                                          .build();

    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(response);
  }

  @ExceptionHandler(MultipartException.class)
  public ResponseEntity<ErrorResponse> handleMultipartError(MultipartException ex,
      HttpServletRequest request) {
    ResponseCodeEnum codeEnum = ResponseCodeEnum.CODE_400;
    String path = request.getRequestURI();
    ErrorResponse response = ErrorResponse.builder()
                                          .code(codeEnum.getCode())
                                          .message("文件上传失败")
                                          .path(path)
                                          .build();
    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(response);
  }


  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> missingServletRequestParameterException(Exception ex,
      HttpServletRequest request) {

    ResponseCodeEnum codeEnum = ResponseCodeEnum.CODE_400;
    String path = request.getRequestURI();
    ErrorResponse response = ErrorResponse.builder()
                                          .code(codeEnum.getCode())
                                          .message(ex.getMessage())
                                          .path(path)
                                          .build();

    log.error("未捕获异常 ==> 路径: {}", path, ex);  // 打印完整堆栈
    return ResponseEntity.status(codeEnum.getHttpStatus()).body(response);
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex,
      HttpServletRequest request) {

    ResponseCodeEnum codeEnum = ResponseCodeEnum.CODE_500;
    String path = request.getRequestURI();

    ErrorResponse response = ErrorResponse.builder()
                                          .code(codeEnum.getCode())
                                          .message("服务器内部错误，请稍后重试")
                                          .path(path)
                                          .build();

    log.error("未捕获异常 ==> 路径: {}", path, ex);  // 打印完整堆栈
    return ResponseEntity.status(codeEnum.getHttpStatus()).body(response);
  }


}