package com.heqilin.util;

import com.heqilin.util.model.Result;
import com.heqilin.util.plugin.captcha.Captcha;
import com.heqilin.util.plugin.captcha.CaptchaParam;
import com.heqilin.util.plugin.captcha.GifCaptcha;
import com.heqilin.util.plugin.captcha.SpecCaptcha;
import com.heqilin.util.model.Constants;

import javax.servlet.http.HttpServletResponse;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 验证码
 *
 * @author heqilin
 * date 2019/04/12
 */
public class CaptchaUtil {

    /**
     *  获取动态验证码
     * @param response
     * @param length
     * @param saveCaptcha
     */
    public static void getGifCode(HttpServletResponse response, int length, Consumer<CaptchaParam> saveCaptcha){
        try {
            RequestUtil.setPageNoCache(response);
            response.setContentType("image/gif");
            Captcha captcha = new GifCaptcha(length*30,40,length);
            saveCaptcha.accept(new CaptchaParam()
                    .setCacheName(Constants.CACHE_CAPTCHA_CODE)
                    .setValue(captcha.text().toLowerCase())
            );
            //输出
            captcha.out(response.getOutputStream());
        } catch (Exception e) {
            LogUtil.error("获取验证码异常",e);
        }
    }

    /**
     * 获取静态验证码
     * @param response
     * @param length
     * @param saveCaptcha
     */
    public static void getPngCode(HttpServletResponse response, int length, Consumer<CaptchaParam> saveCaptcha){
        try {
            RequestUtil.setPageNoCache(response);
            response.setContentType("image/png");
            Captcha captcha = new SpecCaptcha(length*30,40,length);
            saveCaptcha.accept(new CaptchaParam()
                    .setCacheName(Constants.CACHE_CAPTCHA_CODE)
                    .setValue(captcha.text().toLowerCase())
            );
            //输出
            captcha.out(response.getOutputStream());
        } catch (Exception e) {
            LogUtil.error("获取验证码异常",e);
        }
    }

    /**
     * 检测验证码
     * @param code
     * @param getCapchaCode
     * @return
     */
    public static Result check(String code, Function<CaptchaParam,String> getCapchaCode) {
        if (StringUtil.isEmpty(code)) {
            return ResultUtil.errorWithInvalidParamer("验证码为空");
        }
        if(getCapchaCode==null){
            return ResultUtil.errorWithOperationFailed("验证码为空");
        }
        String codeTrue = getCapchaCode.apply(new CaptchaParam()
                .setCacheName(Constants.CACHE_CAPTCHA_CODE));

        if(!code.equals(codeTrue)){
            return ResultUtil.errorWithOperationFailed("图形码不正确");
        }
        return  ResultUtil.success();
    }
}
