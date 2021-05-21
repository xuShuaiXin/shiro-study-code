package com.xsx.boot.controller;

import com.sun.deploy.net.HttpResponse;
import com.xsx.boot.service.UserService;
import com.xsx.boot.uiti.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("code")  String code,
                        HttpSession session){
        try {
            String verifyCode = (String) session.getAttribute("verifyCode");
            if (verifyCode.equalsIgnoreCase(code)){     //验证码不区分大小写
                //如果验证码一致才会进行登录
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                subject.login(token);           //调用主体的 subject.login(Token)方法进行认证
                log.info("登录成功");
                return "redirect:/index.jsp";
            }else{
                //验证码不一致会直接抛异常
                log.error("验证码不正确!");
                throw new RuntimeException(" Incorrect verifyCode code!");
            }
        }catch (UnknownAccountException e){
            log.info("错误提示: 找不到此账号!");
            e.printStackTrace();
        }catch (IncorrectCredentialsException e){
            log.info("错误提示: 密码错误!");
            e.printStackTrace();
        } catch (Exception e){
            //如果失败则会直接报错 抛出异常
            e.printStackTrace();
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

    @RequestMapping("/getImage")
    public void codeImage(HttpSession session, HttpServletResponse response) throws IOException {
        //生成验证码
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //
        session.setAttribute("verifyCode", verifyCode);
        //验证码输出为图片
        ServletOutputStream os = response.getOutputStream();
        response.setContentType("image/png");
        //设置验证码的图片的宽高
        VerifyCodeUtils.outputImage(200, 60, os, verifyCode);
    }

}
