package com.heqilin.util.model;

import com.heqilin.util.JsonUtil;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Token认证实体
 *
 * @author heqilin
 * date:  2018-12-24 ok
 */
@Data
@Accessors(chain = true)
public class JwtSubject {
    private String openId;
    private String loginType;

    public String toString() {
        return JsonUtil.instance.toJson(this);
    }
}
