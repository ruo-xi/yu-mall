package top.yu.mall.config.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import top.yu.mall.api.CommonResult;
import top.yu.mall.config.properties.JwtProperties;
import top.yu.mall.exception.BusnessException;
import top.yu.mall.kit.JwtKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: yu
 * @Date: 4/13/21:3:16 PM
 */
@Slf4j
public class AuthInterceptorHandler implements HandlerInterceptor {
    @Autowired
    JwtKit jwtKit;

    @Autowired
    JwtProperties jwtProperties;
    private static final String GLOBAL_JWT_USER_INFO = "jwttoken:usermember:info";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader(jwtProperties.getHttpHeaderKey());

        if (StringUtils.hasText(authorization) && authorization.startsWith(jwtProperties.getTokenHead())) {
            String authToken = authorization.substring(jwtProperties.getTokenHead().length() + 1);
            try {
                Claims claims = jwtKit.parseJwtToken(authToken);
                request.setAttribute(GLOBAL_JWT_USER_INFO,claims);
                return true;
            } catch (BusnessException e) {
                log.error(e.getMessage() + authToken);
            }
        }

        if (!ObjectUtils.isEmpty(request.getSession().getAttribute("member"))) {
            return true;
        }

        print(response,"你无权访问");
        return false;
    }

    private void print(HttpServletResponse response, String msg) throws IOException {
        response.setHeader("Content-Type","application/json");
        response.setCharacterEncoding("UTF-8");
        String result = new ObjectMapper().writeValueAsString(CommonResult.forbidden(msg));
        response.getWriter().write(result);
    }
}
