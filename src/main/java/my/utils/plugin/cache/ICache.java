package my.utils.plugin.cache;

import my.utils.utils.WebApiJsonMsgUtil;
import my.utils.model.WebApiJsonMsg;

import java.util.List;
import java.util.function.Supplier;

/**
 * 缓存接口
 *
 * @author heqilin
 * date:  2018-12-24 ok
 **/
public interface ICache {

    /**
     * 测试配置是否确
     **/
    default WebApiJsonMsg isConfigurationSuccess() {
        String cacheKey = "testcacheisok";
        addCache(cacheKey,"ok");
        String cacheObj = getCache(cacheKey);
        if(cacheObj.equals("ok"))
            return WebApiJsonMsgUtil.success(null,cacheObj);
        return WebApiJsonMsgUtil.error(null,null,null);
    }

    /**
     * 缓存是否存在
     **/
    public boolean exists(String key);

    /**
     * 新增缓存
     **/
    void addCache(String cacheKey, Object objObject);

    /**
     * 设置数据缓存
     **/
    void addCache(String cacheKey, Object objObject, long cacheSeconds);

    /**
     * 移除指定数据缓存
     **/
    void removeCache(String cacheKey);

    /**
     * 移除全部缓存
     **/
    void removeAllCache();

    /**
     * 获取数据缓存
     **/
    <T> T getCache(String cacheKey);

    /**
     * 获取数据，如果不存在，取出数据并存放于缓存中
     **/
    default <T> T getCacheIfAbsent(String cacheName, Supplier<T> getOriginResult, long cacheSeconds) {
        T result = getCache(cacheName);
        if(result!=null){
            return result;
        }
        result = getOriginResult.get();
        if(result!=null){
            addCache(cacheName,result,cacheSeconds);
        }
        return result;
    }
}
