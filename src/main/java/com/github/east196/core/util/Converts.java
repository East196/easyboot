package com.github.east196.core.util;

import cn.hutool.core.convert.Convert;

import java.util.Date;

public class Converts {

	public static String toString(Object value) {
		return Convert.toStr(value);
	}

	public static Boolean toBoolean(Object value) {
		return Convert.toBool(value);
	}

	public static Integer toInteger(Object value) {
		return Convert.toInt(value);
	}

	public static Double toDouble(Object value) {
		return Convert.toDouble(value);
	}

	public static Date toDate(Object value) {
		return Convert.toDate(value);
	}
}
