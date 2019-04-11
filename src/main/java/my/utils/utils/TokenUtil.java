package my.utils.utils;

import my.utils.model.Constants;
import my.utils.model.Token;
import my.utils.model.WebApiJsonMsg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 *
 * @author heqilin
 * date:  2019-01-18 ok
 **/
public class TokenUtil {

    /**
     * 从header获取token实体
     * @param request
     * @return Token
     */
    public static Token getToken(HttpServletRequest request){
        if(request==null)
            return null;
        return new Token()
                .setLoginType(request.getHeader("loginType"))
                .setOpenId(request.getHeader("openId"))
                .setToken(request.getHeader("token"))
                .setUserType(request.getHeader("userType"));
    }

    /**
     * 验证Token
     * @param request
     * @return
     */
    public static WebApiJsonMsg validateToken(HttpServletRequest request, String attributeName,Function<Token,? extends Token> func){
        Token token = getToken(request);
        if(token==null)
            return WebApiJsonMsgUtil.errorWithInvalidAuthorization(null,null);
        if(StringUtil.isEmpty(token.getOpenId())
                || StringUtil.isEmpty(token.getToken()))
            return WebApiJsonMsgUtil.errorWithInvalidAuthorization(null,"token/openId不全");
        Token tokenCache = func.apply(token);
        if(tokenCache==null)
            return WebApiJsonMsgUtil.errorWithInvalidAuthorization(null,"服务器缓存已失效");
        if(!token.getToken().equals(tokenCache.getToken()))
            return WebApiJsonMsgUtil.errorWithInvalidAuthorization(null,"token不一致");
        request.setAttribute(attributeName,tokenCache);
        return WebApiJsonMsgUtil.success("",tokenCache);
    }

    /**
     * 输出未授权提示 response输出
     * @param response
     */
    public static void responseInvalidAuth(HttpServletResponse response){
        String msg = WebApiJsonMsgUtil.errorWithInvalidAuthorization(null,null)
                .setErrorCode("403").toString();
        RequestUtil.responsePrint(response,403,msg);
    }



}
