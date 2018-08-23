package com.kingdee.hrp.sms.util;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 简化Redis数据访问的一个帮助类，
 * 此类对（RedisTemplate）Redis命令进行高级封装，通过此类可以调用ValueOperations和ListOperations等等方法。
 *
 * @author yadda
 */
public final class RedisUtils {

    @SuppressWarnings("unchecked")
    private static RedisTemplate<Serializable, Object> redisTemplate = Environ.getBean(RedisTemplate.class);
    ;

    /**
     * 批量删除对应的value
     *
     * @param keys keys
     */
    public static void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern pattern
     */
    public static void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key key
     */
    public static void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 缓存是否存在
     *
     * @param key key
     * @return boolean
     */
    public static boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key key
     * @return Object
     */
    public static Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 读取缓存
     *
     * @param key     key
     * @param hashKey hashKey
     * @return Object
     */
    public static Object get(final String key, final String hashKey) {
        Object result = null;
        HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
        result = operations.get(key, hashKey);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key   key
     * @param value value
     * @return boolean
     */
    public static boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    public static boolean set(final String key, final String hashKey, Object value) {
        boolean result = false;
        try {
            HashOperations<Serializable, Object, Object> operations = redisTemplate.opsForHash();
            operations.put(key, hashKey, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
