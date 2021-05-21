package com.xsx.boot.customer;

import com.xsx.boot.uiti.ApplicationContextUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

//自定义的 Cache接口实现类
@Component
public class CustomerRedisCache<K,V> implements Cache<K,V> {
    @Resource
    private RedisKeyValueTemplate redisKeyValueTemplate;
    private String cacheName;

    public CustomerRedisCache() {
    }

    public CustomerRedisCache(String cacheName){
        this.cacheName = cacheName;
    }
    @Override
    public V get(K k) throws CacheException {
        System.out.println("get key:"+k);
        return (V) getRedisTemplate().opsForHash().get(this.cacheName,k.toString());
    }

    @Override
    public V put(K k, V v) throws CacheException {
        System.out.println("put key: "+k);
        System.out.println("put value:"+v);
        getRedisTemplate().opsForHash().put(this.cacheName,k.toString(),v);
        return null;
    }

    @Override
    public V remove(K k) throws CacheException {
        System.out.println("=============remove=============");
        return (V) getRedisTemplate().opsForHash().delete(this.cacheName,k.toString());
    }

    @Override
    public void clear() throws CacheException {
        System.out.println("=============clear==============");
        getRedisTemplate().delete(this.cacheName);
    }

    @Override
    public int size() {
        return getRedisTemplate().opsForHash().size(this.cacheName).intValue();
    }

    @Override
    public Set<K> keys() {
        return getRedisTemplate().opsForHash().keys(this.cacheName);
    }

    @Override
    public Collection<V> values() {
        return getRedisTemplate().opsForHash().values(this.cacheName);
    }

    //封装获取redisTemplate 并已经序列化
    private RedisTemplate getRedisTemplate(){
        //1.使用自定义工具类获取 bean
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        //2.设置字符串类型的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //3.设置hash类型的序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
