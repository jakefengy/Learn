package com.fanzhuo.framework.lang.errorcode;


import com.fanzhuo.framework.ErrorCode;
import com.fanzhuo.framework.lang.BaseException;
import com.fanzhuo.framework.util.MessageUtil;

/**
 * 自定义异常类，增加异常代码，异常描述通过异常代码从资源文件获取
 *
 * @author done
 * @see com.fanzhuo.framework.ErrorCode
 * @see com.fanzhuo.framework.IConstants.DEAULT_RESOURCE_FILE
 */
public class BaseCodeException extends BaseException {

    private static final long serialVersionUID = 5869053107725202229L;

    public String code;

    public BaseCodeException() {
        super();
    }

    /**
     * 使用code从资源文件获取对应异常描述信息
     *
     * @param code
     */
    public BaseCodeException(String code) {
        super(new StringBuilder("[").append(code).append("]")
                .append(MessageUtil.getMessage(code)).toString());
        this.code = code;
    }

    /**
     * 使用code从资源文件获取对应异常描述信息
     *
     * @param code
     * @param arg  作为异常描述信息的参数
     */
    public BaseCodeException(String code, Object arg) {
        super(new StringBuilder("[").append(code).append("]")
                .append(MessageUtil.getMessage(code, arg)).toString());
        this.code = code;
    }

    /**
     * 使用code从资源文件获取对应异常描述信息
     *
     * @param code
     * @param args 作为异常描述信息的参数
     */
    public BaseCodeException(String code, Object[] args) {
        super(new StringBuilder("[").append(code).append("]")
                .append(MessageUtil.getMessage(code, args)).toString());
        this.code = code;
    }

    /**
     * 使用code从资源文件获取对应异常描述信息
     *
     * @param code
     * @param cause
     */
    public BaseCodeException(String code, Throwable cause) {
        super(new StringBuilder("[").append(code).append("]")
                .append(MessageUtil.getMessage(code)).toString(), cause);
        this.code = code;
    }

    /**
     * 使用code从资源文件获取对应异常描述信息
     *
     * @param code
     * @param arg   作为异常描述信息的参数
     * @param cause
     */
    public BaseCodeException(String code, Object arg, Throwable cause) {
        super(new StringBuilder("[").append(code).append("]")
                .append(MessageUtil.getMessage(code, arg)).toString(), cause);
        this.code = code;
    }

    /**
     * 使用code从资源文件获取对应异常描述信息
     *
     * @param code
     * @param args  作为异常描述信息的参数
     * @param cause
     */
    public BaseCodeException(String code, Object[] args, Throwable cause) {
        super(new StringBuilder("[").append(code).append("]")
                .append(MessageUtil.getMessage(code, args)).toString(), cause);
        this.code = code;
    }

    public BaseCodeException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public static void main(String[] args) {
        BaseCodeException e = new BaseCodeException(ErrorCode.err_not_allow_blank, "用户名");
        System.out.println(e.getMessage());
        BaseCodeException e1 = new BaseCodeException(ErrorCode.err_not_allow_blank, "用户名", new Exception());
        System.out.println(e1.getMessage());
    }

}
