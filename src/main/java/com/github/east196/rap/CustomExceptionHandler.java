package com.github.east196.rap;

import com.github.east196.core.api.Result;
import com.github.east196.core.util.Validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 自定义异常处理器
 *
 * @Author east196
 * @Date 2019
 */
@RestControllerAdvice({"com.github.east196"})
public class CustomExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidException(MethodArgumentNotValidException e){
        log.debug("exception:{}", e);
        Result<Object> result = new Result<>();
        //获取信息 default message [手机号码格式不对]
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        String defaultMessage = allErrors.get(0).getDefaultMessage();
        //根据defaultMessage 返回相应状态码
        result.setMessage(defaultMessage);
        switch (defaultMessage) {
            //传入参数异常
            case Validators.MSG_MOBILE: result.setCode(4501);break;
            case Validators.MSG_PASSWORD: result.setCode(4502);break;
            case Validators.MSG_PASSWORD_P: result.setCode(4503);break;
            case Validators.MSG_CH_EN_NUM: result.setCode(4504);break;
            case Validators.MSG_USERNAME_C: result.setCode(4505);break;
            case Validators.MSG_USERNAME_E: result.setCode(4506);break;
            case Validators.MSG_USERNAME_C_E: result.setCode(4507);break;
            case Validators.MSG_FIRM_NAME: result.setCode(4521);break;
            case Validators.MSG_LINKMAN: result.setCode(4522);break;
            case Validators.MSG_POSITION: result.setCode(4523);break;
            case Validators.MSG_REGEX_PROJECT_NAME: result.setCode(4524);break;
            case Validators.MSG_REGEX_ADDRESS: result.setCode(4525);break;
            default: result.setCode(4000);break;
        }
        return result;
    }
}
