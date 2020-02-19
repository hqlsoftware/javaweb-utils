package com.heqilin.util.plugin.cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * EhCache
 *
 * @author heqilin
 * date:  2019/03/04
 * TODO
 */
public class EhCacheImpl implements ICache {

    //region 返回客户端
    private static Cache<String, Object> client = null;

    static {
        //String url = SystemUtil.getMyUtilConfigPath_EhCache();
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Object.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
//                                        .heap(10, EntryUnit.ENTRIES)
                                        .offheap(100, MemoryUnit.MB)
//                                        .disk(20, MemoryUnit.MB, true)
                        ))
                .build();
        cacheManager.init();
        client = cacheManager.getCache("preConfigured", String.class, Object.class);
    }
    //endregion

    @Override
    public boolean exists(String key) {
        return client.containsKey(key);
    }

    @Override
    public void addCache(String cacheKey, Object objObject) {
        client.putIfAbsent(cacheKey, objObject);
    }

    @Override
    public void addCache(String cacheKey, Object objObject, long cacheSeconds) {
        client.putIfAbsent(cacheKey, objObject);
    }

    @Override
    public void removeCache(String cacheKey) {
        client.remove(cacheKey);
    }

    @Override
    public void removeAllCache() {

    }

    @Override
    public <T> T getCache(String cacheKey) {
        return (T) client.get(cacheKey);
    }


    public static void main(String[] args) {
        EhCacheImpl bll = new EhCacheImpl();
        System.out.println(bll.isConfigurationSuccess());
        List<String> result = new ArrayList<>();
        result.add("a");
        result.add("b");
        bll.addCache("heqilin", result);
        System.out.println(bll.getCache("heqilin").toString());
    }
}
