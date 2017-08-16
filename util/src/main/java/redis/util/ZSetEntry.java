package redis.util;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * Created with IntelliJ IDEA.
 * User: spullara
 * Date: 7/22/12
 * Time: 4:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZSetEntry implements Comparable<ZSetEntry> {
    private final BytesKey key;
    private double score;

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ZSetEntry && key.equals(((ZSetEntry) obj).key);
    }

    public ZSetEntry(BytesKey key, double score) {
        this.key = key;
        this.score = score;
    }

    @Override
    public int compareTo(ZSetEntry o) {
        double diff = score - o.score;
        return diff < 0 ? -1 : diff == 0 ? 0 : 1;
    }

    public BytesKey getKey() {
        return key;
    }

    public double getScore() {
        return score;
    }

    public double increment(double value) {
        return score += value;
    }

    public static void main(String[] args) throws InterruptedException {
        RedisURI redisURI = RedisURI.builder().withSentinelMasterId("mymaster").withSentinel("127.0.0.1", 26379).build();

        RedisClient redisClient = RedisClient.create(redisURI);

        RedisAsyncCommands<String, String> async = redisClient.connect().async();

        RedisFuture<String> test = async.get("test");


        test.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                System.out.println(s);
            }
        });

        TimeUnit.SECONDS.sleep(2);
    }
}
