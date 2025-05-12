package com.picturebed.aspect;


import com.picturebed.common.response.RateLimit;
import com.picturebed.common.response.enums.ResponseCodeEnum;
import com.picturebed.exception.BusinessException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author moZiA
 * @date 2025/5/12 18:40
 * @description
 */
@Aspect
@Component
public class RateLimitAspect {

  // 存储限流计数器：Key = 限流唯一标识，Value = 计数器
  private final ConcurrentHashMap<String, AtomicInteger> counterMap = new ConcurrentHashMap<>();
  // 存储时间窗口的起始时间
  private final ConcurrentHashMap<String, Long> timestampMap = new ConcurrentHashMap<>();

  @Around("@annotation(rateLimit)")
  public Object around(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
    String key = rateLimit.key();
    int limit = rateLimit.limit();
    int timeout = rateLimit.timeout();

    // 如果 key 为空，默认使用方法名作为限流标识
    if (key.isEmpty()) {
      key = joinPoint.getSignature().toLongString();
    }

    synchronized (this) {
      long now = System.currentTimeMillis();
      long windowStart = timestampMap.getOrDefault(key, 0L);

      // 如果当前时间超出时间窗口，重置计数器和时间窗口
      if (now - windowStart > timeout * 1000L) {
        counterMap.put(key, new AtomicInteger(0));
        timestampMap.put(key, now);
      }

      // 获取当前计数器值
      AtomicInteger counter = counterMap.computeIfAbsent(key, k -> new AtomicInteger(0));
      if (counter.get() >= limit) {
        System.out.println("-------------------------------------------");
        throw new BusinessException(ResponseCodeEnum.CODE_429, "操作频繁");
      }

      // 计数器加1
      counter.incrementAndGet();
    }

    // 执行目标方法
    return joinPoint.proceed();
  }
}