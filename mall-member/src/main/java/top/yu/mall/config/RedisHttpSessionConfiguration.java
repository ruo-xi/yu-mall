package top.yu.mall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Author: yu
 * @Date: 4/13/21:3:05 PM
 */
@EnableRedisHttpSession
@Configuration
public class RedisHttpSessionConfiguration {
}
