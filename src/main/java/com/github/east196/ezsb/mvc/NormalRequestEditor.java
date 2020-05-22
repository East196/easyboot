package com.github.east196.ezsb.mvc;



import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class NormalRequestEditor extends PropertyEditorSupport {
	
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
