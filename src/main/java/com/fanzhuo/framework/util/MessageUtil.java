package com.fanzhuo.framework.util;

import com.fanzhuo.framework.IConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;

/**
 * 资源信息工具类，根据本地化语言读取相应资源文件获取描述信息 <br>
 * 调用方式：<br>
 * Message.getMessage(String key); <br/>
 * Message.getMessage(String key, Object arg); <br/>
 * Message.getMessage(String key, Object[] args); <br/>
 */
public class MessageUtil {

    private static Logger log = LoggerFactory.getLogger(MessageUtil.class);

    // 根本本地化语言缓存
    private static Map<String, ResourceBundle> bundleLocaleCache = new HashMap<String, ResourceBundle>();
//	private static final String KEY_BUNDLE = "KEY_RESOURCE_BUNDLE_";

    /**
     * 使用本地化语言，从资源文件获取关键字对应的描述信息(使用默认资源文件名)
     *
     * @param name
     * @return
     */
    public static String getMessage(String name) {
        return getMessage(name, null);
    }

    public static String getMessage(String name, Object arg) {
        if (arg == null) {
            return getMessage(name, null);
        }
        return getMessage(name, new Object[]{arg});
    }

    /**
     * 使用本地化语言，从资源文件获取关键字对应的描述信息，并将args填充到描述信息中(使用默认资源文件名)
     *
     * @param name
     * @param args
     * @return
     */
    public static String getMessage(String name, Object[] args) {
        return getMessage(IConstants.DEAULT_RESOURCE_FILE, name, args);
    }

    public static String getMessage(String resource, String name, Object[] args) {
        return getMessage(resource, name, args, Locale.getDefault());
    }

    public static String getMessage(String resource, String name, Object[] args, Locale locale) {
        String value = getMessage(resource, name, locale);
        if (args != null && args.length > 0) {
            value = MessageFormat.format(value, args);
        }
        return value;
    }

    /**
     * 根据本地化语言，从资源文件获取关键字对应的描述信息
     *
     * @param resource
     * @param name
     * @param locale
     * @return
     */
    public static String getMessage(String resource, String name, Locale locale) {
        if (StringUtils.isEmpty(resource) || StringUtils.isEmpty(name)) {
            return name;
        }

        ResourceBundle bundle = getResourceBundle(resource, locale);
        if (bundle == null) {
            return name;
        }

        String value = name;
        try {
            value = bundle.getString(name);
        } catch (Exception e) {
            log.error("从本地化资源文件获取描述信息失败，返回参数原值", e);
        }
        return StringUtils.trim(value);
    }

    /**
     * 根据本地化语言读取资源文件，如果缓存中没有则从资源文件读取
     *
     * @param resource
     * @param locale   zh_CN,zh_TW,en,en_US等
     * @return
     */
    public static ResourceBundle getResourceBundle(String resource, Locale locale) {
        String resourceCacheKey = resource;
        String lang = locale.toString();
        if (!StringUtils.isEmpty(lang)) {
            resourceCacheKey += ("_" + lang);
        }

        ResourceBundle bundle = bundleLocaleCache.get(resourceCacheKey);
        if (bundle == null) {
            bundle = getBaseResourceBundle(resource, locale);
            bundleLocaleCache.put(resourceCacheKey, bundle);
        }
        return bundle;
    }

    /**
     * 根据本地化语言读取资源文件
     *
     * @param resource
     * @param locale
     * @return
     */
    protected static ResourceBundle getBaseResourceBundle(String resource, Locale locale) {
        if (StringUtils.isEmpty(resource)) {
            return null;
        }

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        ResourceBundle bundle = null;
        try {
            bundle = ResourceBundle.getBundle(resource, locale, loader);
        } catch (MissingResourceException e) {
            log.error("Could not find resource: {}_{}", resource, locale.toString(), e);
            return null;
        }
//		if (bundle == null) {
//			log.error("Could not find resource: {}_{}", resource, locale.toString());
//			return null;
//		}
        return bundle;
    }

    /**
     * 资源对象转换为HashMap，处理从父资源文件继承的内容(所有资源文件都根据本地化语言配置，不需处理)<br/>
     *
     * @param bundle
     * @return
     */
//	protected static Map resourceBundleToMap(ResourceBundle bundle) {
//		if (bundle == null) {
//			return new HashMap();
//		}
//
//		// NOTE: this should return all keys, including keys from parent ResourceBundles,
//		// if not then something else must be done here...
//		Map<String, Object> resourceBundleMap = new HashMap<String, Object>();
//		Enumeration<String> keyNum = bundle.getKeys();
//		while (keyNum.hasMoreElements()) {
//			String key = (String) keyNum.nextElement();
//			Object value = bundle.getObject(key);
//			resourceBundleMap.put(key, value);
//		}
//		resourceBundleMap.put(KEY_BUNDLE, bundle);
//		return resourceBundleMap;
//	}
    public static void main(String[] args) {
        log.info("app.title={}", MessageUtil.getMessage("app.title"));
        log.info("app.vendor={}", MessageUtil.getMessage("app.vendor"));
        log.debug("vld-0701={}", MessageUtil.getMessage("vld-0701", "name"));
    }

}
