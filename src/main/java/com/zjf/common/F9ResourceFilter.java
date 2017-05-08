package com.zjf.common;

import java.io.File;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class F9ResourceFilter implements javax.servlet.Filter
{

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)arg0;
        String Uri = request.getRequestURI();
     // 获取最后一段资源名
        String resName = "";
        if (Uri.contains("/"))
            resName = Uri.substring(Uri.lastIndexOf("/"));
        
        if (!resName.contains(".")){
            String path = Uri.substring(request.getContextPath().length() + 1);
            String phiscpath = getWebRootPath() + "pages/" + path + ".html";
            
            if (new File(phiscpath).exists()) {
                String url = "/pages/" + path + ".html";
                request.getRequestDispatcher(url).forward(arg0, arg1);
                return;
            }
        }     
        
        arg2.doFilter(arg0, arg1);
    }



    private String getWebRootPath() {
        return "E:\\WorkSpace\\MyFirstWebProject\\src\\main\\webapp\\";// TODO自行改，或改成动态
    }
    
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
        
    }

}
