package top.yu.mall.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: yu
 * @Date: 4/13/21:3:26 PM
 */
@Component
@ConfigurationProperties("jwt")
@Data
public class JwtProperties {

    public static final String JWT_HEAD = "jwtHead";
    public static final String JWT_TOKEN= "jwtToken";

    private String httpHeaderKey;
    private String secret;
    private Long expire;
    private String tokenHead;

//    private
}
