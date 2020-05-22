package com.github.east196.ezsb.redis;

/**
 * Redis所有Keys
 *
 * @author east196
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }
}
