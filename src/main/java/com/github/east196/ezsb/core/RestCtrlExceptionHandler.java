package com.github.east196.ezsb.core;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.east196.core.api.Result;
import com.github.east196.core.api.ResultUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author east196
 */
@RestControllerAdvice
public class RestCtrlExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(RestCtrlExceptionHandler.class);

    @ExceptionHandler(BootException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Result<Object> handleXCloudException(BootException e) {

        String errorMsg="Xboot exception";
        if (e!=null){
            errorMsg=e.getMsg();
            log.error(e.toString());
        }
        return new ResultUtil<>().setErrorMsg(500, errorMsg);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Result<Object> handleException(Exception e) {

        String errorMsg="Exception";
        if (e!=null){
            errorMsg=e.getMessage();
            log.error(e.toString());
        }
        return new ResultUtil<>().setErrorMsg(500, errorMsg);
    }
}
