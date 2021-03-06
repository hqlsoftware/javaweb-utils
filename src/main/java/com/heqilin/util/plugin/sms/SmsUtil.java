package com.heqilin.util.plugin.sms;

import com.heqilin.util.plugin.sms.ISms;
import com.heqilin.util.plugin.sms.SmsFactory;

/**
 *
 * @author heqilin
 * date 2019/04/23
 */
public class SmsUtil {

    private SmsUtil(){
        throw new AssertionError();
    }

    public static final ISms INSTANCE = getCacheClient(null);

    /**
     * 获取自定义Sms客户端实现
     * @param smsType
     * @return
     */
    public  static  ISms getCacheClient(String smsType){
        return  SmsFactory.newInstance(smsType);
    }
}
