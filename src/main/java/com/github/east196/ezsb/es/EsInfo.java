package com.github.east196.ezsb.es;

import lombok.Data;

/**
 * @author east196
 */
@Data
public class EsInfo {

    private String cluster_name;

    private String status;

    private String number_of_nodes;

    private Integer count;
}
