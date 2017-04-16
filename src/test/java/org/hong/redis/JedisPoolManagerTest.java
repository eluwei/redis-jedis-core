package org.hong.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author hong
 * @version v1.1
 * @ClassName: JedisPoolManagerTest
 * @Description: (测试JedisPool 对象池)
 * @date 2017/4/12
 */
public class JedisPoolManagerTest {

    private JedisPool pool;

    @Before
    public void setup() {
        JedisPoolManager jedisPoolManager = new JedisPoolManager();
        pool = jedisPoolManager.getJedisPool();
    }

    @Test
    public void testJedisPool() {
        Jedis jedis = pool.getResource();
        System.out.println(jedis.ping());
        System.out.println("当前redis key 个数：" + jedis.dbSize());

        // 释放对象池
        // 这个方法已过时
        // pool.returnResource(jedis); 因为jedis.close()时,会调用returnResource 方法。
        jedis.close();
    }
}
