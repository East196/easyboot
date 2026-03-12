package com.github.east196.core.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author east196
 */
@Data
public class PageVo {

    @Schema(description = "页号")
    private int pageNumber;

    @Schema(description = "页面大小")
    private int pageSize;

    @Schema(description = "排序字段")
    private String sort;

    @Schema(description = "排序方式 asc/desc")
    private String order;
}
