package my.utils.plugin.sms;

import my.utils.model.WebApiJsonMsg;
import my.utils.utils.*;

import java.util.HashMap;
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
    public WebApiJsonMsg send(String mobile, String content, String templateId) {
        String url = "http://v.juhe.cn/sms/send";
        Map<String,String> map = new HashMap<>();
        map.put("key",prop.getProperty("my.utils.plugin.sms.juhesms.key"));
        map.put("mobile",mobile);
        map.put("dtype","json");
        map.put("tpl_id",templateId);
        map.put("tpl_value",content);
        Map<String,Object> obj = JsonUtil.instance.toBean(HttpUtil.get(url,map,null),HashMap.class);
        if(Integer.parseInt(obj.get("error_code").toString())==0){
            return WebApiJsonMsgUtil.success("发送成功",null);
        }

        return WebApiJsonMsgUtil.errorWithOperationFailed("发送失败:"+obj.get("reason"),obj);
    }

    public static void main(String[] args) {
        JuheSmsImpl juheSms = new JuheSmsImpl();
        WebApiJsonMsg webApiJsonMsg= juheSms.send("15061805283", "#Mobile#=15061805283&#SubjectName#=test","151074");
        System.out.println(webApiJsonMsg);
    }
}
