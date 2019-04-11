package my.utils.model;

import lombok.Data;
import lombok.experimental.Accessors;
import my.utils.utils.JsonUtil;

@Data
@Accessors(chain = true)
/**
 * Token认证实体
 *
 * @author heqilin
 * date:  2018-12-24 ok
 */
public class Token {
    private String openId;
    private String token;
    private String userType;
    private String loginType;

    public String toString() {
        return JsonUtil.instance.toJson(this);
    }
}
