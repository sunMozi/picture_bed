package com.picturebed.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.picturebed.common.response.enums.ResponseCodeEnum;
import com.picturebed.exception.BusinessException;
import java.util.Calendar;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * @author moZiA
 * @date 2025/5/11 13:20
 * @description
 */
@Component
public class JwtUtils {

  private static final String SIGN_KEY = "SVRIRUlNQQ==";
  private static final Long EXPIRE = 43200000L;

  public String generateToken(Map<String, String> map) {
    Calendar instance = Calendar.getInstance();
    instance.add(Calendar.DATE, 7); // 默认7天过期

    JWTCreator.Builder builder = JWT.create();
    map.forEach(builder::withClaim);

    return builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(SIGN_KEY));
  }

  public String getEmailFromToken(String token) {
    try {
      DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SIGN_KEY)).build().verify(token);
      return jwt.getClaim("email").asString();
    } catch (JWTVerificationException e) {
      throw new BusinessException(ResponseCodeEnum.CODE_403, "认证已经过期,重新登录");
    }
  }
}
