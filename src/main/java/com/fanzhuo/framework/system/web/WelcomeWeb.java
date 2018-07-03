package com.fanzhuo.framework.system.web;

import com.fanzhuo.framework.base.BaseWeb;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 设置默认首页
 * <p>
 * 2018-07-03.
 */
@Controller
public class WelcomeWeb extends BaseWeb {

    @RequestMapping(value = "/")
    public ModelAndView defaultIndex(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView("/web/login.jsp");
    }

    @RequestMapping(value = "/index")
    public ModelAndView indexCore(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView("/web/login.jsp");
    }

}
