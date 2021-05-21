package com.xsx.boot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    //User实体类
    private Integer id;

    private String username;

    private String password;

    private String salt;

    //定义角色集合
    private List<Role> roles;

}