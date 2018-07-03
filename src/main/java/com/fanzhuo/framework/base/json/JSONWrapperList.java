package com.fanzhuo.framework.base.json;

import java.util.List;

/**
 * JAVA对象转换为JSON字符串的封装对象
 *
 * 2018-07-02.
 */
public class JSONWrapperList<E> extends JSONWrapper {

    private static final long serialVersionUID = -9088638359410019286L;

    private List<E> list;
    private int total = 0;// 如果是分页查询可传入总记录数

    public JSONWrapperList() {
        super();
    }

    public JSONWrapperList(List<E> list, int status) {
        this(list, status, null);
    }

    public JSONWrapperList(List<E> list, int status, String message) {
        this(list, (list != null ? list.size() : 0), status, message);
    }

    public JSONWrapperList(List<E> list, int total, int status) {
        this(list, total, status, null);
    }

    public JSONWrapperList(List<E> list, int total, int status, String message) {
        super(status, message);
        this.list = list;
        this.total = total;
    }

    /**
     * @param dateFormat 转为JSON时的日期格式
     * @param list
     * @param status
     */
    public JSONWrapperList(String dateFormat, List<E> list, int status) {
        this(dateFormat, list, status, null);
    }

    public JSONWrapperList(String dateFormat, List<E> list, int status, String message) {
        this(dateFormat, list, (list != null ? list.size() : 0), status, message);
    }

    public JSONWrapperList(String dateFormat, List<E> list, int total, int status) {
        this(dateFormat, list, total, status, null);
    }

    public JSONWrapperList(String dateFormat, List<E> list, int total, int status, String message) {
        super(dateFormat, status, message);
        this.list = list;
        this.total = total;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
