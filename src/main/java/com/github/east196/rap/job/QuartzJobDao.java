package com.github.east196.rap.job;

import java.util.List;

import com.github.east196.ezsb.core.BaseDao;

/**
 * 定时任务数据处理层
 * @author east196
 */
public interface QuartzJobDao extends BaseDao<QuartzJob,String> {

    /**
     * 通过类名获取
     * @param jobClassName
     * @return
     */
    List<QuartzJob> findByJobClassName(String jobClassName);
}