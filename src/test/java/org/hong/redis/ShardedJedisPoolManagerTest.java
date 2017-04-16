package org.hong.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author
 * @version v1.1
 * @ClassName: ShardedJedisPoolManagerTest
 * @Description: (对Jedis 分片式集群测试)
 * @date 2017/4/16
 */
public class ShardedJedisPoolManagerTest {

    private ShardedJedisPool pool;

    @Before
    public void setup() {
        ShardedJedisPoolManager shardedJedisPoolManager = new ShardedJedisPoolManager();
        pool = shardedJedisPoolManager.getJedisPool();
    }

    @Test
    public void test() {
        ShardedJedis jedis = pool.getResource();

        for (int i = 0; i < 100; i++) {
            jedis.set("key_"+i,"value_"+i);
        }
        System.out.println("ok");
    }
}
