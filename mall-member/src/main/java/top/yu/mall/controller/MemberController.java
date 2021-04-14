package top.yu.mall.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.yu.mall.api.CommonResult;
import top.yu.mall.config.properties.JwtProperties;
import top.yu.mall.domain.Register;
import top.yu.mall.domain.UmsMember;
import top.yu.mall.exception.BusnessException;
import top.yu.mall.kit.JwtKit;
import top.yu.mall.service.MemberService;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.HashMap;

/**
 * @Author: yu
 * @Date: 4/13/21:12:11 PM
 */
@RestController
@RequestMapping("/sso")
@Validated
public class MemberController extends BaseController {

    @Autowired
    MemberService memberService;

    @Autowired
    JwtKit jwtKit;

    @Autowired
    JwtProperties jwtProperties;

    @RequestMapping("/getOptCode")
    public CommonResult<String> getOptCode(
            @RequestParam @Length(min = 11, max = 11, message = "手机号长度应为11位") String phone) throws BusnessException {
        String optCode = memberService.getOptCode(phone);
        if (StringUtils.hasLength(optCode)) {
            return CommonResult.success(optCode);
        }
        return CommonResult.failed();
    }

    @RequestMapping("/register")
    public CommonResult register(@RequestBody @Validated Register register) throws BusnessException {
        int result = memberService.register(register);
        if (result > 0) {
            return CommonResult.success("注册成功", null);
        }
        return CommonResult.failed();
    }

    @RequestMapping("/login")
    public CommonResult login(
            @Size(min = 6, max = 20, message = "用户名的长度应在6-20之间") String username,
            @Size(min = 8, max = 20, message = "密码的长度应在8-20之间") String password) throws BusnessException {
        UmsMember umsMember = memberService.login(username, password);
        if (umsMember != null) {
            getSession().setAttribute("member", umsMember);
            return CommonResult.success("登录成功", null);
        }
        return CommonResult.failed();
    }

    @RequestMapping("/jwt_login")
    public CommonResult jwtLogin(
            @Size(min = 6, max = 20, message = "用户名的长度应在6-20之间") String username,
            @Size(min = 8, max = 20, message = "密码的长度应在8-20之间") String password) throws BusnessException {
        UmsMember umsMember = memberService.login(username, password);
        if (umsMember != null) {
            String token = jwtKit.createToken(umsMember);

            HashMap<Object, Object> map = new HashMap<>();
            map.put(jwtProperties.JWT_HEAD, jwtProperties.getTokenHead());
            map.put(jwtProperties.JWT_TOKEN, token);

            return CommonResult.success(map);
        }
        return CommonResult.failed();
    }
}
