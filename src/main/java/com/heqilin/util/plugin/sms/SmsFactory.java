package com.heqilin.util.plugin.sms;

import com.heqilin.util.PropUtil;
import com.heqilin.util.StringUtil;
import com.heqilin.util.SystemUtil;

/**
 *
 * @author heqilin
 * date 2019/04/23
 */
public class SmsFactory {
    public static String smsType = PropUtil.getProp(SystemUtil.getMyUtilConfigPath())
            .getProperty("my.util.smsutil.smsType");

    private SmsFactory(){
        throw new AssertionError();
    }

    public  static ISms create(String smsTypeIfNullReadConfigValue){
        if(StringUtil.isEmpty(smsTypeIfNullReadConfigValue))
            smsTypeIfNullReadConfigValue= smsType;

        switch (smsTypeIfNullReadConfigValue){
            case "juhe":
                return new JuheSmsImpl();
            case "aliyun":
                return  new AliyunSmsImpl();
            default:
                return new JuheSmsImpl();
        }
    }

    public static void main(String[] args)
    {
        System.out.println(smsType);
    }
}
