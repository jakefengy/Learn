package com.fanzhuo.framework.system.web;

import com.fanzhuo.framework.base.BaseWeb;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 2018-07-02.
 */
@Controller
@RequestMapping("/web")
public class LoginWeb extends BaseWeb {

    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView("/web/login.jsp");
    }

}
