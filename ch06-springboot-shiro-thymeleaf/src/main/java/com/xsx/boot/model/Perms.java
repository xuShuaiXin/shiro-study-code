package com.xsx.boot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Perms implements Serializable {
    //权限资源表
    private Integer id;
    private String name;
    private String url;
}
