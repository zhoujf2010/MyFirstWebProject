package com.zjf.common;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class MyServlet extends HttpServlet
{
    private static final long serialVersionUID = -6631477864752617453L;

    public void service(final ServletRequest req, final ServletResponse resp) throws IOException {
        // 获取url和cmd
        HttpServletRequest hreq = (HttpServletRequest) req;
        String cmd = hreq.getParameter("cmd");
        String url = hreq.getRequestURI();
        url = url.substring(url.lastIndexOf("/") + 1);
        String classname = url.substring(0, url.length()-".action".length());

        // 反射类
        String ret = "";
        try {
            Class cls = Class.forName(classname);
            Object obj = cls.newInstance();

//            Object obj = BeanFactiory.getinstance(classname);
//
//            Object obj2 = BeanFactiory.getinstance(classname);

            Object val = cls.getMethod(cmd).invoke(obj);
            if (val != null)
                ret = val.toString();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        
        PrintWriter out = resp.getWriter();
        out.println(ret); // 在网页上输出
        out.close(); // 关闭输出流
    }
}
