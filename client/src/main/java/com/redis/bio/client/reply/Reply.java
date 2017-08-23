package com.redis.bio.client.reply;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zhangxin
 *         Created on 17/8/18.
 */
public interface Reply<T> {
    T data();

    void write(OutputStream os) throws IOException;
}
