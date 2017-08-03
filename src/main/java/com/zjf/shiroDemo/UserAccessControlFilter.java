package com.zjf.shiroDemo;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

public class UserAccessControlFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter   
{/**
     * 控制这个过滤器的有效性
     */
    @Override
    protected boolean isEnabled(ServletRequest request, ServletResponse response, String path, Object mappedValue)
            throws Exception {
       

        return super.isEnabled(request, response, path, mappedValue);
    }
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println(((HttpServletRequest)request).getRequestURI());
        return super.onAccessDenied(request, response);
    }
    
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
//            throws Exception {
//        System.out.println(((HttpServletRequest)request).getRequestURI());
//        if (isLoginRequest(request,response))
//            return true;
//        Subject subject = getSubject(request, response);
//        if (subject.getPrincipal() == null) {// 表示没有登录，重定向到登录页面
////            saveRequest(request);
////            WebUtils.issueRedirect(request, response, "/index.jsp");
//            saveRequestAndRedirectToLogin(request, response);  
//        }
//        return true;
//    }

}
