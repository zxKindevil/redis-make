package com.redis.bio.client.command;

import java.util.List;

/**
 * @author zhangxin
 *         Created on 17/8/21.
 */
public class CommandArgs {
    List<Object> args;

    public List<Object> getArgs() {
        return args;
    }

    public void setArgs(List<Object> args) {
        this.args = args;
    }
}
