package my.utils.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import my.utils.utils.JsonUtil;

/**
 * 接口返回结果
 *
 * @author heqilin
 * date:  2018-12-24 ok
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private boolean success=true;
    private int code=200;
    private String message="操作成功";
    private Object data;

    public String toString() {
        return JsonUtil.instance.toJson(this);
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
}
