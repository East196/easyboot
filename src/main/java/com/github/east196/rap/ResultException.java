package com.github.east196.rap;

import lombok.Data;

/**
 * 自定义异常类
 *
 * @Author east196
 * @Date 2019
 */

@Data
public class ResultException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    public ResultException(){
        super();
    }

    public ResultException(Integer code){
        super();
        this.code = code;
    }
    public ResultException(String message){
        super(message);
        this.msg = message;
    }

    public ResultException(Integer code, String message){
        super(message);
        this.code = code;
        this.msg = message;
    }


}
