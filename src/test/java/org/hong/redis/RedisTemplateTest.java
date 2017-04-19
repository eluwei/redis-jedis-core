package org.hong.redis;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @ClassName: RedisTemplateTest
 * @Description: (测试spring 整合redis )
 * @author hong
 * @date 2017/4/17
 * @version v1.1
 */




//使用junit4进行测试 spring 相关
@RunWith(SpringJUnit4ClassRunner.class)

//加载spring 配置文件
@ContextConfiguration(locations = {"classpath:spring-redis.xml"})
//------------如果加入以下代码，所有继承该类的测试类都会遵循该配置，也可以不加，在测试类的方法上///控制事务，参见下一个实例
//这个非常关键，如果不加入这个注解配置，事务控制就会完全失效！
//@Transactional
//这里的事务关联到配置文件中的事务控制器（transactionManager = "transactionManager"），同时//指定自动回滚（defaultRollback = true）。这样做操作的数据才不会污染数据库！
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//------------

public class RedisTemplateTest {


    @Resource
    private RedisTemplate<String, String> redisTemplate;


    /**
     jedis客户端在编程实施方面存在如下不足：
     1) connection管理缺乏自动化，connection-pool的设计缺少必要的容器支持。
     2) 数据操作需要关注“序列化”/“反序列化”，因为jedis的客户端API接受的数据类型为string和byte，对结构化数据(json,xml,pojo等)操作需要额外的支持。
     3) 事务操作纯粹为硬编码
     4) pub/sub功能，缺乏必要的设计模式支持，对于开发者而言需要关注的太多。

     所以使用spring-data-redis 提供了基于jedis客户端API的高度封装以及与spring容器的整合
     */
    @Test
    public void testSpringRedis(){
        Set<String> redisKeys = redisTemplate.keys("*");
        System.out.println("key值个数:"+redisKeys.size());


    }

}
