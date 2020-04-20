package com.heqilin.util.plugin.json;

import com.heqilin.util.core.PropUtil;
import com.heqilin.util.core.StringUtil;
import com.heqilin.util.core.SystemUtil;

/**
 *
 *
 * @author heqilin
 * date:  2019-01-15 ok
 **/
public class JsonFactory {
    public static final String JSON_TYPE = PropUtil.getProp(SystemUtil.getMyUtilConfigPath())
            .getProperty("my.util.jsonutil.jsonType");

    private JsonFactory(){
        throw new AssertionError();
    }

    public  static IJson newInstance(String jsonTypeIfNullReadConfigValue){
        if(StringUtil.isEmpty(jsonTypeIfNullReadConfigValue))
            jsonTypeIfNullReadConfigValue= JSON_TYPE;

        switch (jsonTypeIfNullReadConfigValue){
            case "default":
                return new JackonImpl();
            case "jackon":
                return new JackonImpl();
            default:
                return null;
        }
    }

    public static void main(String[] args)
    {
        System.out.println(JSON_TYPE);
    }
}
