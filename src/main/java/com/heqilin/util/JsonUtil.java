package com.heqilin.util;

import com.heqilin.util.plugin.json.IJson;
import com.heqilin.util.plugin.json.JsonFactory;

/**
 *
 *
 * @author heqilin
 * date:  2018-12-26 ok
 **/
public class JsonUtil {

    private JsonUtil(){
        throw new AssertionError();
    }

    public static final IJson INSTANCE = getJsonClient(null);

    /**
     * 获取自定义Json客户端实现
     * @param jsonType
     * @return
     */
    public  static  IJson getJsonClient(String jsonType){
        return  JsonFactory.newInstance(jsonType);
    }
}
