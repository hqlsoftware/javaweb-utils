package com.heqilin.util;

import com.heqilin.util.model.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author heqilin
 * date:  2019-01-18 ok
 **/
public class TokenUtil {

    private TokenUtil(){
        throw new AssertionError();
    }

    /**
     * 创建并保存Token
     *
     * @param setSubject
     * @param saveTokenToCache
     * @return
     */
    public static <T extends Token> T createToken(Supplier<JwtSubject> setSubject, Function<Token, T> saveTokenToCache) {
        if (setSubject == null || saveTokenToCache == null) {
            return null;
        }
        JwtSubject jwtSubject = setSubject.get();
        Token token = new Token().setExpiresInSeconds(jwtSubject.getExpiresInSeconds())
                .setToken(RandomUtil.createUUID32())
                .setLoginType(jwtSubject.getLoginType())
                .setOpenId(jwtSubject.getOpenId());

        return saveTokenToCache.apply(token);
    }

    /**
     * 从header获取token实体
     *
     * @param request
     * @return Token
     */
    public static Token getToken(HttpServletRequest request) {
        if (request == null)
            return null;
        return new Token()
                .setLoginType(request.getHeader("loginType"))
                .setOpenId(request.getHeader("openId"))
                .setToken(request.getHeader("token"));
    }

    /**
     * 验证Token
     *
     * @param tokenHeader
     * @param getTokenFromCache
     * @return
     */
    public static <T extends Token> ResultT<T> validateToken(Token tokenHeader, Function<Token, T> getTokenFromCache) {
        if (tokenHeader == null)
            return ResultTUtil.errorWithNoneAuthorization();
        if (StringUtil.isEmpty(tokenHeader.getOpenId())
                || StringUtil.isEmpty(tokenHeader.getToken()))
            return ResultTUtil.errorWithNoneAuthorization("token/openId不全");
        Token tokenCache = getTokenFromCache.apply(tokenHeader);
        if (tokenCache == null)
            return ResultTUtil.errorWithNoneAuthorization("服务器缓存已失效");
        if (!tokenHeader.getToken().equals(tokenCache.getToken()))
            return ResultTUtil.errorWithNoneAuthorization("token已失效");
        return ResultTUtil.success(tokenCache);
    }

    /**
     * 未授权，无法访问 response输出
     *
     * @param response
     */
    public static void responseNoneAuthorization(HttpServletResponse response) {
        RequestUtil.responsePrint(response, ResultEnum.ErrorWithNoneAuthorization.getCode(), ResultEnum.ErrorWithNoneAuthorization.getMessage());
    }

    /**
     * 权限不足，无法访问 response输出
     *
     * @param response
     */
    public static void responseAuthorizationDenied(HttpServletResponse response) {
        RequestUtil.responsePrint(response, ResultEnum.ErrorWithAuthorizationDenied.getCode(), ResultEnum.ErrorWithAuthorizationDenied.getMessage());
    }


}
