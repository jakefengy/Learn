package com.fanzhuo.framework.base.json;

/**
 * JAVA对象转换为JSON字符串的封装对象
 *
 * 2018-07-02.
 */
public class JSONWrapperObject extends JSONWrapper {

    private static final long serialVersionUID = -5624493945768478577L;

    // 业务数据
    private Object data;

    public JSONWrapperObject() {
        super();
    }

    public JSONWrapperObject(Object data, int status) {
        this(data, status, null);
    }

    public JSONWrapperObject(Object data, int status, String message) {
        super(status, message);
        this.data = data;
    }

    /**
     * @param dateFormat 转为JSON时的日期格式
     * @param data
     * @param status
     */
    public JSONWrapperObject(String dateFormat, Object data, int status) {
        this(dateFormat, data, status, null);
    }

    public JSONWrapperObject(String dateFormat, Object data, int status, String message) {
        super(dateFormat, status, message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
