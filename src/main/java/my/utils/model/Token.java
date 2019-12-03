package my.utils.model;

import lombok.Data;
import lombok.experimental.Accessors;
import my.utils.utils.JsonUtil;

/**
 * Token认证实体
 *
 * @author heqilin
 * date:  2018-12-24 ok
 */
@Data
@Accessors(chain = true)
public class Token {
    private String openId;
    private String token;
    private String loginType;

    public String toString() {
        return JsonUtil.instance.toJson(this);
    }
}
