package my.utils.plugin.cache;

import my.utils.utils.PropUtil;
import my.utils.utils.StringUtil;
import my.utils.utils.SystemUtil;

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
