package redis.client;

import com.redis.bio.client.CommandHandler;
import com.redis.bio.client.RedisClient;
import com.redis.bio.client.RedisSyncCommands;
import com.redis.bio.client.RedisURI;
import com.redis.bio.client.reply.BulkReply;
import com.redis.bio.client.reply.ErrorReply;
import com.redis.bio.client.reply.IntegerReply;
import com.redis.bio.client.reply.StatusReply;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class RedisTest {
    @Test
    public void test() {
        RedisURI uri = new RedisURI.RedisURIBuilder().withHost("10.32.64.19").withPort(6379).build();
        RedisClient client = new RedisClient(uri);

        RedisSyncCommands sync = client.connect().sync();

        sync.set("test","str");

        String test = sync.get("test");

    }

    @Test
    public void a() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("10.32.64.19", 6379));

        byte[] bytes = "*1\r\n$4\r\nINFO\r\n".getBytes();
        System.out.println(Arrays.toString(bytes));
        socket.getOutputStream().write(bytes);

        int code = socket.getInputStream().read();

        System.out.println((char) code);

        switch (code) {
            case StatusReply.MARKER: {
                System.out.println("1");
                break;
            }
            case ErrorReply.MARKER: {
                break;
            }
            case IntegerReply.MARKER: {
                break;
            }
            case BulkReply.MARKER: {
                BulkReply bulkReply = new BulkReply(CommandHandler.readBytes(socket.getInputStream()));
                System.out.println(bulkReply.asAsciiString());
                break;
            }
            default: {
                throw new IOException("Unexpected character in stream: " + code);
            }
        }
    }

}
