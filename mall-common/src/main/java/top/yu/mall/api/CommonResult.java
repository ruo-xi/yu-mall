package top.yu.mall.api;

import lombok.Data;

/**
 * @Author: yu
 * @Date: 4/13/21:9:06 AM
 */
@Data
public class CommonResult<T> {
    private Long code;
    private String msg;
    private T data;

    public CommonResult(Long code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public CommonResult(IResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public CommonResult(IResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultCode.SUCCESS);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResultCode.SUCCESS, data);
    }

    public static <T> CommonResult<T> success(String msg, T data) {
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> CommonResult<T> failed() {
        return new CommonResult<>(ResultCode.FAILED);
    }

    public static <T> CommonResult<T> failed(IResultCode resultCode) {
        return new CommonResult<>(resultCode);
    }

    public static <T> CommonResult<T> failed(String msg) {
        return new CommonResult<>(ResultCode.FAILED.getCode(), msg, null);
    }

    public static <T> CommonResult<T> failed(T data) {
        return new CommonResult<>(ResultCode.FAILED, data);
    }

    public static <T> CommonResult<T> failed(IResultCode resultCode, T data) {
        return new CommonResult<>(resultCode, data);
    }

    public static Object forbidden(String msg) {
        return new CommonResult<>(ResultCode.FORBIDEN, msg);
    }
}
