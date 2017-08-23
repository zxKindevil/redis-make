package com.redis.bio.client;

import com.google.common.base.Strings;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author zhangxin
 *         Created on 17/8/17.
 */
public class RedisClient {

    private RedisURI redisURI;
    private RedisConnection connection;

    public RedisClient(RedisURI redisURI) {
        this.redisURI = redisURI;
    }

    public RedisConnection connect() {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(redisURI.getHost(), redisURI.getPort()));
            System.out.println("connected suc");
            this.connection = new RedisConnection(socket);
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }

        if (!Strings.isNullOrEmpty(this.redisURI.getPassword())) {
            this.connection.sync().auth(this.redisURI.getPassword());
        }

        connection.sync().info();

        return connection;
    }

}
