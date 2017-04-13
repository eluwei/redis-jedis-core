package org.hong.redis;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @ClassName: JedisPoolMasterSlave
 * @Description: ( Jedis Master-Slave模式)
 * @author hong
 * @date 2017/4/12
 * @version v1.1
 */
public class JedisPoolMasterSlave {

    private static  ShardedJedisPool shardedJedisPool;

    static {
        //获取jedis 对应的资源文件
        ResourceBundle bundle = ResourceBundle.getBundle("jedis-pool");
        if (bundle == null) {
            throw new IllegalArgumentException("没有找到对应资源文件");
        }

        //获取Jedis 连接池
        JedisPoolConfig jedisPoolConfig =new JedisPoolConfig();
        //设置连接池属性
        jedisPoolConfig.setMaxTotal(Integer.valueOf(bundle.getString("redis.pool.maxActive")));
        jedisPoolConfig.setMaxIdle(Integer.valueOf(bundle.getString("redis.pool.maxIdle")));
        jedisPoolConfig.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.pool.maxWait")));
        jedisPoolConfig.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));
        jedisPoolConfig.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn")));

        //创建连接池对象
        JedisShardInfo jedisShardInfo =new JedisShardInfo(bundle.getString("redis.ip"),bundle.getString("redis.port"));
        JedisShardInfo jedisShardInfo2 =new JedisShardInfo(bundle.getString("redis.ip"),bundle.getString("redis.port2"));
        JedisShardInfo jedisShardInfo3 =new JedisShardInfo(bundle.getString("redis.ip"),bundle.getString("redis.port3"));

        List<JedisShardInfo> jedisShardInfoList =new LinkedList<>();
        jedisShardInfoList.add(jedisShardInfo);
        jedisShardInfoList.add(jedisShardInfo2);
        jedisShardInfoList.add(jedisShardInfo3);
        shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, jedisShardInfoList);
    }

    public ShardedJedisPool getJedisPool(){
        return shardedJedisPool;
    }
}
