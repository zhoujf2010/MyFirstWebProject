import java.net.URI;
import java.net.URISyntaxException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class testRedis
{

    public static void main(String[] args) throws URISyntaxException {

        String uri = "redis://192.168.201.60:6379/9";
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10000);
        config.setMaxIdle(10000);
        config.setMaxWaitMillis(1000 * 30);// 最大等待
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        JedisPool jedisPool = new JedisPool(config, new URI(uri), 1000 * 100);// 超时时间
        Jedis jredis = jedisPool.getResource();

        String val = jredis.get("key");
        System.out.println("oldValue=" + val);

        jredis.append("key", "testvalue");

        jedisPool.close();

    }

}
