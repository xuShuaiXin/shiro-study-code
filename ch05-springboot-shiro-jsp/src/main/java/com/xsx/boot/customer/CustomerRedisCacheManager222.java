package com.xsx.boot.customer;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

//自定义Redis缓存管理器 需要实现CacheManager接口
public class CustomerRedisCacheManager222 implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        System.out.println(cacheName);

        return new CustomerRedisCache222<K, V>(cacheName);
    }
}
