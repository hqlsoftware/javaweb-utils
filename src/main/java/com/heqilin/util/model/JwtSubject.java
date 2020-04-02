package com.heqilin.util.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.heqilin.util.plugin.json.JsonUtil;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private long expiresInSeconds;

    public String toString() {
        return JsonUtil.INSTANCE.toJson(this);
    }
}
