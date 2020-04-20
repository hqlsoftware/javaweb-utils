package com.heqilin.util.plugin.storage;

import com.heqilin.util.core.PropUtil;
import com.heqilin.util.core.StringUtil;
import com.heqilin.util.core.SystemUtil;

/**
 * @author heqilin
 * date 2020/04/01
 */
public class StorageFactory {
    public static final String STORAGE_TYPE = PropUtil.getProp(SystemUtil.getMyUtilConfigPath())
            .getProperty("my.util.plugin.storage.storageType");

    private StorageFactory(){
        throw new AssertionError();
    }

    public  static IStorage newInstance(String storageTypeIfNullReadConfigValue){
        if(StringUtil.isEmpty(storageTypeIfNullReadConfigValue))
            storageTypeIfNullReadConfigValue= STORAGE_TYPE;

        switch (storageTypeIfNullReadConfigValue){
            case "local":
                return new LocalStorageImpl();
            case "aliyunoss":
                return new AliyunStorageImpl();
            default:
                return null;
        }
    }
}
