package com.fanzhuo.framework.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 2018-07-03.
 */
public class ActiveUsersListener implements HttpSessionListener {
    private static final Logger log = LoggerFactory.getLogger(StartupListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        String sessionId = arg0.getSession().getId();
        log.debug("用户正在访问系统，sessionId=" + sessionId);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        String sessionId = arg0.getSession().getId();
        log.debug("用户退出系统，sessionId=" + sessionId);

    }
}
