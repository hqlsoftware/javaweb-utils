package my.utils.plugin.sms;

import my.utils.model.WebApiJsonMsg;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 短信接口 -ok
 * @author heqilin
 * date 2019/04/18
 */
public interface ISms {
    WebApiJsonMsg send(String mobile, Map content, String templateId, String signName);
}
