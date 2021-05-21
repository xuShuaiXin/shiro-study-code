package com.xsx.boot.config;

import com.xsx.boot.customer.CustomerRealm;
import com.xsx.boot.customer.CustomerRedisCacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        //1.创建安全过滤器
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //4.把web安全管理器加入到过滤器中
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //配置系统受限资源
        HashMap<String, String> map = new HashMap<>();
//        map.put("/user/login", "anon");   // anon表示可匿名访问
//        map.put("/login.jsp", "anon");
//        map.put("/register.jsp", "anon");
//        map.put("/user/register", "anon");
        //如果设置所有都需要认证的话 则需要把公共资源声明在此条代码之上
        map.put("/user/index", "authc");     // authc表示需要认证和授权
        map.put("/index.jsp", "authc");     // authc表示需要认证和授权
        //默认认证界面路径 /login.jsp
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm){
        //2.创建web安全管理器
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //5.把Realm对象添加到安全管理器中
        securityManager.setRealm(realm);
        return securityManager;
    }

    @Bean("realm")
    public Realm getRealm(){
        //3.创建自定义Realm对象
        //新建一个证明匹配器 加入Md5规则 和 散列次数
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(1024);
        //把匹配器添加到realm中
        CustomerRealm realm = new CustomerRealm();
        realm.setCredentialsMatcher(credentialsMatcher);
        //6. 开启缓存管理(使用shiro默认的 EhCache缓存管理器--须要引入依赖)
//        realm.setCacheManager(new EhCacheManager());
//        使用自定义的Redis缓存管理器
        realm.setCacheManager(new CustomerRedisCacheManager());
        //开启全局缓存管理器
        realm.setCachingEnabled(true);
        //开启认证缓存
        realm.setAuthenticationCachingEnabled(true);
        //设置认证缓存管理器的名称 (有默认值)
        realm.setAuthenticationCacheName("authenticationCache");
        //开启授权管理
        realm.setAuthorizationCachingEnabled(true);
        //设置授权缓存管理器的名称 (有默认值)
        realm.setAuthorizationCacheName("authorizationCache");

        return realm;
    }
}
