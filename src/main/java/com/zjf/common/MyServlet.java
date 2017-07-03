package com.zjf.common;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.DispatcherServlet;

public class MyServlet extends DispatcherServlet
{
    private static final long serialVersionUID = -6631477864752617453L;

    public void service(final ServletRequest req, final ServletResponse resp) throws IOException, ServletException {
        // 获取url和cmd
        HttpServletRequest hreq = (HttpServletRequest) req;
        String cmd = hreq.getParameter("cmd");
        String url = hreq.getRequestURI();
        if (url.contains("/rest/")){
            super.service(req, resp);
            return;
        }
        
        url = url.substring(url.lastIndexOf("/") + 1);
        String actionName = url.substring(0, url.length()-".action".length());

        // 反射类
        String ret = "";
        try {
            
            Object obj = SpringContextUtil.getContext().getBean(actionName);
            System.out.println(obj.hashCode());

            Object obj2 = SpringContextUtil.getContext().getBean(actionName);
            System.out.println(obj2.hashCode());
            
            Object val = obj.getClass().getMethod(cmd).invoke(obj);
            if (val != null)
                ret = val.toString();
        }
        catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        
        PrintWriter out = resp.getWriter();
        out.println(ret); // 在网页上输出
        out.close(); // 关闭输出流
    }
}
