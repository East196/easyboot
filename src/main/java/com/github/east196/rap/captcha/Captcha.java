package com.github.east196.rap.captcha;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author east196
 */
@Data
public class Captcha {

    @Schema(description = "验证码id")
    private String captchaId;

    @Schema(description = "验证码")
    private String code;
    
    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }
}
