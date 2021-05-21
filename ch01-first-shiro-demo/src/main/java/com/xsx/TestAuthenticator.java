package com.xsx;


import org.apache.shiro.SecurityUtils;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

//测试shiro的认证 Authenticator
public class TestAuthenticator {
    public static void main(String[] args) {
        //1.创建安全管理器  SecurityManager对象 他是一个接口
        // 一般使用其实现类 DefaultSecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2.设置认证域 Realm 读取ini用户认证配置文件
        securityManager.setRealm(new IniRealm("classpath:shiro.ini"));
        //3.将安全工具类 中设置为默认安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //4.通过安全工具类 获取主体对象 SubJect
        Subject subject = SecurityUtils.getSubject();
        //5.获取令牌 Token  UsernamePasswordToken 参数(username, password)
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("xsx", "080119");
        //6.认证(登录)
        try {
            //如果认证成功会打印 slf4j的日志信息
            System.out.println("是否登录: " + subject.isAuthenticated());
            subject.login(usernamePasswordToken);           //调用主题的 subject.login(Token)方法进行认证
            System.out.println("是否登录: " + subject.isAuthenticated());   //查看是否认证
        }catch (UnknownAccountException e){
            System.out.println("错误提示: 找不到此账号!");
            e.printStackTrace();
        }catch (IncorrectCredentialsException e){
            System.out.println("错误提示: 密码错误!");
            e.printStackTrace();
        } catch (Exception e){
            //如果失败则会直接报错 抛出异常
            e.printStackTrace();
        }
    }
}
