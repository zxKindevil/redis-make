package com.redis.bio.client.command;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhangxin
 *         Created on 17/8/18.
 */
public class Command {
    public static final char ARGS_PREFIX = '*';
    public static final char BYTES_PREFIX = '$';
    public static final byte[] CRLF = "\r\n".getBytes();
    public static final byte[] EMPTY_BYTES = new byte[0];

    private final CommandKeyword type;

    protected List<Object> args = Lists.newArrayList();

    public Command(CommandKeyword type) {
        this.type = type;
    }

    public byte[] toBytes() {
        return this.encode();
    }

    private byte[] encode() {
        int argLength = args == null ? 0 : args.size();


        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //*?
        buffer.putChar(ARGS_PREFIX);
        buffer.put(EncodeDic.numToBytes(argLength + 1));

        //$?
        this.writeObject(buffer, type.getBytes());

        //$?
        for (Object arg : args) {
            this.writeObject(buffer, arg);
        }

        return EncodeDic.getByte(buffer);
    }

    private void writeObject(ByteBuffer buffer, Object object) {
        byte[] argument;
        if (object == null) {
            argument = EMPTY_BYTES;
        } else if (object instanceof byte[]) {
            argument = (byte[]) object;
        } else {
            argument = object.toString().getBytes(Charsets.UTF_8);
        }

        buffer.putChar(BYTES_PREFIX);
        buffer.put(EncodeDic.numToBytes(argument.length, true));
        buffer.put(argument);
        buffer.put(CRLF);
    }

    public static void main(String[] args) {
//        byte[] bytes = new byte[10];
//        ByteBuffer buffer = ByteBuffer.allocate(100);
//        buffer.putChar('x');
//        ByteBuffer x = buffer.get(bytes);
//        System.out.println(Arrays.toString(bytes));
//
//        System.out.println((int) ARGS_PREFIX);

        Command info = new Command(CommandType.INFO);
        System.out.println(info.toBytes().length);

        System.out.println(Arrays.toString(info.toBytes()));
        System.out.println(new String());
    }

}