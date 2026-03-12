package com.github.east196.rap.job;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.east196.ezsb.core.BaseEntity;
import com.github.east196.ezsb.core.CommonConstant;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author east196
 */
@Data
@Entity
@Table(name = "t_quartz_job")
@TableName("t_quartz_job")
@Schema(description = "定时任务")
public class QuartzJob extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "任务类名")
    private String jobClassName;

    @Schema(description = "cron表达式")
    private String cronExpression;

    @Schema(description = "参数")
    private String parameter;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "状态 0正常 -1停止")
    private Integer status = CommonConstant.STATUS_NORMAL;
    
    public String getJobClassName() {
        return jobClassName;
    }
    
    public String getCronExpression() {
        return cronExpression;
    }
    
    public String getParameter() {
        return parameter;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
}
