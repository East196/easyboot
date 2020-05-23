package com.github.east196.rap.captcha;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author east196
 */
@Data
public class Captcha {

    @ApiModelProperty(value = "验证码id")
    private String captchaId;

    @ApiModelProperty(value = "验证码")
    private String code;
}
