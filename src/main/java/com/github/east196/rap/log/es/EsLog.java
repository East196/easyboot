package com.github.east196.rap.log.es;


import java.util.Date;
import java.util.Map;

import jakarta.persistence.Id;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.east196.core.boon.Glue;
import com.github.east196.core.util.SnowFlakeUtil;
import com.github.east196.ezsb.core.CommonConstant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * Elasticsearch文档实体类
 * @author east196
 */
@Data
@Document(indexName = "log", type = "log", shards = 1, replicas = 0, refreshInterval = "-1")
public class EsLog {

    private static final long serialVersionUID = 1L;

    @Id
    @Schema(description = "唯一标识")
    private String id = String.valueOf(SnowFlakeUtil.getFlowIdInstance().nextId());

    @Schema(description = "创建者")
    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    @Field(type = FieldType.Date, index = false, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime = new Date();

    @Schema(description = "时间戳 查询时间范围时使用")
    private Long timeMillis = System.currentTimeMillis();

    @Schema(description = "更新者")
    private String updateBy;

    @Schema(description = "删除标志 默认0")
    private Integer delFlag = CommonConstant.STATUS_NORMAL;

    @Schema(description = "方法操作名称")
    private String name;

    @Schema(description = "日志类型 0登陆日志 1操作日志")
    private Integer logType;

    @Schema(description = "请求路径")
    private String requestUrl;

    @Schema(description = "请求类型")
    private String requestType;

    @Schema(description = "请求参数")
    private String requestParam;

    @Schema(description = "请求用户")
    private String username;

    @Schema(description = "ip")
    private String ip;

    @Schema(description = "ip信息")
    private String ipInfo;

    @Schema(description = "花费时间")
    private Integer costTime;

    /**
     * 转换请求参数为Json
     * @param paramMap
     */
    public void setMapToParams(Map<String, String[]> paramMap) {
        this.requestParam = Glue.mapToString(paramMap);
    }
}
