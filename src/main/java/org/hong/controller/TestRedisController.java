package org.hong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

/**
 * Created by hong on 2017/4/17.
 */
@Controller
@RequestMapping("/test")
public class TestRedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/redis")
    public void testRedis(){
        Set<String> redisSet = redisTemplate.keys("*");
        System.out.println(redisSet.size());
    }
}
