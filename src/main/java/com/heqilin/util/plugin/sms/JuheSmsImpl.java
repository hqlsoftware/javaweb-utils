package com.heqilin.util.plugin.sms;

import com.heqilin.util.model.Result;
import com.heqilin.util.*;
import com.heqilin.util.plugin.json.JsonUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 聚合短信接口
 * @author heqilin
 * date 2019/04/18
 */
public class JuheSmsImpl implements ISms {

    private Properties prop = PropUtil.getProp(SystemUtil.getMyUtilConfigPath());

    @Override
    public Result isConfigurationSuccess(String mobile, String templateId, String signName) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("Code","1234");
        return send("15061805283", linkedHashMap,"151074",null);
    }

    @Override
    public Result send(String mobile, Map contentMap, String templateId, String signName) {
        String content = "";
        if(contentMap!=null && !contentMap.isEmpty()){
            content = RequestUtil.getQueryStringFromMap(contentMap, x->"#"+x+"#");
        }
        String url = "http://v.juhe.cn/sms/send";
        Map<String,String> map = new HashMap<>();
        map.put("key",prop.getProperty("my.util.plugin.sms.juhesms.key"));
        map.put("mobile",mobile);
        map.put("dtype","json");
        map.put("tpl_id",templateId);
        map.put("tpl_value",content);
        Map<String,Object> obj = JsonUtil.INSTANCE.toBean(HttpUtil.get(url,map,null),HashMap.class);
        if(Integer.parseInt(obj.get("error_code").toString())==0){
            return ResultUtil.success();
        }

        return ResultUtil.errorWithOperationFailed("发送失败:"+obj.get("reason"));
    }
}
