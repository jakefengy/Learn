package com.fanzhuo.framework.lang.errorcode;


/**
 * 自定义异常类，增加异常代码，异常描述通过异常代码从资源文件获取
 *
 * @author done
 * @version 1.0
 * @see com.fanzhuo.framework.ErrorCode
 * @see com.fanzhuo.framework.IConstants.DEAULT_RESOURCE_FILE
 */
public class MyDaoCodeException extends BaseCodeException {

    private static final long serialVersionUID = 5656111978586755436L;

    public MyDaoCodeException() {
        super();
    }

    /**
     * 使用code从资源文件获取对应异常描述信息
     *
     * @param code
     */
    public MyDaoCodeException(String code) {
        super(code);
    }

    /**
     * 使用code从资源文件获取对应异常描述信息
     *
     * @param code
     * @param arg  作为异常描述信息的参数
     */
    public MyDaoCodeException(String code, Object arg) {
        super(code, arg);
    }

    /**
     * 使用code从资源文件获取对应异常描述信息
     *
     * @param code
     * @param args 作为异常描述信息的参数
     */
    public MyDaoCodeException(String code, Object[] args) {
        super(code, args);
    }

    /**
     * 使用code从资源文件获取对应异常描述信息
     *
     * @param code
     * @param cause
     */
    public MyDaoCodeException(String code, Throwable cause) {
        super(code, cause);
    }

    /**
     * 使用code从资源文件获取对应异常描述信息
     *
     * @param code
     * @param arg   作为异常描述信息的参数
     * @param cause
     */
    public MyDaoCodeException(String code, Object arg, Throwable cause) {
        super(code, arg, cause);
    }

    /**
     * 使用code从资源文件获取对应异常描述信息
     *
     * @param code
     * @param args  作为异常描述信息的参数
     * @param cause
     */
    public MyDaoCodeException(String code, Object[] args, Throwable cause) {
        super(code, args, cause);
    }

    public MyDaoCodeException(Throwable cause) {
        super(cause);
    }

}
