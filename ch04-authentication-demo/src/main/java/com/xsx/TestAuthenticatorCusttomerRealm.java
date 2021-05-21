package com.xsx;

import com.sun.org.apache.bcel.internal.generic.LUSHR;
import com.xsx.customize.CustomizeRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;

//授权案例
public class TestAuthenticatorCusttomerRealm {
    public static void main(String[] args) {
        //1.创建安全管理器对象 DefaultSecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2.设置自定义的 MD5+盐后的 Realm对象获取认证数据
        CustomizeRealm customizeRealm = new CustomizeRealm();
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashIterations(1024);
        credentialsMatcher.setHashAlgorithmName("md5");
        customizeRealm.setCredentialsMatcher(credentialsMatcher);
        securityManager.setRealm(customizeRealm);
        //3.将安装工具设置为默认安全管理器 SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);
        //4.获取主体对象 通过 SecurityUtils对象
        Subject subject = SecurityUtils.getSubject();
        //5.创建token令牌 直接new
        UsernamePasswordToken token = new UsernamePasswordToken("xsx","123");
        //6.通过主体对象认证
        //6.认证(登录)
        try {
            //如果认证成功会打印 slf4j的日志信息
            System.out.println("是否登录: " + subject.isAuthenticated());
            subject.login(token);           //调用主题的 subject.login(Token)方法进行认证
            System.out.println("是否登录: " + subject.isAuthenticated());   //获取认证状态
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
        //7.如果认证成功才会进行授权
        if (subject.isAuthenticated()){
            //基于角色的权限管理测试
            //是否含有该角色
//            System.out.println(subject.hasRole("admin"));
//            System.out.println(subject.hasRole("test"));
//            ArrayList<String> list = new ArrayList<>();
//            list.add("dev");
//            list.add("test");
//            //查询是否含有多个角色
//            boolean[] booleans = subject.hasRoles(list);
//            for (boolean aBoolean : booleans) {
//                System.out.print(aBoolean + " ");
//            }

            //基于资源的权限管理测试
            System.out.println(subject.isPermitted("dev:*:*"));
            System.out.println(subject.isPermitted("prod:*:0123"));
            System.out.println(subject.isPermitted("tes222t:create:02"));
            System.out.println(subject.isPermitted("*:update:02"));
        }

    }
}
