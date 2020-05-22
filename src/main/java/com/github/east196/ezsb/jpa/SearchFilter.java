package com.github.east196.ezsb.jpa;

import cn.hutool.core.util.StrUtil;

import com.github.east196.core.boon.Boon;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

public class SearchFilter {

	public enum Operator {
		/**
		 * EQ: 等于
		 */
		EQ,

		LIKE,

		GT,

		LT,

		GTE,

		LTE,

		IN;
	}

	public String fieldName;

	public Object value;

	public Operator operator;

	public SearchFilter(final String fieldName, final Operator operator, final Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	public static Map<String, SearchFilter> parse(final Map<String, Object> searchParams) {
		final HashMap<String, SearchFilter> filters = Maps.<String, SearchFilter>newHashMap();
		Set<Map.Entry<String, Object>> _entrySet = searchParams.entrySet();
		for (final Map.Entry<String, Object> entry : _entrySet) {
			final String key = entry.getKey();
			final Object value = entry.getValue();
			boolean _isNotBlank = StrUtil.isNotBlank(((String) value));
			if (_isNotBlank) {
				final String[] names = StrUtil.split(key, "_");
				int _length = names.length;
				boolean _notEquals = (_length != 2);
				if (_notEquals) {
					throw new IllegalArgumentException((key + " is not a valid search filter name"));
				}
				final String filedName = names[1];
				String _get = names[0];
				final Operator operator = Operator.valueOf(_get);
				final SearchFilter filter = new SearchFilter(filedName, operator, value);
				filters.put(key, filter);
			}
		}
		return filters;
	}

	public static <T> List<SearchFilter> from(final Map<String, String[]> requestParameterMap,
			final Class<T> entityClass) {
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		for (Map.Entry<String, String[]> e : requestParameterMap.entrySet()) {
			// 筛选搜索条件
			if (!"_".equals(e.getKey()) && e.getKey().contains("_")) {
				parameterMap.put(e.getKey(), e.getValue());
			}
		}

		// 获取类属性名称与类型
		final Map<String, Type> fieldTypes = new HashMap<>();
		Field[] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {
			fieldTypes.put(field.getName().toLowerCase(), field.getGenericType());
		}

		List<SearchFilter> _xblockexpression = null;
		{
			Set<Map.Entry<String, String[]>> _entrySet = parameterMap.entrySet();
			final Function<Map.Entry<String, String[]>, SearchFilter> _function = new Function<Map.Entry<String, String[]>, SearchFilter>() {
				@Override
				public SearchFilter apply(final Map.Entry<String, String[]> entry) {
					SearchFilter filter = null;
					final String key = entry.getKey();
					String[] _value = entry.getValue();
					String value = _value[0];
					boolean _isNotBlank = StrUtil.isNotBlank(value);
					if (_isNotBlank) {
						final String[] names = StrUtil.split(key, "_");
						int _length = names.length;
						boolean _notEquals = (_length != 2);
						if (_notEquals) {
							throw new IllegalArgumentException((key + " is not a valid search filter name"));
						}
						Object object = value;
						Type type = fieldTypes.get(names[0].toLowerCase());
						if (null != type) {
							if ("class java.util.Date".equals(type.toString())) {
								object = Boon.praseDate(value);
							} else if ("class java.lang.Boolean".equals(type.toString())) {
								object = Boolean.parseBoolean(value);
							} else if ("class java.lang.Integer".equals(type.toString()) && !"null".equals(value)) {
								object = Integer.parseInt(value);
							} else if ("class java.lang.Double".equals(type.toString())) {
								object = Double.parseDouble(value);
							} else if ("class java.lang.Long".equals(type.toString())) {
								object = Long.parseLong(value);
							} else if ("class java.lang.boolean".equals(type.toString())) {
								object = Boolean.parseBoolean(value);
							}
						}
						final String filedName = names[0];
						String _get = names[1];
						String _upperCase = _get.toUpperCase();
						final Operator operator = Operator.valueOf(_upperCase);
						SearchFilter _searchFilter = new SearchFilter(filedName, operator, object);
						filter = _searchFilter;
					}
					return filter;
				}
			};
			Collection<SearchFilter> _map = Collections2.<Map.Entry<String, String[]>, SearchFilter>transform(_entrySet,
					_function);
			Predicate<SearchFilter> nullFilter = new Predicate<SearchFilter>() {
				@Override
				public boolean apply(SearchFilter input) {
					return input != null;
				}
			};
			Collection<SearchFilter> _filterNull = Collections2.<SearchFilter>filter(_map, nullFilter);
			_xblockexpression = Lists.newArrayList(_filterNull);
		}
		return _xblockexpression;
	}

	public static <T> List<SearchFilter> fromQueryMap(final Map<String, String> queryMap, final Class<T> entityClass) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		for (Map.Entry<String, String> e : queryMap.entrySet()) {
			// 筛选搜索条件
			if (!"_".equals(e.getKey()) && e.getKey().contains("_")) {
				parameterMap.put(e.getKey(), e.getValue());
			}
		}

		// 获取类属性名称与类型
		final Map<String, Type> fieldTypes = new HashMap<>();
		Field[] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {
			fieldTypes.put(field.getName().toLowerCase(), field.getGenericType());
		}

		List<SearchFilter> _xblockexpression = null;
		{
			Set<Map.Entry<String, String>> _entrySet = parameterMap.entrySet();
			final Function<Map.Entry<String, String>, SearchFilter> _function = new Function<Map.Entry<String, String>, SearchFilter>() {
				@Override
				public SearchFilter apply(final Map.Entry<String, String> entry) {
					SearchFilter filter = null;
					final String key = entry.getKey();
					String value = entry.getValue();
					boolean _isNotBlank = StrUtil.isNotBlank(value);
					if (_isNotBlank) {
						final String[] names = StrUtil.split(key, "_");
						int _length = names.length;
						boolean _notEquals = (_length != 2);
						if (_notEquals) {
							throw new IllegalArgumentException((key + " is not a valid search filter name"));
						}
						Object object = value;
						Type type = fieldTypes.get(names[0].toLowerCase());
						if (null != type) {
							if ("class java.util.Date".equals(type.toString())) {
								object = Boon.praseDate(value);
							} else if ("class java.lang.Boolean".equals(type.toString())) {
								object = Boolean.parseBoolean(value);
							} else if ("class java.lang.Integer".equals(type.toString()) && !"null".equals(value)) {
								object = Integer.parseInt(value);
							} else if ("class java.lang.Double".equals(type.toString())) {
								object = Double.parseDouble(value);
							} else if ("class java.lang.Long".equals(type.toString())) {
								object = Long.parseLong(value);
							}
						}
						final String filedName = names[0];
						String _get = names[1];
						String _upperCase = _get.toUpperCase();
						final Operator operator = Operator.valueOf(_upperCase);
						SearchFilter _searchFilter = new SearchFilter(filedName, operator, object);
						filter = _searchFilter;
					}
					return filter;
				}
			};
			Collection<SearchFilter> _map = Collections2.<Map.Entry<String, String>, SearchFilter>transform(_entrySet,
					_function);
			Predicate<SearchFilter> nullFilter = new Predicate<SearchFilter>() {
				@Override
				public boolean apply(SearchFilter input) {
					return input != null;
				}
			};
			Collection<SearchFilter> _filterNull = Collections2.<SearchFilter>filter(_map, nullFilter);
			_xblockexpression = Lists.newArrayList(_filterNull);
		}
		return _xblockexpression;
	}

