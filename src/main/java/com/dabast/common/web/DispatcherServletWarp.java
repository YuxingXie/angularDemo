package com.dabast.common.web;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: lxd
 * Date: 11-11-16
 * Time: 下午5:28
 */
public class DispatcherServletWarp extends DispatcherServlet {

    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(DispatcherServletWarp.class);

    protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            super.render(mv, request, response);
        } catch (ServletException e) {
            logger.info(e.getMessage());
        }
    }

}
