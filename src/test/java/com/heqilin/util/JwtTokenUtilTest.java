package com.heqilin.util;

import com.heqilin.util.model.JwtSubject;
import com.heqilin.util.model.Result;
import com.heqilin.util.model.Token;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtilTest {

    @Test
    public void createToken() {
        String token = JwtTokenUtil.createToken(150000, () -> {
            return new JwtSubject()
                    .setOpenId("13570")
                    .setLoginType("pc");
        });
        System.out.println(token);
    }

    @Test
    public void validateToken() {
        String tokenStr = JwtTokenUtil.createToken(150000, () -> {
            return new JwtSubject()
                    .setOpenId("13570")
                    .setLoginType("pc");
        });
        Result result = JwtTokenUtil.validateTokenUniversal(tokenStr+"1"
                , claims -> {
                    Token token = JsonUtil.instance.toBean(claims.getSubject(),Token.class);
                    if(token==null){
                        return ResultUtil.errorWithNoneAuthorization();
                    }
                    if(token.getOpenId().equals("13570")){
                        return ResultUtil.success();
                    }else{
                        return ResultUtil.errorWithNoneAuthorization();
                    }
                });
        System.out.println(result.toString());
    }
}