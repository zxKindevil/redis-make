package com.redis.bio.client.command;

import java.nio.charset.Charset;

/**
 * @author zhangxin
 *         Created on 17/8/18.
 */
public interface CommandKeyword {

    byte[] getBytes();

    String name();

}
