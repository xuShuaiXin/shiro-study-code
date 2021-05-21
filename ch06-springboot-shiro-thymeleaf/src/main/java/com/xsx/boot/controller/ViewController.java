package com.xsx.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {
    //重定向到指定的html页面
    @RequestMapping("/login")
    public String toLogin(){
        return "login";
    }


    @RequestMapping("/index")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("/register")
    public String toRegister(){
        return "register";
    }
}
