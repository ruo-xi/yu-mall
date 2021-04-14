package top.yu.mall.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.yu.mall.api.CommonResult;
import top.yu.mall.exception.BusnessException;

/**
 * @Author: yu
 * @Date: 4/13/21:2:23 PM
 */
//@RestControllerAdvice
public class AdviceController {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult exceptionHandler(Exception e) {
        if (e instanceof BusnessException) {
            return CommonResult.failed(e.getMessage());
        }

        return CommonResult.failed("未知错误: " + e.getMessage() + "\n" + e.getCause());
    }
}
