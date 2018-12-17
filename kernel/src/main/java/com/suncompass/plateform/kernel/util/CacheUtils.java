package com.suncompass.plateform.kernel.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * Cache工具类
 * Created by alex on 2017/12/24.
 *
 * @author by alex.
 * @version 1.0.0.
 */
public class CacheUtils {
    private static final Logger logger = LoggerFactory.getLogger(CacheUtils.class);
    @Autowired
    private static CacheManager cacheManager;
    private static final String DEFAULT_CACHE_NAME = "defaultCache";

    /**
     * 获取缺省缓存
     * @param key
     * @return
     */
    public static Object get(String key){
        return get(DEFAULT_CACHE_NAME, key);
    }

    /**
     * 写入缺省缓存
     * @param key
     * @param value
     */
    public static void put(String key, Object value){
        put(DEFAULT_CACHE_NAME, key, value);
    }

    /**
     * 获取缓存
     * @param cacheName
     * @param key
     */
    public static Object get(String cacheName, String key){
        return getCache(cacheName).get(key);
    }

    /**
     * 写入缓存
     * @param cacheName
     * @param key
     * @param value
     */
    public static void put(String cacheName, String key, Object value){
        getCache(cacheName).put(key, value);
    }

    /**
     * 获得一个Cache，没有则显示日志。
     * @param cacheName
     * @return
     */
    private static Cache getCache(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null){
            throw new RuntimeException("系统中没有定义["+cacheName+"]缓存。");
        }
        return cache;
    }
}
