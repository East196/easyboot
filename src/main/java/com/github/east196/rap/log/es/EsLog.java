package com.github.east196.rap.log.es;

import java.util.Date;
import java.util.Map;

import com.github.east196.core.boon.Glue;
import com.github.east196.core.util.SnowFlakeUtil;
import com.github.east196.ezsb.core.CommonConstant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Elasticsearch文档实体类
 * @author east196
 * TODO: Spring Boot 3 中 Elasticsearch 需要新版本客户端
 */
public class EsLog {

    private static final long serialVersionUID = 1L;

    private String id = String.valueOf(SnowFlakeUtil.getFlowIdInstance().nextId());

    private String createBy;

    private Date createTime = new Date();

    private Long timeMillis = System.currentTimeMillis();

    private String updateBy;

    private Integer delFlag = CommonConstant.STATUS_NORMAL;

    private String name;

    private Integer logType;

    private String requestUrl;

    private String requestType;

    private String requestParam;

    private String username;

    private String ip;

    private String ipInfo;

    private Integer costTime;
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getCreateBy() { return createBy; }
    public void setCreateBy(String createBy) { this.createBy = createBy; }
    
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    
    public Long getTimeMillis() { return timeMillis; }
    public void setTimeMillis(Long timeMillis) { this.timeMillis = timeMillis; }
    
    public String getUpdateBy() { return updateBy; }
    public void setUpdateBy(String updateBy) { this.updateBy = updateBy; }
    
    public Integer getDelFlag() { return delFlag; }
    public void setDelFlag(Integer delFlag) { this.delFlag = delFlag; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Integer getLogType() { return logType; }
    public void setLogType(Integer logType) { this.logType = logType; }
    
    public String getRequestUrl() { return requestUrl; }
    public void setRequestUrl(String requestUrl) { this.requestUrl = requestUrl; }
    
    public String getRequestType() { return requestType; }
    public void setRequestType(String requestType) { this.requestType = requestType; }
    
    public String getRequestParam() { return requestParam; }
    public void setRequestParam(String requestParam) { this.requestParam = requestParam; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    
    public String getIpInfo() { return ipInfo; }
    public void setIpInfo(String ipInfo) { this.ipInfo = ipInfo; }
    
    public Integer getCostTime() { return costTime; }
    public void setCostTime(Integer costTime) { this.costTime = costTime; }

    public void setMapToParams(Map<String, String[]> paramMap) {
        this.requestParam = Glue.mapToString(paramMap);
    }
}
