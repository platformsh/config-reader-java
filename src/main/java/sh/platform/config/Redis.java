package sh.platform.config;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;
import java.util.function.Supplier;

/**
 * 
 */
public class RedisJedisPool extends Credential implements Supplier<JedisPool> {

    private static final int DEFAULT_TIMEOUT = 2000;

    public RedisJedisPool(Map<String, Object> config) {
        super(config);
    }

    @Override
    public JedisPool get() {

        String host = getStringSafe("host").orElse(null);
        Integer port = getInt("port");
        String password = getStringSafe("password").orElse(null);
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        return new JedisPool(poolConfig, host, port, DEFAULT_TIMEOUT, password);
    }
}
