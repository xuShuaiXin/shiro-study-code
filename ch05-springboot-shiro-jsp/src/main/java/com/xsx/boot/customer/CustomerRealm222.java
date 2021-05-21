package com.xsx.boot.customer;

import com.xsx.boot.model.Role;
import com.xsx.boot.model.User;
import com.xsx.boot.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class CustomerRealm222 extends AuthorizingRealm {
    @Resource
    private UserService service;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //授权
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        User user = service.selectByUserName(primaryPrincipal);
        if (user != null){
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            System.out.println("user =======" + user);
            //根据查询到的内容添加角色
            for (Role role : user.getRoles()) {
                System.out.println("Role === " + role);
                authorizationInfo.addRole(role.getName());

                role.getPerms().forEach(perm -> {
                    System.out.println("perm === " + perm.getName());
                    authorizationInfo.addStringPermission(perm.getName());
                });

            }
//            authorizationInfo.addRole("user");
            return authorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //认证
        String principal = (String) authenticationToken.getPrincipal();
        User user = service.selectByUserName(principal);
        System.out.println(user);
        if (user != null){
            //使用自定义的已经实现序列化接口的CustomerMyByteSource序列化盐
            return new SimpleAuthenticationInfo(principal, user.getPassword(),
                    ByteSource.Util.bytes(user.getSalt()),this.getName());
        }
        return null;
    }
}
