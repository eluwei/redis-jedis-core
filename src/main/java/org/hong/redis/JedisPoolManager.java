package org.hong.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 * @author hong
 * @version v1.1
 * @ClassName: JedisPoolManager
 * @Description: (Jedis 对象池工具)
 * @date 2017/4/12
 */
public class JedisPoolManager {

    private static JedisPool pool;

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
        pool = new JedisPool(jedisPoolConfig,bundle.getString("redis.ip"), Integer.parseInt(bundle.getString("redis.port")));

        System.out.println(pool.getResource().ping());
    }

    public JedisPool getJedisPool(){
        return pool;
    }
}
