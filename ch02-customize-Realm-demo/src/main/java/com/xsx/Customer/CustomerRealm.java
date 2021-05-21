package com.xsx.Customer;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

//自定义 Realm 必须继承AuthorizingRealm抽象类并重写方法 1.继承AuthorizingRealm抽象类并重写方法
public class CustomerRealm extends AuthorizingRealm {

    //返回授权信息方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
    //返回认证信息的方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //2.获取身份认证信息 即username
        String principal = (String) token.getPrincipal();
        //3.模拟数据库做判断操作
        if("xs1".equals(principal)){
            /**
             *      该方法的返回值使用 AuthenticationInfo接口
             *          的实现类SimpleAuthenticationInfo
             *     public SimpleAuthenticationInfo
             *          (Object principal, Object credentials, String realmName)
             *     参数1:
             *          principal:      认证信息即用户名
             *          credentials:    数据库中的正确密码
             *          String:         当前Realm的类名 this.getName()
             */
            //4.返回  SimpleAuthenticationInfo对象
            return new SimpleAuthenticationInfo(principal, "122", this.getName());
        }
        return null;
    }
}
