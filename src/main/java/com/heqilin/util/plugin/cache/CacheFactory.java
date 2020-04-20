package com.heqilin.util.plugin.cache;

import com.heqilin.util.core.PropUtil;
import com.heqilin.util.core.StringUtil;
import com.heqilin.util.core.SystemUtil;

/**
 * Cache Factory
 *
 * @author heqilin
 * date:  2019-01-15 ok
 **/
public class CacheFactory {
    public static final String CACHE_TYPE = PropUtil.getProp(SystemUtil.getMyUtilConfigPath())
            .getProperty("my.util.cacheutil.cacheType");

    private CacheFactory() {
        throw new AssertionError();
    }

    public static ICache newInstance(String cacheTypeIfNullReadConfigValue) {
        if (StringUtil.isEmpty(cacheTypeIfNullReadConfigValue))
            cacheTypeIfNullReadConfigValue = CACHE_TYPE;

        switch (cacheTypeIfNullReadConfigValue) {
            case "default":
                return new EhCacheImpl();
            case "redis":
                return new JedisImpl();
            case "memcached":
                return new MemcachedImpl();
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(CACHE_TYPE);
    }
}
