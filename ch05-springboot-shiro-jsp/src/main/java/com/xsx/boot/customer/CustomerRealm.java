package com.xsx.boot.customer;

import com.xsx.boot.mapper.UserMapper;
import com.xsx.boot.model.Perms;
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
import java.util.List;

public class CustomerRealm extends AuthorizingRealm {
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService service;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //授权
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        User user = service.selectByUserName(primaryPrincipal);
        if (user != null){
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            System.out.println(user);
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
        User user = userMapper.selectByUserName(principal);

        if (user != null){
            System.out.println(" getUsername ==== " + user.getUsername());
            System.out.println(" getPassword ==== " + user.getPassword());
            System.out.println(" getSalt === " + user.getSalt());
            //使用自定义的已经实现序列化接口的CustomerMyByteSource序列化盐
            return new SimpleAuthenticationInfo(principal, user.getPassword(),
                    new CustomerMyByteSource(user.getSalt()),this.getName());
        }
        return null;
    }
}
