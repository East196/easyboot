package com.github.east196.ezsb.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

/**
 * NormalRequest 的转换器
 *
 * @author Tung
 */
public class String2NormalRequestConverter implements Converter<String, NormalRequest> {
    
    private static final Logger log = LoggerFactory.getLogger(String2NormalRequestConverter.class);

    @Override
    public NormalRequest convert(String source) {
        String uri = new Cryptor().decrypt(source);
        Map<String, String[]> paramsMap = UrlParamParseUtils.getParamsMap(uri, "utf-8");
        NormalRequest normalRequest = new NormalRequest();
        normalRequest.setMap(paramsMap);
        log.info("{}", normalRequest);
        return normalRequest;
    }

}
