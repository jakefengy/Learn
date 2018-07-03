package com.fanzhuo.framework.base.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class JSONWrapper implements Serializable {

    private static final long serialVersionUID = -61156617775476828L;

    // STATUS CODE
    // success
    public static int SC_OK = 200;
    // failure
    public static int SC_FAIL = 0;
    // failure, forbidden access
    public static int SC_FORBIDDEN = 403;
    // failure, application error
    public static int SC_INTERNAL_SERVER_ERROR = 500;

    public static String MSG_OK = "操作成功！";
    public static String MSG_FAIL = "操作失败，未知错误！";
    public static String MSG_FAIL_REQUEST = "操作失败，请求服务出现错误！";

    private String dateFormat;

    private int status;
    private String message;

    public JSONWrapper() {
        this(SC_FAIL, null);
    }

    public JSONWrapper(int status) {
        this(status, null);
    }

    public JSONWrapper(int status, String message) {
        this.status = status;
        this.message = (StringUtils.isNotBlank(message) ? message : (SC_OK == status ? MSG_OK : MSG_FAIL));
    }

    /**
     * @param dateFormat 转为JSON时的日期格式
     */
    public JSONWrapper(String dateFormat) {
        this(dateFormat, SC_FAIL, null);
    }

    public JSONWrapper(String dateFormat, int status) {
        this(dateFormat, status, null);
    }

    public JSONWrapper(String dateFormat, int status, String message) {
        this(status, message);
        this.dateFormat = dateFormat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // to json string by fastjson
    public String toString() {
        return JSON.toJSONString(this,
                new FastJsonValueFilter(this.dateFormat));
    }

    public String toStringPretty() {
        return JSON.toJSONString(this,
                new FastJsonValueFilter(this.dateFormat),
                SerializerFeature.PrettyFormat);
    }

}
