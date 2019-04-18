package my.utils.plugin.sms;

import my.utils.model.WebApiJsonMsg;

/**
 * 短信接口 -ok
 * @author heqilin
 * date 2019/04/18
 */
public interface ISms {
    WebApiJsonMsg send(String mobile,String content,String templateId);
}
