package com.github.east196.ezsb.core;

import lombok.Data;

/**
 * @author east196
 */
@Data
public class BootException extends RuntimeException {

    private String msg;

    public BootException(String msg){
        super(msg);
        this.msg = msg;
    }

    private int code = 500;

    public BootException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public BootException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BootException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
