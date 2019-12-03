package my.utils.plugin.sms;

import my.utils.model.Result;

import java.util.Map;

/**
 * 短信接口 -ok
 * @author heqilin
 * date 2019/04/18
 */
public interface ISms {
    Result send(String mobile, Map content, String templateId, String signName);
}
