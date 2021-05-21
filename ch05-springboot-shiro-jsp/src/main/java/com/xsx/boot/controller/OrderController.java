package com.xsx.boot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {
    @RequestMapping("save")
    @ResponseBody
    public String save(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole("admin")){
            return "保存成功";
        }
        return "保存失败";
    }

    @RequestMapping("delete")
    @ResponseBody
    public String delete(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isPermitted("order:delete:*")){
            return "删除成功";
        }
        return "删除失败";
    }

    @RequiresRoles("user")
    @RequestMapping("/select")      //有该角色权限才可访问
    @ResponseBody
    public String select(){
//        Subject subject = SecurityUtils.getSubject();
//        if (subject.isPermitted("order:delete:*")){
//            return "删除成功";
//        }
        return "show users..";
    }

//    @RequiresRoles({"user", "admin"})   //表示同时具有多个角色才能访问
    @RequestMapping("/add")         //先有该角色权限才可访问
    @RequiresPermissions("order:add:*")
    @ResponseBody
    public String add(){
//        Subject subject = SecurityUtils.getSubject();
//        if (subject.isPermitted("order:delete:*")){
//            return "删除成功";
//        }
        return "add success..";
    }
}
