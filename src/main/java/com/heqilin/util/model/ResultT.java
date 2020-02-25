package com.heqilin.util.model;

import com.heqilin.util.JsonUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 接口返回结果
 *
 * @author heqilin
 * date:  2018-12-24 ok
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ResultT<T> {

    private int code=200;
    private String message="操作成功";
    protected T data;

    public String toString() {
        return JsonUtil.instance.toJson(this);
    }

    public ResultT(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultT(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ResultT(String message) {
        this.message = message;
    }

    public ResultT(T data) {
        this.data = data;
    }

    public boolean isSuccess(){
        return this.code==200;
    }

    public Result toResult(){
        return new Result().setCode(this.getCode())
                .setMessage(this.getMessage())
                .setData(this.getData());
    }
}
