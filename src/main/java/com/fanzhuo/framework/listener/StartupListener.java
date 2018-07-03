package com.fanzhuo.framework.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 2018-07-03.
 */
public class StartupListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(StartupListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("************* application startup *************");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("************* application shutdown *************");
    }
}
