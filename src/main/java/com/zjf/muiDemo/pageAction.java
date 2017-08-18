package com.zjf.muiDemo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/muidemo")
public class pageAction
{
    protected Logger log = Logger.getLogger(getClass());// MethodHandles.lookup().lookupClass()

    @RequestMapping("/**")
    public ModelAndView Do(HttpServletRequest request,  
            HttpServletResponse response) {
        String page = request.getRequestURI();
        String contextpath = request.getContextPath();
        page = page.substring(contextpath.length() + 1);
        //返回ModelAndView  
        ModelAndView modelAndView =  new ModelAndView();  
        modelAndView.setViewName(page);
        return modelAndView;// "weixin/login/login";
    }

    @RequestMapping(value="/back.do",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String back(String pam1, String pam2, HttpServletRequest request, HttpServletResponse response) {

        String p = request.getParameter("pam");

        // try {
        // PrintWriter out = response.getWriter();
        // out.println("{\"result\":\"Hello " + p + "\"}");
        // out.close(); // 关闭输出流
        // }
        // catch (IOException e) {
        // e.printStackTrace();
        // }
        return "{\"result\":\"Hello " + p + "\"}";
    }
}
