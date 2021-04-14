package top.yu.mall.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: yu
 * @Date: 4/13/21:1:18 PM
 */
@ConfigurationProperties("redis.key")
@Component
@Data
public class RedisKeyProperties {
    private String prefix;
    private Long expire;
}
