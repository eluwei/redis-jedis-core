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

        //设置最大连接数
        jedisPoolConfig.setMaxTotal(Integer.valueOf(bundle.getString("redis.pool.maxActive")));

        //最大能够保持idel状态的对象数
        jedisPoolConfig.setMaxIdle(Integer.valueOf(bundle.getString("redis.pool.maxIdle")));

        //当池内没有返回对象时，最大等待时间
        jedisPoolConfig.setMaxWaitMillis(Long.valueOf(bundle.getString("redis.pool.maxWait")));

        //当调用borrow Object方法时，是否进行有效性检查
        jedisPoolConfig.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.pool.testOnBorrow")));

        //当调用return Object方法时，是否进行有效性检查
        jedisPoolConfig.setTestOnReturn(Boolean.valueOf(bundle.getString("redis.pool.testOnReturn")));

        //创建连接池对象
        pool = new JedisPool(jedisPoolConfig,bundle.getString("redis.ip"), Integer.parseInt(bundle.getString("redis.port")));

        System.out.println(pool.getResource().ping());


    }

    public JedisPool getJedisPool(){
        return pool;
    }
}
