package top.yu.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: yu
 * @Date: 4/13/21:2:18 PM
 */
@Configuration
@MapperScan({"top.yu.mall.mapper"})
public class MybatisConfiguration {
}
