package top.yu.mall.exception;

/**
 * @Author: yu
 * @Date: 4/13/21:12:50 PM
 */
public class BusnessException extends Exception{
    public BusnessException() {
    }

    public BusnessException(String message) {
        super(message);
    }

    public BusnessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusnessException(Throwable cause) {
        super(cause);
    }

    public BusnessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
