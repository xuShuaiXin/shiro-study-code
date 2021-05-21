package com.xsx.customize;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class CustomizeRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal = (String) principalCollection.getPrimaryPrincipal();
        System.out.println("principal===" + principal);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //基于角色的权限管理
        //添加角色
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addRole("user");
        simpleAuthorizationInfo.addRole( "test");

        //基于资源的权限管理
        //添加资源权限字符串 : : :
        simpleAuthorizationInfo.addStringPermission("dev:*:*");
        //如果最后一段字符为 * 则可以省略
        simpleAuthorizationInfo.addStringPermission("*:create:*");
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //认证
        String principal = (String) token.getPrincipal();
        if(principal.equals("xsx")){
            String password = "c1b38668efe7a2487056ff0189fe4e16";
            String salt = "@012qq";
            return new SimpleAuthenticationInfo(principal, password,
                    ByteSource.Util.bytes(salt), this.getName());
        }
        return null;
    }
}
