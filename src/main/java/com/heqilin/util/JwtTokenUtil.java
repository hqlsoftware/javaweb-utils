package com.heqilin.util;

import com.heqilin.util.model.JwtSubject;
import com.heqilin.util.model.Result;
import com.heqilin.util.model.ResultT;
import com.heqilin.util.model.Token;
import com.heqilin.util.plugin.json.JsonUtil;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author heqilin
 * @date 2020/02/24
 */
public class JwtTokenUtil {

    private JwtTokenUtil(){
        throw new AssertionError();
    }

    private static String SECRET = PropUtil.getProp(SystemUtil.getMyUtilConfigPath())
            .getProperty("my.util.jwttokenutil.secret");

    static {
        if (StringUtil.isEmpty(SECRET)) {
            SECRET = "heqilin2020default";
        }
    }

    //region 通用方法

    /**
     * 用户登录成功后生成Jwt Token
     * 通用的
     * 使用Hs256算法  私匙使用配置文件中的密码
     *
     * @param expiresInSeconds jwt过期时间
     * @param setSubject       创建签发人(JWT的主体) 这个是一个json格式的字符串
     * @param setPriviteClaims 创建payload的私有声明(可为空)
     * @return
     */
    public static String createTokenUniversal(long expiresInSeconds, Supplier<String> setSubject, Supplier<Map<String, Object>> setPriviteClaims) {
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<>();
        if (setPriviteClaims != null) {
            claims = setPriviteClaims.get();
        }

        //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        String key = SECRET;

        //生成签发人
        String subject = setSubject.get();

        //下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder();
        if (claims != null && claims.size() > 0) {
            builder
                    //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                    .setClaims(claims);
        }
        builder
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(RandomUtil.createUUID())
                //iat: jwt的签发时间
                .setIssuedAt(now)
                //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(subject)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);

        if (expiresInSeconds > 0) {
            long expMillis = nowMillis + expiresInSeconds * 1000;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * Token的解密
     *
     * @param token 加密后的token
     * @return
     */
    private static ResultT<Claims> parseJwtToken(String token) {
        try {
            //签名秘钥，和生成的签名的秘钥一模一样
            String key = SECRET;
            //得到DefaultJwtParser
            Claims claims = Jwts.parser()
                    //设置签名的秘钥
                    .setSigningKey(key)
                    //设置需要解析的jwt
                    .parseClaimsJws(token).getBody();
            return ResultTUtil.success(claims);
        } catch (ExpiredJwtException ex) {
            return ResultTUtil.errorWithNoneAuthorization("JwtToken已过期");
        } catch (Exception ex) {
            LogUtil.error("解析JwtToken出现异常：{0}", ex.getMessage());
            return ResultTUtil.errorWithNoneAuthorization(ex.getMessage());
        }
    }

    /**
     * 校验token
     * 通用的
     *
     * @param token
     * @param checkTokenRule
     * @return
     */
    public static Result validateTokenUniversal(String token, Function<Claims, Result> checkTokenRule) {
        //得到DefaultJwtParser
        ResultT<Claims> parseJwtTokenResult = parseJwtToken(token);
        if (!parseJwtTokenResult.isSuccess()) {
            return parseJwtTokenResult.asResult();
        }
        return checkTokenRule.apply(parseJwtTokenResult.getData());
    }

    //endregion

    // region Token调用三步骤

    /**
     * 用户登录成功后生成Jwt Token
     * 使用Hs256算法  私匙使用配置文件中的密码
     *
     * @param setSubject 创建签发人(JWT的主体) 这个是一个json格式的字符串
     * @return
     */
    public static <T extends Token> T createToken(Supplier<JwtSubject> setSubject, Function<Token, T> saveTokenToCache) {
        if (setSubject == null || saveTokenToCache == null) {
            return null;
        }
        JwtSubject jwtSubject = setSubject.get();
        String tokenStr = createTokenUniversal(jwtSubject.getExpiresInSeconds(), () -> {
            if (setSubject == null) {
                return null;
            } else {
                return setSubject.get().toString();
            }
        }, null);
        if (StringUtil.isEmpty(tokenStr)) {
            return null;
        }
        Token token = new Token().setToken(tokenStr)
                .setLoginType(jwtSubject.getLoginType())
                .setOpenId(jwtSubject.getOpenId())
                .setExpiresInSeconds(jwtSubject.getExpiresInSeconds());

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
        ResultT<Claims> parseJwtTokenResult = parseJwtToken(tokenHeader.getToken());
        if (!parseJwtTokenResult.isSuccess()) {
            return ResultTUtil.errorWithNoneAuthorization(parseJwtTokenResult.getMessage());
        }
        Claims claims = parseJwtTokenResult.getData();
        Token token = JsonUtil.INSTANCE.toBean(claims.getSubject(), Token.class);
        if (token == null) {
            return ResultTUtil.errorWithNoneAuthorization("token格式不正确-subject格式不正确");
        }
        if (StringUtil.isEmpty(token.getOpenId()))
            return ResultTUtil.errorWithNoneAuthorization("token格式不正确-subject信息不正确");
        Token tokenCache = getTokenFromCache.apply(token);
        if (tokenCache == null)
            return ResultTUtil.errorWithNoneAuthorization("服务器缓存已失效");
        if (!tokenHeader.getToken().equals(tokenCache.getToken()))
            return ResultTUtil.errorWithNoneAuthorization("token已失效");
        return ResultTUtil.success(tokenCache);
    }

    //endregion

}
