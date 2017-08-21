import java.net.URI;
import java.net.URISyntaxException;

import org.apache.shiro.session.Session;

import com.zjf.cluster.SessionRedisDao;

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
        
        byte[] bts = jredis.get("c4fdb8df-36a9-4745-8daf-4d02f7759800".getBytes());
        Session tt = new SessionRedisDao().byteToSession(bts);    

        String val = jredis.get("key");
        System.out.println("oldValue=" + val);

        jredis.append("key", "testvalue");

        jedisPool.close();

    }

}
