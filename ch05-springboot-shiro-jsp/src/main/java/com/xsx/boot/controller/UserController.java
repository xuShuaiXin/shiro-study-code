package com.xsx.boot.controller;

import com.xsx.boot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password){

        if (userService.loginUser(username, password)){
            return "redirect:/index.jsp";
        }
        return "redirect:/login.jsp";
    }

    @RequestMapping("/register")
    public String save(@RequestParam("username") String username,
                       @RequestParam("password") String password){

        userService.addUser(username, password);
        return "redirect:/login.jsp";
    }

    @RequestMapping("/logout")
    public String logout(){
        userService.logout();
        return "redirect:/login.jsp";
    }

}
