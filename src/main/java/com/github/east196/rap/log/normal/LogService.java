package com.github.east196.rap.log.normal;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.east196.ezsb.core.BaseService;
import com.github.east196.rap.log.es.SearchVo;

/**
 * 日志接口
 * @author east196
 */
public interface LogService extends BaseService<Log,String> {

    /**
     * 分页搜索获取日志
     * @param type
     * @param key
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<Log> findByConfition(Integer type, String key, SearchVo searchVo, Pageable pageable);
    /**
     * 删除所有
     */
    void deleteAll();
}
