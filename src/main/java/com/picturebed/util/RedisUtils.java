package com.picturebed.util;

import java.util.concurrent.TimeUnit;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @className: RedisUtils
 * @author: moZi
 * @date: 2024/3/27 13:03
 */


@Component("redisUtils")
public class RedisUtils<V> {

  private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

  private final RedisTemplate<String, V> redisTemplate;

  @Autowired
  public RedisUtils(RedisTemplate<String, V> redisTemplate) {
    this.redisTemplate = redisTemplate;
    logger.debug("RedisUtils initialized with RedisTemplate: {}", redisTemplate);
  }


  /**
   * 普通缓存放入
   * @param key 键
   * @param value 值
   */
  public void set(String key, V value) {
    try {
      redisTemplate.opsForValue().set(key, value);
      logger.info("Redis SET成功 - Key: {}", key);
    } catch (Exception e) {
      logger.error("Redis SET失败 - Key: {}", key, e);
      throw new RuntimeException("Redis操作失败", e);
    }
  }

  /**
   * 普通缓存放入并设置过期时间
   * @param key 键
   * @param value 值
   * @param timeout 时间（默认单位：秒）
   */
  public void setWithExpire(String key, V value, long timeout) {
    redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
  }

  /**
   * 带时间单位的缓存设置
   * @param key 键
   * @param value 值
   * @param timeout 时间
   * @param unit 时间单位
   */
  public void setWithExpire(String key, V value, long timeout, TimeUnit unit) {
    try {
      redisTemplate.opsForValue().set(key, value, timeout, unit);
      logger.info("Redis SETEX成功 - Key: {}, Timeout: {}{}", key, timeout, unit);
    } catch (Exception e) {
      logger.error("Redis SETEX失败 - Key: {}", key, e);
      throw new RuntimeException("Redis操作失败", e);
    }
  }

  /**
   * 删除缓存
   * @param key 键
   * @return 是否删除成功
   */
  public boolean delete(String key) {
    try {
      boolean success = redisTemplate.delete(key);
      if (success) {
        logger.info("Redis DEL成功 - Key: {}", key);
      } else {
        logger.warn("Redis DEL失败 - Key不存在: {}", key);
      }
      return success;
    } catch (Exception e) {
      logger.error("Redis DEL异常 - Key: {}", key, e);
      throw new RuntimeException("Redis操作失败", e);
    }
  }


  /**
   * 获取缓存
   * @param key 键
   * @return 值
   */
  public V get(String key) {
    try {
      V value = redisTemplate.opsForValue().get(key);
      if (value != null) {
        logger.debug("Redis GET命中 - Key: {}", key);
      } else {
        logger.debug("Redis GET未命中 - Key: {}", key);
      }
      return value;
    } catch (Exception e) {
      logger.error("Redis GET异常 - Key: {}", key, e);
      throw new RuntimeException("Redis操作失败", e);
    }
  }


}
