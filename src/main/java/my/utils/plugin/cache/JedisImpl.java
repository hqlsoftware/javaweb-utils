package my.utils.plugin.cache;

import my.utils.utils.JsonUtil;
import my.utils.utils.PropUtil;
import my.utils.utils.SystemUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Redis缓存
 *
 * @author heqilin
 * date:  2018-12-24
 **/
public class JedisImpl implements ICache {

    //region 返回客户端

    private static JedisPool pool = null;

    static{
        init();
    }

    private static void init(){
        //加载配置文件
        Properties prop = PropUtil.getProp(SystemUtil.getMyUtilConfigPath());
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(Integer.parseInt(prop.getProperty("my.utils.cacheutil.redis.MaxTotal")));//最大连接数，连接全部用完，进行等待
        poolConfig.setMinIdle(Integer.parseInt(prop.getProperty("my.utils.cacheutil.redis.MinIdle"))); //最小空余数
        poolConfig.setMaxIdle(Integer.parseInt(prop.getProperty("my.utils.cacheutil.redis.MaxIdle"))); //最大空余数
        pool = new JedisPool(poolConfig
                ,prop.getProperty("my.utils.cacheutil.redis.host")
                ,Integer.parseInt(prop.getProperty("my.utils.cacheutil.redis.port"))
                ,10000,prop.getProperty("my.utils.cacheutil.redis.password"));
    }

    public static Jedis getClient(){
        if(pool==null)
            init();
        return pool.getResource();
    }
    /**
     * 返还到连接池
     *
     * @param jedis
     */
    public static void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
    //endregion

    public boolean exists(String key) {
        Jedis client = null;
        try{
            client = getClient();
            return client.exists(key);
        }finally {
            close(client);
        }
    }

    public void addCache(String cacheKey, Object cacheObj) {
        Jedis client = null;
        try{
            client = getClient();
            client.set(cacheKey, JsonUtil.instance.toJson(cacheObj));
        }finally {
            close(client);
        }
    }


    public void addCache(String cacheKey, Object cacheObj, long cacheSeconds) {
        Jedis client = null;
        try{
            client = getClient();
            client.set(cacheKey, JsonUtil.instance.toJson(cacheObj));
            client.expire(cacheKey,(int)cacheSeconds);
        }finally {
            close(client);
        }
    }


    public void removeCache(String cacheKey) {
        Jedis client = null;
        try{
            client = getClient();
            client.del(cacheKey);
        }finally {
            close(client);
        }
    }


    public void removeAllCache() {
        Jedis client = null;
        try{
            client = getClient();
            client.flushAll();
        }finally {
            close(client);
        }
    }

    public <T> T getCache(String cacheKey) {
        Jedis client = null;
        try{
            client = getClient();
            return (T)client.get(cacheKey);
        }finally {
            close(client);
        }
    }

    //region 额外方法

    public void zAdd(String cacheKey,double score, String member){
        Jedis client = null;
        try{
            client = getClient();
            client.zadd(cacheKey,score,member);
        }finally {
            close(client);
        }
    }

    public void zRem(String cacheKey,String member){
        Jedis client = null;
        try{
            client = getClient();
            client.zrem(cacheKey,member);
        }finally {
            close(client);
        }
    }

    public Long zRemrangebyrank(String cacheKey,long min,long max){
        Jedis client = null;
        try{
            client = getClient();
            return client.zremrangeByRank(cacheKey,min,max);
        }finally {
            close(client);
        }
    }

    public long zCard(String cacheKey){
        Jedis client = null;
        try{
            client = getClient();
            return client.zcard(cacheKey);
        }finally {
            close(client);
        }
    }

    //endregion

    public static void main(String[] args) {
        JedisImpl bll = new JedisImpl();
        System.out.println(bll.isConfigurationSuccess());
        List<String> result = new ArrayList<>();
        result.add("a");
        result.add("b");
        bll.addCache("heqilin",result);
        System.out.println(bll.getCache("heqilin").toString());
    }
}
