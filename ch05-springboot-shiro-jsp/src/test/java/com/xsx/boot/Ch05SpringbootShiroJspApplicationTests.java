package com.xsx.boot;

import com.xsx.boot.mapper.UserMapper;
import com.xsx.boot.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class Ch05SpringbootShiroJspApplicationTests {

    @Resource
    private UserService service;

    @Resource
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        service.addUser("xsx", "123");
    }
    @Test
    void contextLoads2() {
        System.out.println(userMapper.selectByUserName("xsx"));
    }
    @Test
    void contextLoads3() {
        System.out.println(userMapper.findRolesByUserName("chen"));
    }

    @Test
    void contextLoads4() {
        System.out.println(service.selectByUserName("xsx"));
    }

}
