package com.sy.blog.utils.reids;

import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * 只是用来连接redis和一些操作
 * @author Su.Yang
 */
public class RedisUtil {

    private static Jedis jedis;

    /**
     * 连接
     * @return
     */
    private static Jedis connectRedis() {
        if(jedis == null) {
            //连接本地的 Redis 服务
//            jedis = new Jedis("localhost", 6379);
            jedis = RedisConnection.getJedis();
            System.out.println("连接成功");
            //查看服务是否运行
            System.out.println("服务正在运行: " + jedis.ping());

        }
        return jedis;
    }

    /**
     *增加
     * @param key
     * @param secound
     * @param value
     */
    public static void putSomething (String key,int secound,String value) {
         jedis = connectRedis();
         jedis.setex(key,secound,value);
        System.out.println("增加了key="+ key + "secound=" + secound + "value=" + value);
        jedis.disconnect();
    }

    /**
     * 获取
     * @param key
     * @return
     */
    public static String getSomething (String key) {
        jedis = connectRedis();
        String re = "";
        if(exists(key)) {
            re = jedis.get(key);
        }
        jedis.disconnect();
        return re;
    }

    /**
     * 移除token
     * @param key
     */
    public static void moveSomething (String key) {
        jedis = connectRedis();
        jedis.del(key);
        System.out.println("移除了="+ key);
        jedis.disconnect();
    }

    /**
     * 检查token
     * @param token
     * @return
     */
    public static boolean exists (String token){
        jedis = connectRedis();
        boolean flag = jedis.exists(token);
        jedis.disconnect();
        return flag;
    }

    /**
     * 刷新token有效时间
     * @param key
     * @return
     */
    public static void reTrueToken (String key) {
        jedis = connectRedis();
//        System.out.println("我在刷新了");
//        System.out.println("i re true");
        if(jedis.exists(key)) {//todo 这里有Bug,还需要一个记住我功能
            System.out.println("刷新了="+ key +"=有效时间");
            jedis.expire("key",60*60*24);
        }
        jedis.disconnect();
    }

    /**
     * 清除所有key
     */
    public static void flushAll(){
        jedis = connectRedis();
        jedis.flushAll();
        System.out.println("清理了全部key");
        jedis.disconnect();
    }

    public static void main(String[] args) throws InterruptedException {
//        putSomething("a",5,"b");
//        System.out.println(exists("a"));
//        System.out.println(getSomething("a"));
//
//        TimeUnit.SECONDS.sleep(5);
//        System.out.println(exists("a"));
//        System.out.println(getSomething("a"));
        flushAll();
        System.out.println(jedis.get("*"));
    }

}
