package com.zjf.mvcdemo;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    
	@RequestMapping("/showUser")
	public String toIndex(HttpServletRequest request,Model model){
		model.addAttribute("user", "Hello");
		 Map<String, Object> map = new HashMap<String, Object>();
       map.put("prm1", "map中的第一个");
       map.put("prm2", "map中的第二个");
       map.put("prm3", "map中的第三个");
       model.addAttribute( "prm", map);
		 
		return "showUser";
	}
    
    @RequestMapping("/hello")
    public String hello(HttpServletRequest request,Model model){
//      int userId = Integer.parseInt(request.getParameter("id"));
//      User user = this.userService.getUserById(userId);
        model.addAttribute("message", "Hello World!");  
         Map<String, Object> map = new HashMap<String, Object>();
       map.put("prm1", "map中的第一个");
       map.put("prm2", "map中的第二个");
       map.put("prm3", "map中的第三个");
       model.addAttribute( "prm", map);
         
        return "hello";
    }
}