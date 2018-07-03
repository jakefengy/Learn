package com.fanzhuo.framework.base.json;

import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.fanzhuo.framework.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * Java对象转换为JSON时，部分类型字段的null处理为空值，日期按指定格式进行格式化
 * <p>
 * 2018-07-02.
 */
public class FastJsonValueFilter implements ContextValueFilter {

    private static final Logger log = LoggerFactory.getLogger(FastJsonValueFilter.class);

    public static String EMPTY = "";
    public static Number ZERO = 0;
    public static String NULL_LIST = "[]";
    public static String NULL_MAP = "{}";

    private String dateFormat = DateUtil.PATTERN_DATE_TIME;

    public FastJsonValueFilter() {

    }

    public FastJsonValueFilter(String dateFormat) {
        if (StringUtils.isNotBlank(dateFormat)) {
            this.dateFormat = dateFormat;
        }
    }

    @Override
    public Object process(BeanContext context, Object object, String name, Object value) {
//		log.debug("context={}, object={}, name={}, value={}", context, object, name, value);

        // list empty -> [], null -> []
        // map empty -> {}, null -> {}
        // number null -> 0
        // date format by pattern, null -> ""
        // string or others null -> ""
        Class<?> clazz = (context != null ? context.getFieldClass() : (value != null ? value.getClass() : null));
//		log.debug("class={}", clazz);
        if (clazz != null) {
            // null list
            if (List.class.isAssignableFrom(clazz)) {
                if (value == null) {
//					return EMPTY;
//					return NULL_LIST;
                    return new ArrayList<Object>();
                }
            }
            // null map
            if (Map.class.isAssignableFrom(clazz)) {
                if (value == null) {
//					return EMPTY;
//					return NULL_MAP;
                    return new HashMap<Object, Object>();
                }
            }
            // null number as zero，泛型数字(Integer/Short/Long/Float/Double/BigDecimal)
            if (Number.class.isAssignableFrom(clazz) && !Byte.class.isAssignableFrom(clazz)) {
                if (value == null) {
                    return ZERO;
                }
            }
            // date format
            if (value != null && Date.class.isAssignableFrom(clazz)) {
                return DateFormatUtils.format((Date) value, this.getDateFormat());
            }
        }

        // null to empty others
        if (value == null) {
            return EMPTY;
        }
        return value;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

}
