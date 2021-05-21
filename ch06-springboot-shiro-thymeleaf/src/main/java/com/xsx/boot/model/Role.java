package com.xsx.boot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    //角色表
    private Integer id;
    private String name;

    //权限属性
    private List<Perms> perms;
}
