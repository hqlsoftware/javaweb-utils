package com.heqilin.util.plugin.captcha;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 *
 * @author heqilin
 * date 2019/04/12
 */

@Data
@Accessors(chain = true)
public class CaptchaParam {
    public String cacheName;
    public String value;
}
