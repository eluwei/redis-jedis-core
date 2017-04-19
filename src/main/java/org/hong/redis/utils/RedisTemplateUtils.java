package org.hong.redis.utils;

import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author hong
 * @version v1.1
 * @ClassName: RedisTemplateUtils
 * @Description: (通用缓存工具类)
 * @date 2017/4/18
 */
@Component
public class RedisTemplateUtils {


    @Autowired
    private  StringRedisTemplate stringRedisTemplate;

    @Autowired
    private  RedisTemplate redisTemplate;



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
