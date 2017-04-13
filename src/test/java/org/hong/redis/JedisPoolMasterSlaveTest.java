package org.hong.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Created by hong on 2017/4/12.
 */
public class JedisPoolMasterSlaveTest {

    private ShardedJedisPool pool;

    @Before
    public void setup() {
        JedisPoolMasterSlave jedisPoolMasterSlave =new JedisPoolMasterSlave();
        pool = jedisPoolMasterSlave.getJedisPool();
    }

    @Test
    public void test(){
        ShardedJedis jedis = pool.getResource();
        System.out.println(jedis.get("k1"));
    }
}