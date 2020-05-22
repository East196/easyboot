package com.github.east196.ezsb.mvc;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.beans.BeanUtils.getPropertyDescriptors;
import static org.springframework.beans.BeanUtils.instantiate;

public class NormalRequest {

	private static final Logger logger = LoggerFactory.getLogger(NormalRequest.class);

	private Map<String, String[]> map;

	public Map<String, String[]> getMap() {
		return map;
	}

	public void setMap(Map<String, String[]> map) {
		this.map = map;
	}

	public String get(String key) {
		String[] values = getArray(key);
		return values == null ? null : values[0];
	}

	public Long getLong(String key) {
		String value = get(key);
		return value == null ? null : Long.valueOf(value);
	}

	public Integer getInt(String key) {
		String value = get(key);
		return value == null ? null : Integer.valueOf(value);
	}

	public String[] getArray(String key) {
		String[] values = map.get(key);
		return values;
	}

	public <T> T toBean(Class<T> klass) {
		// TODO +NameMap 通过Map对照属性
		// TODO +Maybe 通过相似度判定
		Assert.notEmpty(map, "the map must be not null or empty!");
		T target = instantiate(klass);
		DirectFieldAccessor accessor = new DirectFieldAccessor(target);
		PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(klass);
		for (PropertyDescriptor property : propertyDescriptors) {
			Class<?> type = property.getPropertyType();
			String propertyName = property.getName();
			String key = lowCamelToUnderscore(propertyName);
			Object value = null;
			logger.debug("property {} : {} key: {}", new Object[] { type.getName(), property, key });
			// TODO +Type 加入对其他类型的判别
			if (String.class.isAssignableFrom(type)) {
				value = get(key);// 先使用underScore的key取值
				if (value == null) {
					value = get(propertyName); // 若取值为空再使用camel的key取值
				}
			} else if (Long.class.isAssignableFrom(type)) {
				value = getLong(key);// 先使用underScore的key取值
				if (value == null) {
					value = getLong(propertyName); // 若取值为空再使用camel的key取值
				}
			} else if (Integer.class.isAssignableFrom(type)) {
				value = getInt(key);// 先使用underScore的key取值
				if (value == null) {
					value = getInt(propertyName); // 若取值为空再使用camel的key取值
				}
				logger.debug("{} : {} : {}", new Object[] { get(key),getInt(key), getInt(propertyName)});
			}

			if (value != null) {
				accessor.setPropertyValue(propertyName, value);
			}
		}
		logger.debug("Request to {} : {}", target.getClass(), target);
		return target;
	}

	private static String lowCamelToUnderscore(String propertyName) {
		// CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,propertyName);
		String[] words = StringUtils.splitByCharacterTypeCamelCase(propertyName);
		List<String> newWords = new ArrayList<String>();
		for (String word : words) {
			String newWord = StringUtils.uncapitalize(word);
			newWords.add(newWord);
		}
		return StringUtils.join(newWords, '_');
	}

	@Override
	public String toString() {
		return "NormalRequest [map=" + map + "]";
	}

}
