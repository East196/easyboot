package com.github.east196.ezsb.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyEditorSupport;
import java.util.Map;

/**
 * NormalRequest 的属性编辑器
 * 
 * <p>
 * 命名BeanName+Editor 是JavaBeans规范，可以被自动注册
 * </p>
 * 
 * @author Tung
 *
 */
public class NormalRequestEditor extends PropertyEditorSupport {
    
    private static final Logger log = LoggerFactory.getLogger(NormalRequestEditor.class);
    
    @Override
    public void setAsText(String text) {
        String uri = new Cryptor().decrypt(text);
        Map<String, String[]> paramsMap = UrlParamParseUtils.getParamsMap(uri,"utf-8");
        NormalRequest normalRequest = new NormalRequest();
        normalRequest.setMap(paramsMap);
        log.info("{}",normalRequest);
        setValue(normalRequest);
    }
}
