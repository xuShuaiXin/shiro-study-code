package com.xsx.customize;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
//自定义Realm
public class CustomizeRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
            //认证
            String principal = (String) authenticationToken.getPrincipal();
            if ("xsx".equals(principal)) {
                String password = "c1b38668efe7a2487056ff0189fe4e16";
                String salt = "@012qq";
                /**
                 *      该方法的返回值使用 AuthenticationInfo接口
                 *          的实现类SimpleAuthenticationInfo
                 *     public SimpleAuthenticationInfo
                 *          (Object principal, Object credentials, ByteSource.Util.bytes(salt) ,String realmName)
                 *     参数1:
                 *          principal:      认证信息即用户名
                 *          credentials:    数据库中的正确密码
                 *          salt:           随机盐,必须使用ByteSource.Util.bytes(盐)方法传参
                 *          String:         当前Realm的类名 this.getName()
                 */
//            return new SimpleAuthenticationInfo(principal, password, this.getName());
                return new SimpleAuthenticationInfo(principal, password,
                        ByteSource.Util.bytes(salt), this.getName());
            }
        return null;
    }
}
