package org.hong.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by hong on 2017/4/15.
 */
public class TsetMasterSlave {

    private Jedis jedis_master;

    private Jedis jedis_slave;


    @Before
    public void setup(){
        jedis_master =new Jedis("127.0.0.1",6379);
        jedis_slave =new Jedis("127.0.0.1",6380);
    }


    @Test
    public void testMasterSlave(){
        jedis_slave.slaveof("127.0.0.1",6379);

        jedis_master.set("master","port:6379");

        System.out.println(jedis_slave.get("master"));
    }
}
