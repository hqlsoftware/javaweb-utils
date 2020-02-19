package com.heqilin.util.plugin.cache;

import com.heqilin.util.PropUtil;
import com.heqilin.util.StringUtil;
import com.heqilin.util.SystemUtil;

/**
 * Cache Factory
 *
 * @author heqilin
 * date:  2019-01-15 ok
 **/
public class CacheFactory {
    public static String cacheType = PropUtil.getProp(SystemUtil.getMyUtilConfigPath())
            .getProperty("my.utils.cacheutil.cacheType");

    private CacheFactory(){
        throw new AssertionError();
    }

    public  static ICache create(String cacheTypeIfNullReadConfigValue){
        if(StringUtil.isEmpty(cacheTypeIfNullReadConfigValue))
            cacheTypeIfNullReadConfigValue= cacheType;

        switch (cacheTypeIfNullReadConfigValue){
            case "default":
                return new JedisImpl();
            case "redis":
                return new JedisImpl();
            default:
                return new MemcachedImpl();
        }
    }

    public static void main(String[] args)
    {
        System.out.println(cacheType);
    }
}
