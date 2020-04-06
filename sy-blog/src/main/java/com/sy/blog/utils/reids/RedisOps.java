package com.sy.blog.utils.reids;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sy.blog.beans.BlogClassify;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Json字符串存储
 */
public class RedisOps<T> {
    private static Integer SY = 0;
    public static void setJsonString(String key,Object object){
        Jedis jedis = RedisConnection.getJedis();
        jedis.set(key, JSON.toJSONString(object));
        jedis.close();
    }
    public static Object getJsonObject(String key,Class clazz){
        Jedis jedis = RedisConnection.getJedis();
        String value = jedis.get(key);
        jedis.close();
        return JSON.parseObject(value,clazz);
    }
    public void setJsonStringPage(String key, String get, List<T> memberList){
        System.out.println("i come redis" + SY);
        SY = 0;
        if(SY == 0) {
            SY = 1;
            Jedis jedis = RedisConnection.getJedis();
            try {
                for (T object : memberList) {
                    Integer a = (Integer) object.getClass().getMethod("getId",null).invoke(((T)object) ,null);
                    jedis.zadd(key, Double.valueOf(a.intValue()) ,JSON.toJSONString(object));
                    System.out.println("in ///");System.out.println(JSON.toJSONString(object));
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            jedis.close();
//            System.out.println("redis is succeed in");
            SY = 0;
        } else {
            System.out.println(key + "is haved");
        }
    }

    public JSONObject getJsonStringPage (String key, int size ,int curr) {
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> jsonObjectList = new ArrayList<>();
        Jedis jedis = RedisConnection.getJedis();
        Set<String> zrange = jedis.zrange(key, size*curr-size, size*curr-1);
        for (String st : zrange) {
            jsonObject = JSON.parseObject(st);
        }
//        jsonObject.put("total",jedis.zcard(key));
//        jsonObject.put("size",param.getInteger("size"));
//        jsonObject.put("current",param.getInteger("current"));
        return jsonObject;
    }

    public static void main(String[] args) {
         Jedis jedis = new Jedis("127.0.0.1",6379);

         /**
           *com.sy.blog.service.impl.BlogClassifyServiceImpl,100,1,null,null
          * 示例1： zadd zset的key score值 项的值， Score和项可以是多对，score可以是整数，也可以是浮点数，还可以是+inf表示无穷大，-inf表示负无穷大
           */
        Set<String> zrange = jedis.zrange("com.sy.blog.service.impl.BlogClassifyServiceImpl,100,1,null,null", 0, 10);
        System.out.println("zrange = " + zrange);
        System.out.println("zrangeString = " + zrange.toString());
        JSONObject jsonObject = new JSONObject();
        for (String st : zrange) {
            jsonObject = JSON.parseObject(st);
        }
        System.out.println("jsonObject = " + jsonObject);
        jedis.close();
    }
}
