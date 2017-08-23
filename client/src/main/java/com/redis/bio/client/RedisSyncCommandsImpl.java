package com.redis.bio.client;

import com.redis.bio.client.command.Command;
import com.redis.bio.client.command.CommandType;
import com.redis.bio.client.reply.Reply;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author zhangxin
 *         Created on 17/8/18.
 */
public class RedisSyncCommandsImpl implements RedisSyncCommands {
    private RedisConnection redisConnection;
    private Socket socket;
    private BufferedInputStream inputStream;
    private OutputStream outputStream;

    public RedisSyncCommandsImpl(Socket socket, BufferedInputStream inputStream, OutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public RedisSyncCommandsImpl(RedisConnection redisConnection) {
        this.redisConnection = redisConnection;
    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public String auth(String password) {
        return null;
    }

    @Override
    public String info() {
        Command info = new Command(CommandType.INFO);

        Reply reply = redisConnection.execute(info);

        return "";
    }
}
