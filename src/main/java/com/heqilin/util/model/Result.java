package com.heqilin.util.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.heqilin.util.JsonUtil;

/**
 * 接口返回结果
 *
 * @author heqilin
 * date:  2018-12-24 ok
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Result {

    private int code=200;
    private String message="操作成功";
    protected Object data;

    public String toString() {
        return JsonUtil.INSTANCE.toJson(this);
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public Result(String message) {
        this.message = message;
    }

    public Result(Object data) {
        this.data = data;
    }

    public boolean isSuccess(){
        return this.code==200;
    }
}
