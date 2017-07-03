package com.zjf.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class restAction
{
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@RequestParam("x") int x, @RequestParam("y") int y) {
        return (x+y)+"";
    }
    
    @ResponseBody
    @RequestMapping(value = "/add2/{x}/{y}", method = RequestMethod.GET)
    public String add2(@PathVariable("x") int x, @PathVariable("y") int y) {
        return (x+y)+"";
    }
    
    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String getUser(@RequestBody String user) {
        return "Hello " + user;
    }
}
