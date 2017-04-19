package org.hong.redis;

import org.hong.redis.utils.RedisTemplateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


//使用junit4进行测试 spring 相关
@RunWith(SpringJUnit4ClassRunner.class)

//加载spring 配置文件
@ContextConfiguration(locations = {"classpath:spring-redis.xml"})
public class RedisTemplateUtilsTest {



    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    private RedisTemplateUtils redisTemplateUtils;

    @Before
    public void setInit(){
         redisTemplateUtils =new RedisTemplateUtils();
    }


    @Test
    public void testGet(){
        String v1 = redisTemplateUtils.getStrValue("k1");
        System.out.println(v1);
    }







    /**
     * @Description: 删除缓存（根据key精确删除,key可以多个，已逗号分隔）
     * @param key
     * @throws
     */
    public  void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 0) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }


    /**
     * @Description: 获取缓存（int型）
     * @param key
     * @return 返回key对应value 值
     * @throws
     */
    public  Integer getIntValue(String key){
        String value= stringRedisTemplate.boundValueOps(key).get();
        if(!StringUtils.isEmpty(value)){
            return Integer.valueOf(value);
        }
        return null;
    }

    /**
     * @Description: 获取缓存（String 型）
     * @param
     * @return
     * @throws
     */
    public  String getStrValue(String key){
        return stringRedisTemplate.boundValueOps(key).get();
    }


    /**
     * @Description: 获取缓存（String 型） ,并可以选择删除
     * @param  key  retain(true/false)
     * @return
     * @throws
     */
    public  String getStrValueDel(String key,boolean retain){
        String value =stringRedisTemplate.boundValueOps(key).get();
        if(retain){
            redisTemplate.delete(key);
        }
        return value;
    }

    /**
     * @Description: 获取缓存
     * @param key
     * @return
     * @throws
     */
    public  Object getObjValue(String key){
        return redisTemplate.boundValueOps(key).get();
    }


    /**
     * @Description:  获取缓存（String 型） ,并可以选择删除
     * @param  key  retain(true/false)
     * @return
     * @throws
     */
    public  Object getObjValueDel(String key,boolean retain){
        Object value =  redisTemplate.boundValueOps(key).get();
        if(retain){
            redisTemplate.delete(key);
        }
        return value;
    }






}
