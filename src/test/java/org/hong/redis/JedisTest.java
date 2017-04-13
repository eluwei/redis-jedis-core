package org.hong.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author hong
 * @version v1.1
 * @ClassName: JedisTest
 * @Description: (Jedis 操作redis 测试用例)
 * @date 2017/4/12
 */
public class JedisTest {

    private Jedis jedis;

    @Before
    public void setup() {
        //连接redis服务器，192.168.0.100:6379
        jedis = new Jedis("192.168.6.204", 6379);

        //测试下是否已连接通
        System.out.println(jedis.ping());
    }


    /**
     * redis 存储字符串
     */
    @Test
    public void testRedisString() {
        //添加数据
        jedis.set("name", "hong");//向key -->name 放入了value --> hong
        System.out.println(jedis.get("name"));//获取数据

        //拼接数据
        jedis.append("name", "is my lover");
        System.out.println(jedis.get("name"));

        //删除某个键
        jedis.del("k1");
        System.out.println(jedis.get("k1"));//null

        //同时设置多个键值对
        jedis.mset("k1", "v1", "k2", "v2", "k3", "v3");

        //对数字进行加减
        jedis.mset("num", "1", "num1", "2", "num2", "20", "num3", "50");
        // +1
        jedis.incr("num");
        System.out.println(jedis.get("num"));//给数字加1

        // +N
        jedis.incrBy("num2", 2);
        System.out.println(jedis.get("num2"));//给数字添加指定的值

        // -1
        jedis.decr("num1");
        System.out.println(jedis.get("num1"));//给数字减1

        // -N
        jedis.decrBy("num3", 2);
        System.out.printf(jedis.get("num3"));//给数字减N
    }


    /**
     * jedis 操作list
     */
    @Test
    public void testRedisList() {
        // 开始前，移除所有的内容
        jedis.del("javaees");
        //查询整个集合当前元素
        System.out.println(jedis.lrange("javaees", 0, -1));

        //向key javaees 中存放三条记录
        //[mybatis, springmvc, spring]
        jedis.lpush("javaees", "spring");
        jedis.lpush("javaees", "springmvc");
        jedis.lpush("javaees", "mybatis");

        //取出当前集合的所有数据 jedis.lrange 是按范围获取的。
        //参数说明： 第一个是key值，第二个是起始位置 ，第三个 -1 表示获取所有的。
        System.out.println(jedis.lrange("javaees", 0, -1));

        //[spring, springmvc, mybatis]
        jedis.del("javaees");
        jedis.rpush("javaees", "spring");
        jedis.rpush("javaees", "springmvc");
        jedis.rpush("javaees", "mybatis");
        System.out.println(jedis.lrange("javaees", 0, -1));

        //获取list 集合的长度
        System.out.println(jedis.llen("javaees"));
        //获取指定下标的元素
        System.out.println(jedis.lindex("javaees", 0));

        //根据key值对应的value值 删除 N个指定的元素
        jedis.rpush("javaees", "hibernate");
        System.out.println(jedis.lrem("javaees", 1, "hibernate"));
    }


    /**
     * jedis操作Set
     */
    @Test
    public void testRedisSet() {
        jedis.del("user");
        jedis.sadd("user", "zhangsan");
        jedis.sadd("user", "lisi");
        jedis.sadd("user", "wangwu");
        jedis.sadd("user", "mazi");

        //获取元素集合中的个数
        System.out.println(jedis.scard("user"));

        // Redis Sismember 命令判断成员元素是否是集合的成员。
        System.out.println(jedis.sismember("user", "mazi"));

        //查看set 集合的元素
        List<String> lists = jedis.smembers("user").stream().collect(Collectors.toList());
        lists.forEach(System.out::println);

        // 删除集合中的元素
        long count = jedis.srem("user", "wangwu");
        System.out.println(count);

        // 随机获取N 个集合元素
        List<String> str = jedis.srandmember("user", 1);
        System.out.println(str);

        // 随机出栈
        String item = jedis.spop("user");
        System.out.println(item);

    }

    /**
     * Jedis 操作hash
     */
    @Test
    public void testRedisHash() {

        jedis.del("user");

        //添加一条记录
        jedis.hset("user", "age", "11");

        //获取一条数据
        String age = jedis.hget("user", "age");
        System.out.println(age);


        //-----添加数据----------
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "xinxin");
        map.put("age", "22");
        map.put("qq", "123456");
        map.put("test", "1111");
        jedis.hmset("user", map);
        //单个获取hash 中的元素
        System.out.println("user:" + jedis.hget("user", "name"));
        System.out.println("age:" + jedis.hget("user", "age"));
        System.out.println("qq:" + jedis.hget("user", "qq"));

        System.out.println("==================华丽的分割线====================");

        //获取hash中的所有value值
        List<String> list = jedis.hmget("user", "name", "age", "qq");
        list.forEach(System.out::println);

        //获取hash中所有的value值
        Map<String, String> reMap = jedis.hgetAll("user");
        System.out.println(reMap);

        //删除元素
        jedis.hdel("user", "test");
        System.out.println(jedis.hget("user", "test"));// null 表示已经被删除了

        //查看当前hash 的元素个数
        System.out.println(jedis.hlen("user"));

        //在key里面的某个值的key 是否存在
        Boolean flag = jedis.hexists("user", "age");
        System.out.println("判断user中age 是否存在" + flag);

        //获取hash中所有的key 值
        //由于key不能重复，所以返回的是set 集合
        Set<String> keySet = jedis.hkeys("user");
        System.out.println(keySet);

        //获取hash中所有的value 值
        List<String> vlList = jedis.hvals("user");
        System.out.println(vlList);

        //给对应key 的value值+N
        jedis.hincrBy("user", "age", 2);
        //给对应key 的value值+ 一个float类型的值
        jedis.hincrByFloat("user", "age", 2.5);

        //当对应key值不存在时才会添加
        jedis.hsetnx("user", "age", "111");
    }

    /**
     * Jedis 操作Zset
     */
    @Test
    public void testRedisZset() {

        //添加元素 参数：第一个为key 第二个为score, 第三个为value
        jedis.zadd("word", 100, "test");

        //查看 score 在某个区间的值
        Set<String> set = jedis.zrange("word", 0, 100);
        System.out.println(set);

        //查看 score 在某个区间的值
        Set<String> vlSet = jedis.zrangeByScore("word", 0, 100);
        System.out.println(vlSet);

        Long count = jedis.zrem("word", "test");
        System.out.println("删除了" + count + "记录");

        Long len = jedis.zcard("word");
        System.out.println("当前集合的Zset列表长度：" + len);

        jedis.zadd("word", 10, "value10");
        jedis.zadd("word", 15, "value15");
        jedis.zadd("word", 20, "value20");

        Long zcount = jedis.zcount("word", 10, 15);
        System.out.println("查看score在某一个区间的值：" + zcount);

        Long index = jedis.zrank("word", "15");
        System.out.println("获取指定member 对应的位置下标：" + index);

        //获取member 对应score
        Double score = jedis.zscore("word", "value10");
        System.out.println("获取到value10 对应的score值为："+score);


    }


}
