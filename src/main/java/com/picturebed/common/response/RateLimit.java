package com.picturebed.common.response;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

  // 限流唯一标识（例如接口名、用户ID等）
  String key() default "";

  // 时间窗口内的最大请求数（例如 10 次/秒）
  int limit() default 10;

  // 时间窗口长度（单位：秒）
  int timeout() default 60;
}