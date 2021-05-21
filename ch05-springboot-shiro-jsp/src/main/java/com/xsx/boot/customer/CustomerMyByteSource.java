package com.xsx.boot.customer;

import org.apache.shiro.util.SimpleByteSource;

import java.io.Serializable;

//自定义salt实现  实现序列化接口
public class CustomerMyByteSource extends SimpleByteSource implements Serializable {

    public CustomerMyByteSource(String string) {
        super(string);
    }
}
