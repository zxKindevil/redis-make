package redis.client;

import com.redis.bio.client.RedisClient;
import com.redis.bio.client.RedisSyncCommands;
import com.redis.bio.client.RedisURI;
import com.redis.bio.client.command.Command;
import com.redis.bio.client.command.CommandType;
import com.redis.bio.client.command.EncodeDic;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class RedisTest {
    @Test
    public void test() {
        RedisURI uri = new RedisURI.RedisURIBuilder().withHost("10.32.64.19").withPort(6379).build();
        RedisClient client = new RedisClient(uri);

        RedisSyncCommands sync = client.connect().sync();

        String test = sync.get("test");

    }

    @Test
    public void a() throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("10.32.64.19", 6379));

        byte[] bytes = "*1\r\n$4\r\nINFO\r\n".getBytes();
        socket.getOutputStream().write(bytes);


        System.out.println(Arrays.toString(bytes));


        while (socket.getInputStream().read() != -1) {
            System.out.println(socket.getInputStream().read());
        }

    }

    @Test
    public void toBytes() {
        Command command = new Command(CommandType.INFO);
        byte[] bytes = command.toBytes();
        System.out.println(Arrays.toString(bytes));


        System.out.println(Arrays.toString("*1\r\n$4\r\nINFO\r\n".getBytes()));

        System.out.println(Arrays.toString("INFO".getBytes()));
    }

    @Test
    public void b() {
        ByteBuffer buffer = ByteBuffer.allocate(100);

        buffer.put("*".getBytes());
        buffer.put("1".getBytes());

        byte[] aByte = EncodeDic.getByte(buffer);
        System.out.println(Arrays.toString(aByte));
    }
}
