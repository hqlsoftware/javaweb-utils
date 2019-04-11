package my.utils.plugin.json;

import my.utils.utils.PropUtil;
import my.utils.utils.StringUtil;
import my.utils.utils.SystemUtil;

/**
 *
 *
 * @author heqilin
 * date:  2019-01-15 ok
 **/
public class JsonFactory {
    public static String jsonType = PropUtil.getProp(SystemUtil.getMyUtilConfigPath())
            .getProperty("my.utils.jsonutil.jsonType");

    private JsonFactory(){
    }

    public  static IJson create(String jsonTypeIfNullReadConfigValue){
        if(StringUtil.isEmpty(jsonTypeIfNullReadConfigValue))
            jsonTypeIfNullReadConfigValue= jsonType;

        switch (jsonTypeIfNullReadConfigValue){
            case "default":
                return new JackonImpl();
            default:
                return new JackonImpl();
        }
    }

    public static void main(String[] args)
    {
        System.out.println(jsonType);
    }
}
