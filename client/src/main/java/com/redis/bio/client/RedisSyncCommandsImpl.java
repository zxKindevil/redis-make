package com.redis.bio.client;

import com.redis.bio.client.command.Command;
import com.redis.bio.client.command.CommandType;
import com.redis.bio.client.reply.Reply;

/**
 * @author zhangxin
 *         Created on 17/8/18.
 */
public class RedisSyncCommandsImpl implements RedisSyncCommands {
    private CommandHandler commandHandler;

    public RedisSyncCommandsImpl(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
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

        Reply reply = commandHandler.getRedisConnection().execute(info);

        return "";
    }
}
