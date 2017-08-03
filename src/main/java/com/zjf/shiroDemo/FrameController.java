package com.zjf.shiroDemo;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo")
public class FrameController
{
    @RequestMapping("/Frame")
    public String hello(HttpServletRequest request, Model model) {
        Subject subject = SecurityUtils.getSubject();
        Object obj = subject.getPrincipal();

        model.addAttribute("message", "欢迎：" + obj + "!");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("prm1", "map中的第一个");
        map.put("prm2", "map中的第二个");
        map.put("prm3", "map中的第三个");
        model.addAttribute("prm", map);

        return "jsp/Frame";
    }
}
