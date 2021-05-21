package com.xsx;

import com.xsx.customize.CustomizeRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

//加入MD5和随机盐和散列次数的认证
public class TestRandomSalt {
    public static void main(String[] args) {
        //1.获取安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2.在自定义Realm中设置md5加密方式 和盐的散列次数

        CustomizeRealm customizeRealm = new CustomizeRealm();
        //2.1 获取 HashedCredentialsMatcher
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 2.2
        credentialsMatcher.setHashIterations(1024);       //设置散列次数
        //2.3 设置匹配器加密模式为 MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //2.4 把匹配器放入到 Realm中
        customizeRealm.setCredentialsMatcher(credentialsMatcher);

        //3.把realm添加到安全管理器中
        securityManager.setRealm(customizeRealm);
        //4.将安装工具类中设置默认安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //5.获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //6.创建token令牌
        UsernamePasswordToken token = new UsernamePasswordToken("xsx", "123");
        //7.认证
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

    }
}
