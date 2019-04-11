package my.utils.utils;

import my.utils.plugin.json.IJson;
import my.utils.plugin.json.JsonFactory;

/**
 *
 *
 * @author heqilin
 * date:  2018-12-26 ok
 **/
public class JsonUtil {
    public static IJson instance = JsonFactory.create(null);

    /**
     * 获取自定义Json客户端实现
     * @param jsonType
     * @return
     */
    public  static  IJson getCacheClient(String jsonType){
        return  JsonFactory.create(jsonType);
    }
}
