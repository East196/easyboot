package com.github.east196.rap.log.es;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.east196.core.vo.SearchVo;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author east196
 * TODO: Spring Boot 3 中 Elasticsearch API 有较大变更，需要重构
 */
@Service
@Transactional
@Slf4j
public class EsLogServiceImpl implements EsLogService {

    @Autowired
    private EsLogDao logDao;

    @Override
    public EsLog saveLog(EsLog esLog) {
        return logDao.save(esLog);
    }

    @Override
    public void deleteLog(String id) {
        logDao.deleteById(id);
    }

    @Override
    public void deleteAll() {
        logDao.deleteAll();
    }
    
    @Override
    public Page<EsLog> findByConfition(Integer type, String key, SearchVo searchVo, Pageable pageable) {
        // TODO: 需要适配 Spring Boot 3 的 Elasticsearch 新 API
        // 临时简化实现
        if(type==null&&StrUtil.isBlank(key)){
            return logDao.findAll(pageable);
        }else if(type!=null){
            return logDao.findByLogType(type, pageable);
        }
        return logDao.findAll(pageable);
    }
}
