package com.github.east196.rap.log.es;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author east196
 * TODO: Spring Boot 3 中 Elasticsearch 需要新版本客户端
 */
public interface EsLogDao {
    
    EsLog save(EsLog esLog);
    
    void deleteById(String id);
    
    void deleteAll();
    
    Page<EsLog> findAll(Pageable pageable);
    
    Page<EsLog> findByLogType(Integer type, Pageable pageable);
}
