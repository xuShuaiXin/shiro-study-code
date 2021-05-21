package com.xsx.boot.mapper;

import com.xsx.boot.model.Perms;
import com.xsx.boot.model.Role;
import com.xsx.boot.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    //根据身份信息认证的方法
    User selectByUserName(@Param("username") String username);
    //根据用户名查询所有角色
    User findRolesByUserName(@Param("username") String username);
    //根据角色id查询权限资源字符串
    List<Perms> findPermsByRoleId(@Param("rId") Integer rId);

}