	public static PageRequest sort(final Map<String, String[]> requestParameterMap, Integer start, Integer length) {
		PageRequest pageRequest = null;
		if (requestParameterMap.containsKey("order[0][dir]")) {
			String[] sorts = requestParameterMap.get("order[0][dir]");
			String[] columns = requestParameterMap.get("order[0][column]");
			String columName = requestParameterMap.get("columns[" + columns[0] + "][data]")[0];
			pageRequest = PageRequest.of(start / length, length,
                    "asc".equals(sorts[0]) ? Direction.ASC : Direction.DESC, columName);
		} else {
			pageRequest = PageRequest.of(start / length, length);
		}
		return pageRequest;
	}

	public static PageRequest sortQueryMap(final Map<String, String> queryMap, Integer start, Integer length) {
		PageRequest pageRequest = null;
		if (queryMap.containsKey("order[0][dir]")) {
			String sort = queryMap.get("order[0][dir]");
			String column = queryMap.get("order[0][column]");
			String columName = queryMap.get("columns[" + column + "][data]");
			pageRequest = PageRequest.of(start / length, length, "asc".equals(sort) ? Direction.ASC : Direction.DESC,
					columName);
		} else {
			pageRequest = PageRequest.of(start / length, length);
		}
		return pageRequest;
	}

}
