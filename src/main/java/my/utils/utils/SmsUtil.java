package my.utils.utils;

import my.utils.plugin.sms.ISms;
import my.utils.plugin.sms.SmsFactory;

/**
 *
 * @author heqilin
 * date 2019/04/23
 */
public class SmsUtil {
    public static ISms instance = SmsFactory.create(null);

    /**
     * 获取自定义Sms客户端实现
     * @param smsType
     * @return
     */
    public  static  ISms getCacheClient(String smsType){
        return  SmsFactory.create(smsType);
    }
}
