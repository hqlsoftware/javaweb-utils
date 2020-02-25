package com.heqilin.util;

import com.heqilin.util.model.Result;
import com.heqilin.util.model.ResultEnum;
import com.heqilin.util.model.Token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
                .setToken(request.getHeader("token"));
    }

    /**
     * 验证Token
     * @param request
     * @return
     */
    public static Result validateToken(HttpServletRequest request, String attributeName, Function<Token,? extends Token> getTokenFromCache){
        Token token = getToken(request);
        if(token==null)
            return ResultUtil.errorWithNoneAuthorization();
        if(StringUtil.isEmpty(token.getOpenId())
                || StringUtil.isEmpty(token.getToken()))
            return ResultUtil.errorWithNoneAuthorization("token/openId不全");
        Token tokenCache = getTokenFromCache.apply(token);
        if(tokenCache==null)
            return ResultUtil.errorWithNoneAuthorization("服务器缓存已失效");
        if(!token.getToken().equals(tokenCache.getToken()))
            return ResultUtil.errorWithNoneAuthorization("token不一致");
        request.setAttribute(attributeName,tokenCache);
        return ResultUtil.success(tokenCache);
    }

    /**
     * 未授权，无法访问 response输出
     * @param response
     */
    public static void responseNoneAuthorization(HttpServletResponse response){
        RequestUtil.responsePrint(response, ResultEnum.ErrorWithNoneAuthorization.getCode(),ResultEnum.ErrorWithNoneAuthorization.getMessage());
    }

    /**
     * 权限不足，无法访问 response输出
     * @param response
     */
    public static void responseAuthorizationDenied(HttpServletResponse response){
        RequestUtil.responsePrint(response, ResultEnum.ErrorWithAuthorizationDenied.getCode(),ResultEnum.ErrorWithAuthorizationDenied.getMessage());
    }



}
