package com.heqilin.util.plugin.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.heqilin.util.model.Result;
import com.heqilin.util.JsonUtil;
import com.heqilin.util.PropUtil;
import com.heqilin.util.ResultUtil;
import com.heqilin.util.SystemUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 阿里云短信
 * @author heqilin
 * date 2019/04/22
 */
public class AliyunSmsImpl implements ISms {

    private Properties prop = PropUtil.getProp(SystemUtil.getMyUtilConfigPath());

    @Override
    public Result send(String mobile, Map contentMap, String templateId, String signName) {
        String content = "";
        if(contentMap!=null && !contentMap.isEmpty()){
            content = JsonUtil.instance.toJson(contentMap);
        }
        DefaultProfile profile = DefaultProfile.getProfile("default", prop.getProperty("my.util.plugin.sms.aliyunsms.key")
                ,prop.getProperty("my.util.plugin.sms.aliyunsms.secret"));
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateId);
        request.putQueryParameter("TemplateParam", content);

        try {
            CommonResponse response = client.getCommonResponse(request);
            String result = response.getData();
            return ResultUtil.success(result);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        AliyunSmsImpl aliyunSms = new AliyunSmsImpl();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("code","15061805283");
        Result result = aliyunSms.send("15061805283",linkedHashMap,"SMS_129740077","智题库");
        System.out.println(result);
    }
}
