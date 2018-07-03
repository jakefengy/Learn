package com.fanzhuo.framework.lang;

/**
 * 框架内异常基类，其他自定义异常类以此扩展
 *
 * @author done
 * @version 1.0
 */
public class BaseException extends Exception {

    private static final long serialVersionUID = -3354200470837540986L;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public static void main(String[] args) {
        BaseException e = new BaseException("出现异常一");
        System.out.println(e.getMessage());
        BaseException e1 = new BaseException("出现异常二", new Exception());
        System.out.println(e1.getMessage());
    }

}
