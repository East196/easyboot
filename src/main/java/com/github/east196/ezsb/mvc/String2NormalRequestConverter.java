package com.github.east196.ezsb.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

/**
 * NormalRequest 的转换器
 *
 * @author Tung
 */
@Slf4j
public class String2NormalRequestConverter implements Converter<String, NormalRequest> {

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
