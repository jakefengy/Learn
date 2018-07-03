package com.fanzhuo.framework.listener;

import com.fanzhuo.framework.license.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 2018-07-03.
 */
public class InitializeListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(InitializeListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        License lic = new License();
        if (!lic.readFile()) {
            throw new RuntimeException("no license exist error");
        }
        log.info("license:\n" + lic.toString());
        if (!lic.verify()) {
            throw new RuntimeException("verify license error");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
