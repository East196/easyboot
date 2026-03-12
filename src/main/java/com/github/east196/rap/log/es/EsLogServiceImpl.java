package com.github.east196.rap.log.es;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.east196.rap.log.es.SearchVo;

import cn.hutool.core.util.StrUtil;

/**
 * @author east196
 * TODO: Spring Boot 3 中 Elasticsearch API 有较大变更，需要重构
 */
@Service
@Transactional
public class EsLogServiceImpl implements EsLogService {

    @Autowired
    private EsLogDao logDao;

    @Override
    public EsLog saveLog(EsLog esLog) {
        // TODO: 需要适配 Spring Boot 3 的 Elasticsearch 新 API
        return esLog;
    }

    @Override
    public void deleteLog(String id) {
        // TODO: 需要适配 Spring Boot 3 的 Elasticsearch 新 API
    }

    @Override
    public void deleteAll() {
        // TODO: 需要适配 Spring Boot 3 的 Elasticsearch 新 API
    }
    
    @Override
    public Page<EsLog> findByConfition(Integer type, String key, SearchVo searchVo, Pageable pageable) {
        // TODO: 需要适配 Spring Boot 3 的 Elasticsearch 新 API
        return Page.empty(pageable);
    }
}
