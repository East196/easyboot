package com.github.east196.rap.ip;

import lombok.Data;

/**
 * @author east196
 */
@Data
public class IpInfo {

    private String url;

    private String p;
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setP(String p) {
        this.p = p;
    }
}
