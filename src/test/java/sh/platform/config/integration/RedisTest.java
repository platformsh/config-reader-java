package sh.platform.config.integration;

import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import sh.platform.config.Config;
import sh.platform.config.Redis;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RedisTest {

    private final GenericContainer redis =
            new GenericContainer("redis:latest")
                    .withExposedPorts(6379)
                    .waitingFor(Wait.defaultWaitStrategy());


    @Test
    public void shouldRunIntegrationTest() {
        redis.start();
        System.setProperty("redis.host", redis.getContainerIpAddress());
        System.setProperty("redis.port", Integer.toString(redis.getFirstMappedPort()));
        Config config = new Config();
        Redis database = config.getCredential("redis", Redis::new);
        JedisPool dataSource = database.get();
        final Jedis jedis = dataSource.getResource();

        jedis.sadd("cities", "Salvador");
        jedis.sadd("cities", "London");
        jedis.sadd("cities", "São Paulo");

        Set<String> cities = jedis.smembers("cities");
        assertFalse(cities.isEmpty());
        jedis.del("cities");
        assertTrue(jedis.smembers("cities").isEmpty());

        System.clearProperty("redis.host");
        System.clearProperty("redis.port");

        redis.stop();

    }
}
