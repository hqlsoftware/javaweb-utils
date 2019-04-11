package my.utils.model;

import lombok.Data;
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
public class WebApiJsonMsg {

    private boolean success;
    private String errorCode;
    private String message;
    private Object data;

    public String toString() {
        return JsonUtil.instance.toJson(this);
    }
}
