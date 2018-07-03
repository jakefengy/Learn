package com.fanzhuo.framework.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

    /**
     * JSON字符转义 <br/>
     * 斜杠转义： \ = \\ <br/>
     * 双引号转义： " = \" <br/>
     *
     * @param str
     * @return
     */
    public static String escapeJSONCharacter(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        return str.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"");
    }

}
