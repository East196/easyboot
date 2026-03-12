package com.github.east196.rap.ip;

import lombok.Data;

/**
 * @author east196
 */
@Data
public class IpLocate {

    private String retCode;

    private City result;
    
    public String getRetCode() {
        return retCode;
    }
    
    public City getResult() {
        return result;
    }
}

