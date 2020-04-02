package com.heqilin.util.plugin.sms;

import com.heqilin.util.model.Result;

import java.util.Map;

/**
 * 短信接口 -ok
 * @author heqilin
 * date 2019/04/18
 */
public interface ISms {
    /**
     * 是否配置正确
     * @param mobile
     * @param templateId
     * @param signName
     * @return
     */
    Result isConfigurationSuccess(String mobile,String templateId, String signName);

    /**
     * 发送短信
     * @param mobile 手机号码
     * @param content 内容
     * @param templateId 模板ID
     * @param signName 签名
     * @return
     */
    Result send(String mobile, Map content, String templateId, String signName);
}
