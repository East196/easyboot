package com.github.east196.core.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author east196
 */
@Data
public class PageVo {

    @ApiModelProperty(value = "页号")
    private int pageNumber;

    @ApiModelProperty(value = "页面大小")
    private int pageSize;

    @ApiModelProperty(value = "排序字段")
    private String sort;

    @ApiModelProperty(value = "排序方式 asc/desc")
    private String order;
}
