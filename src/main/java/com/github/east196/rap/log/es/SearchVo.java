package com.github.east196.rap.log.es;

import lombok.Data;

/**
 * @author east196
 */
@Data
public class SearchVo  {

    private String startDate;

    private String endDate;
    
    public String getStartDate() {
        return startDate;
    }
    
    public String getEndDate() {
        return endDate;
    }
}
