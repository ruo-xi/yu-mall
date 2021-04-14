package top.yu.mall.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedList;


/**
 * @Author: yu
 * @Date: 4/13/21:3:22 PM
 */
@ConfigurationProperties("auth.skip")
@Component
@Data
public class AuthSkipUrlProperties {
    private LinkedList<String> urls;
}
