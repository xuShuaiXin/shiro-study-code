package com.xsx.boot.service.impl;

import com.xsx.boot.mapper.UserMapper;
import com.xsx.boot.model.User;
import com.xsx.boot.service.UserService;
import com.xsx.boot.uiti.SaltUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public Boolean addUser(String username, String password) {
        //获取盐
        String salt = SaltUtils.getSalt(8);
        log.info(" salt ****" + salt);
        //加密并设置散列次数为 1024
        Md5Hash md5Hash = new Md5Hash(password, salt, 1024);
        String md5Password = md5Hash.toHex();
        log.info(" md5Password ****" + md5Password);
        //新建一个user对象作为参数
        User user = new User();
        user.setUsername(username);
        user.setSalt(salt);
        user.setPassword(md5Password);
        //插入到数据库中
        if (userMapper.insert(user) >= 1){
            return true;
        }
        return false;
    }

    @Override
    public Boolean loginUser(String username, String password) {

        return false;
    }

    @Override
    public Boolean logout() {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            log.info("退出失败");
        }
        return false;
    }

    @Override
    public User selectByUserName(String primaryPrincipal) {
        User user = userMapper.findRolesByUserName(primaryPrincipal);
        System.out.println(user);
//        System.out.println(userMapper.findPermsByRoleId(1));
        user.getRoles().forEach(role -> {
            role.setPerms(userMapper.findPermsByRoleId(role.getId()));
        });
        return user;
    }
}
