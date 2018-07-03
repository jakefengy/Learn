package com.fanzhuo.framework.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 提供从Spring's ApplicationContext中获取对象实例的方法 <br/>
 * 配置在spring中，在初始化时将会注入ApplicationContext
 * <p>
 * 2018-07-02.
 */
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return SpringContext.context;
    }

    public static Object getBean(String beanId) {
        return SpringContext.context.getBean(beanId);
    }
}
