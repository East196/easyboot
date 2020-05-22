package com.github.east196.ezsb.mvc;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/****
 * 获取url里面的参数
 * @author lzl
 *
 */
@Slf4j
public class UrlParamParseUtils {

	/****
	 * 
	 * @param uri uri地址 （包括参数信息）
	 * @param enc url参数编码格式
	 * @return
	 */
	public static Map<String,String[]> getParamsMap(String uri, String enc) {
		log.debug("Date："+new Date()+"，URI："+uri);
		Map<String,String[]> paramsMap = new HashMap<String,String[]>();
		if (uri != null && uri.length() > 0) {
			int ampersandIndex = 0;
			int lastAmpersandIndex=uri.indexOf('?')+1;
			String subStr, param, value;
			String[] paramPair, values, newValues;
			do {
				ampersandIndex = uri.indexOf('&', lastAmpersandIndex) + 1;
				if (ampersandIndex > 0) {
					subStr = uri.substring(lastAmpersandIndex,
							ampersandIndex - 1);
					lastAmpersandIndex = ampersandIndex;
				} else {
					subStr = uri.substring(lastAmpersandIndex);
				}
				paramPair = subStr.split("=");
				param = paramPair[0];
				value = paramPair.length == 1 ? "" : paramPair[1];
				try {
					value = URLDecoder.decode(value, enc);
				} catch (UnsupportedEncodingException ignored) {
				}
				if (paramsMap.containsKey(param)) {
					values = (String[]) paramsMap.get(param);
					int len = values.length;
					newValues = new String[len + 1];
					System.arraycopy(values, 0, newValues, 0, len);
					newValues[len] = value;
				} else {
					newValues = new String[] { value };
				}
				paramsMap.put(param, newValues);
			} while (ampersandIndex > 0);
		}
		return paramsMap;
	}
	
	
	public static void main(String[] args) {
		String url="openid=2121&relateType=1&nickname=ekwjekw&headimgurl=euwjekwejw";
		Map<String,String[]> map=getParamsMap(url, "utf-8");
		for (Map.Entry<String,String[]> string : map.entrySet()) {
			System.out.println(string.getKey()+":"+string.getValue()[0]);;
		}
	}
}
