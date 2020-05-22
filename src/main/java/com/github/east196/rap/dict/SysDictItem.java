package com.github.east196.rap.dict;


import lombok.Data;

import java.util.Date;


@Data
public class SysDictItem{


    /**
     * id
     */
    private String id;

    /**
     * 字典id
     */
    private String dictId;

    /**
     * 字典项文本
     */
    private String itemText;

    /**
     * 字典项值
     */
    private String itemValue;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sortOrder;


    /**
     * 状态（1启用 0不启用）
     */
    private Integer status;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;


}
