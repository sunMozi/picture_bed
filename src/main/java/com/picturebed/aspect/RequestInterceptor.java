package com.picturebed.aspect;


import com.picturebed.common.response.constants.Constants;
import com.picturebed.common.response.enums.ResponseCodeEnum;
import com.picturebed.exception.BusinessException;
import com.picturebed.model.entity.User;
import com.picturebed.util.JwtUtils;
import com.picturebed.util.RedisUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author moZiA
 * @date 2025/4/23 15:43
 * @description
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);
  @Resource
  private JwtUtils jwtUtils;

  @Resource
  private RedisUtils<User> redisUtils;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    if (HttpMethod.OPTIONS.toString().equalsIgnoreCase(request.getMethod())) {
      return true;
    }
    String token = request.getHeader("token");


    if (!StringUtils.hasLength(token)) {
      throw new BusinessException(ResponseCodeEnum.CODE_403, "参数异常,请联系管理员");
    }
    String email = jwtUtils.getEmailFromToken(token);
    User user = redisUtils.get(Constants.REDIS_KEY_USER_CODE + email);
    if (user == null) {
      throw new BusinessException(ResponseCodeEnum.CODE_403, "token 错误或已经过期");
    }
    return true;
  }
}