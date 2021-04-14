package top.yu.mall.api;

import lombok.Getter;

/**
 * @Author: yu
 * @Date: 4/13/21:9:06 AM
 */
@Getter
public enum ResultCode implements IResultCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    FORBIDEN(403, "没有相关权限");

    private long code;
    private String msg;

    ResultCode(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
