package my.utils.utils;

import my.utils.model.Constants;
import my.utils.model.WebApiJsonMsg;
import my.utils.plugin.captcha.Captcha;
import my.utils.plugin.captcha.CaptchaParam;
import my.utils.plugin.captcha.GifCaptcha;
import my.utils.plugin.captcha.SpecCaptcha;

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
    public static WebApiJsonMsg check(String code, Function<CaptchaParam,String> getCapchaCode) {
        if (StringUtil.isEmpty(code)) {
            return WebApiJsonMsgUtil.errorWithOperationFailed("验证码为空", code);
        }
        if(getCapchaCode==null){
            return WebApiJsonMsgUtil.errorWithOperationFailed("验证码为空", code);
        }
        String codeTrue = getCapchaCode.apply(new CaptchaParam()
                .setCacheName(Constants.CACHE_CAPTCHA_CODE));

        if(!code.equals(codeTrue)){
            return WebApiJsonMsgUtil.errorWithOperationFailed("图形码不正确", code);
        }
        return  WebApiJsonMsgUtil.success("验证成功", code);
    }
}
