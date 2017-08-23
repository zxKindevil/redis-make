package com.redis.bio.client;

import com.redis.bio.client.command.Command;
import com.redis.bio.client.reply.*;

import java.io.*;

/**
 * @author zhangxin
 *         Created on 17/8/18.
 */
public class CommandHandler {
    private RedisConnection redisConnection;

    public CommandHandler(RedisConnection redisConnection) {
        this.redisConnection = redisConnection;
    }

    public void write(Command command) throws IOException {
        byte[] toBytes = command.toBytes();
        System.out.println(new String(toBytes));
        redisConnection.outputStream.write(toBytes);
    }

    public Reply read() throws IOException {
        BufferedInputStream inputStream = redisConnection.inputStream;
        int code = inputStream.read();
        if (code == -1) {
            throw new EOFException();
        }
        switch (code) {
            case StatusReply.MARKER: {
                return new StatusReply(new DataInputStream(inputStream).readLine());
            }
            case ErrorReply.MARKER: {
                return new ErrorReply(new DataInputStream(inputStream).readLine());
            }
            case IntegerReply.MARKER: {
                return null;
            }
            case BulkReply.MARKER: {
                return new BulkReply(readBytes(inputStream));
            }
//            case MultiBulkReply.MARKER: {
//                return new MultiBulkReply(inputStream);
//            }
            default: {
                throw new IOException("Unexpected character in stream: " + code);
            }
        }
    }

    public static byte[] readBytes(InputStream is) throws IOException {
        long size = readLong(is);
        if (size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Java only supports arrays up to " + Integer.MAX_VALUE + " in size");
        }
        int read;
        if (size == -1) {
            return null;
        }
        if (size < 0) {
            throw new IllegalArgumentException("Invalid size: " + size);
        }
        byte[] bytes = new byte[(int) size];
        int total = 0;
        int length = bytes.length;
        while (total < length && (read = is.read(bytes, total, length - total)) != -1) {
            total += read;
        }
        if (total < length) {
            throw new IOException("Failed to read enough bytes: " + total);
        }
        int cr = is.read();
        int lf = is.read();
        if (cr != RedisConnection.CR || lf != RedisConnection.LF) {
            throw new IOException("Improper line ending: " + cr + ", " + lf);
        }
        return bytes;
    }

    public static long readLong(InputStream is) throws IOException {
        int sign;
        int read = is.read();
        if (read == '-') {
            read = is.read();
            sign = -1;
        } else {
            sign = 1;
        }
        long number = 0;
        do {
            if (read == -1) {
                throw new EOFException("Unexpected end of stream");
            } else if (read == RedisConnection.CR) {
                if (is.read() == RedisConnection.LF) {
                    return number * sign;
                }
            }
            int value = read - RedisConnection.ZERO;
            if (value >= 0 && value < 10) {
                number *= 10;
                number += value;
            } else {
                throw new IOException("Invalid character in integer");
            }
            read = is.read();
        } while (true);
    }

}
