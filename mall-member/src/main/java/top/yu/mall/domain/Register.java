package top.yu.mall.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Author: yu
 * @Date: 4/13/21:12:22 PM
 */
@Data
public class Register {

    @NotBlank(message = "密码不允许为空")
    @Size(min = 8, max = 20, message = "密码的长度应在8-20之间")
    private String password;

    @NotBlank(message = "电话号码不允许为空")
    @Size(min = 11, max = 11, message = "手机号的长度应是11个字符")
    @Pattern(regexp = "^1[134578]\\d{9}$", message = "手机格式不正确")
    private String phone;

    @NotBlank(message = "用户名不允许为空")
    @Size(min = 6, max = 20, message = "用户名的长度应在6-20之间")
    private String username;

    @NotBlank(message = "动态校验码不允许为空")
    @Size(min = 6, max = 6, message = "动态校验码的长度应为6")
    private String optCode;
}
