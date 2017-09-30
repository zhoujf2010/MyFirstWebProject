package com.zjf.rest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zjf.db4.DBOper;

@RestController
@RequestMapping("/test")
public class restAction
{
    protected Logger log = Logger.getLogger(getClass());// MethodHandles.lookup().lookupClass()
    
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@RequestParam("x") int x, @RequestParam("y") int y) {
        return (x+y)+"";
    }
    
    @ResponseBody
    @RequestMapping(value = "/testlog", method = RequestMethod.GET)
    public String testlog() {
        log.info("aaa");
        log.info("中文信息输出来");
        log.info("aaa中文");
        return "abc中文";
    }
    
    @ResponseBody
    @RequestMapping(value = "/add2/{x}/{y}", method = RequestMethod.GET)
    public String add2(@PathVariable("x") int x, @PathVariable("y") int y) {
        
        System.out.println(DBOper.getInstance().getConnection().hashCode());
        System.out.println(DBOper.getInstance().getConnection().hashCode());
        
        return (x+y)+"";
    }
    
    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String getUser(@RequestBody String user) {
        return "Hello " + user;
    }
}
