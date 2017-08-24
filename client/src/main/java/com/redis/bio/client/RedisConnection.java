package com.redis.bio.client;

import com.redis.bio.client.command.Command;
import com.redis.bio.client.reply.Reply;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author zhangxin
 *         Created on 17/8/17.
 */
public class RedisConnection {

    public static final char CR = '\r';
    public static final char LF = '\n';
    public static final char ZERO = '0';
    protected InputStream inputStream;
    protected OutputStream outputStream;
    private final Socket socket;
    private RedisSyncCommands redisSyncCommands;
    private CommandHandler commandHandler;

    public RedisConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
        this.commandHandler = new CommandHandler(this);

        this.redisSyncCommands = new RedisSyncCommandsImpl(commandHandler);
    }

    public synchronized Reply execute(Command command) {

        try {
            commandHandler.write(command);

            Reply reply = commandHandler.read();

            System.out.println(reply.data());

            return reply;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Read fixed size field from the stream.
     *
     * @param is
     * @return
     * @throws IOException
     */
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
        if (cr != CR || lf != LF) {
            throw new IOException("Improper line ending: " + cr + ", " + lf);
        }
        return bytes;
    }

    /**
     * Read a signed ascii integer from the input stream.
     *
     * @param is
     * @return
     * @throws IOException
     */
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
            } else if (read == CR) {
                if (is.read() == LF) {
                    return number * sign;
                }
            }
            int value = read - ZERO;
            if (value >= 0 && value < 10) {
                number *= 10;
                number += value;
            } else {
                throw new IOException("Invalid character in integer");
            }
            read = is.read();
        } while (true);
    }


    public RedisSyncCommands sync() {
        return redisSyncCommands;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Socket getSocket() {
        return socket;
    }
}
