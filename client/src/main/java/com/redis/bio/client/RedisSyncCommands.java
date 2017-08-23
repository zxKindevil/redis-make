package com.redis.bio.client;

/**
 * 命令api
 *
 * @author zhangxin
 *         Created on 17/8/18.
 */
public interface RedisSyncCommands {

    String get(String key);

    String auth(String password);

    String info();
}
