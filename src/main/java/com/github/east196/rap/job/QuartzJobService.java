package com.github.east196.rap.job;

import java.util.List;

import com.github.east196.ezsb.core.BaseService;

/**
 * 定时任务接口
 * @author east196
 */
public interface QuartzJobService extends BaseService<QuartzJob,String> {

    /**
     * 通过类名获取
     * @param jobClassName
     * @return
     */
    List<QuartzJob> findByJobClassName(String jobClassName);
}