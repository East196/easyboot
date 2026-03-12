package com.github.east196.rap.ip;

import lombok.Data;

/**
 * @author east196
 */
@Data
public class City{

    private String country;

    private String province;

    private String city;
    
    public String getCountry() {
        return country;
    }
    
    public String getProvince() {
        return province;
    }
    
    public String getCity() {
        return city;
    }
}
