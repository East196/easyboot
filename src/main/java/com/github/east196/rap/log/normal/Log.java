package com.github.east196.rap.log.normal;

import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.east196.core.boon.Glue;
import com.github.east196.ezsb.core.BaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author east196
 */
@Data
@Entity
@Table(name = "t_log")
@TableName("t_log")
@Schema(description = "日志")
public class Log extends BaseEntity {

    private static final long serialVersionUID = 1L;

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

    public void setName(String name) {
        this.name = name;
    }
    
    public void setLogType(Integer logType) {
        this.logType = logType;
    }
    
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
    
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
    
    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public void setIpInfo(String ipInfo) {
        this.ipInfo = ipInfo;
    }
    
    public void setCostTime(Integer costTime) {
        this.costTime = costTime;
    }

    /**
     * 转换请求参数为Json
     * @param paramMap
     */
    public void setMapToParams(Map<String, String[]> paramMap) {
        this.requestParam = Glue.mapToString(paramMap);
    }

}
