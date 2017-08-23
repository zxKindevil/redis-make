package com.redis.bio.client;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangxin
 *         Created on 17/8/18.
 */
public class RedisURI {

    public static final int DEFAULT_REDIS_PORT = 6379;
    public static final long DEFAULT_TIMEOUT = 60;
    public static final TimeUnit DEFAULT_TIMEOUT_UNIT = TimeUnit.SECONDS;

    private String host;
    private int port;
    private String password;
    private TimeUnit unit = TimeUnit.SECONDS;

    public RedisURI(String host, int port, String password, TimeUnit unit) {
        this.host = host;
        this.port = port;
        this.password = password;
        this.unit = unit;
    }


    public static final class RedisURIBuilder {
        private String host;
        private int port;
        private String password;
        private TimeUnit unit = TimeUnit.SECONDS;

        public RedisURIBuilder() {
        }

        public static RedisURIBuilder aRedisURI() {
            return new RedisURIBuilder();
        }

        public RedisURIBuilder withHost(String host) {
            this.host = host;
            return this;
        }

        public RedisURIBuilder withPort(int port) {
            this.port = port;
            return this;
        }

        public RedisURIBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public RedisURIBuilder withUnit(TimeUnit unit) {
            this.unit = unit;
            return this;
        }

        public RedisURI build() {
            RedisURI redisURI = new RedisURI(host, port, password, unit);
            return redisURI;
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }
}
