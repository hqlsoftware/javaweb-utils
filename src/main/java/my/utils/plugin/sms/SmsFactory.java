package my.utils.plugin.sms;

import my.utils.plugin.json.JackonImpl;
import my.utils.utils.PropUtil;
import my.utils.utils.StringUtil;
import my.utils.utils.SystemUtil;

/**
 *
 * @author heqilin
 * date 2019/04/23
 */
public class SmsFactory {
    public static String smsType = PropUtil.getProp(SystemUtil.getMyUtilConfigPath())
            .getProperty("my.utils.smsutil.smsType");

    private SmsFactory(){
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
