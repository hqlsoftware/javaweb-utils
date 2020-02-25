package com.heqilin.util;

import com.heqilin.util.model.JwtSubject;
import com.heqilin.util.model.Result;
import com.heqilin.util.model.ResultT;
import com.heqilin.util.model.Token;
import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JwtTokenUtilTest {

    @Test
    public void createToken() {
        UserLoginResponseDTO token = JwtTokenUtil.createToken(()->{
            return new JwtSubject().setExpiresInSeconds(0)
                    .setOpenId("heqilin")
                    .setLoginType("pc");
        },t->{
            UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO();
            userLoginResponseDTO.setAge(33);
            userLoginResponseDTO.setName("heqilin");
            return userLoginResponseDTO;
        });
        System.out.println(token.toString());
    }

    @Test
    public void validateTokenUniversal() {
        Token token = JwtTokenUtil.createToken(()->{
            return new JwtSubject().setLoginType("pc").setOpenId("heqilin").setExpiresInSeconds(60);
        },t->{
            return t;
        });
        Result result = JwtTokenUtil.validateTokenUniversal(token.getToken(),claims -> {
            if(claims==null){
                return ResultUtil.errorWithNoneAuthorization();
            }
            Token tokenOld = JsonUtil.INSTANCE.toBean(claims.getSubject(),Token.class);
            if(tokenOld==null){
                return  ResultUtil.errorWithNoneAuthorization();
            }
            if(tokenOld.getOpenId().equals(token.getOpenId())){
                return ResultUtil.success();
            }
            return ResultUtil.errorWithNoneAuthorization();
        });
        assertTrue(result.isSuccess());
    }

    @Test
    public void validateToken(){
        Token token = JwtTokenUtil.createToken(()->{
            return new JwtSubject().setLoginType("pc").setOpenId("heqilin").setExpiresInSeconds(60);
        },t->{
            return t;
        });
        ResultT<UserLoginResponseDTO> result = JwtTokenUtil.validateToken(new Token().setToken(token.getToken()), t -> {
            UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO();
            userLoginResponseDTO.setName("heqilin");
            userLoginResponseDTO.setAge(40);
            userLoginResponseDTO.setToken(token.getToken());
            return userLoginResponseDTO;
        });
        assertNotNull(token.getToken());
        assertTrue(result.getData().getName().equals("heqilin"));
        assertTrue(result.isSuccess());
    }

    @Data
    @Accessors(chain = true)
    public class UserLoginResponseDTO extends Token{
        private String name;
        private int age;
    }

    @Data
    @Accessors(chain = true)
    public class test1{
        private String name;
        private int age;
    }
}