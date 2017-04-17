package org.hong.redis;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
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
    private RedisTemplate redisTemplate;


    @Test
    public void testSpringRedis(){
        Set<String> redisKeys = redisTemplate.keys("*");
        System.out.println("key值个数:"+redisKeys.size());
    }

}
