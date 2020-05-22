/*
 * Copyright (c) 2011-2013 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 *     The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 *     The Apache License v2.0 is available at
 *     http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

package org.vertx.java.core.json.impl;

import org.vertx.java.core.json.DecodeException;
import org.vertx.java.core.json.EncodeException;

/**
 * @author <a href="http://blog.csdn.net/east196">east196</a>
 */
public class Json {

	private static JsonFunction jsonFunction;

	static {
		boolean jackson2Present = ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", Json.class.getClassLoader())
				&& ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", Json.class.getClassLoader());
		boolean gsonPresent =
				ClassUtils.isPresent("com.google.gson.Gson", Json.class.getClassLoader());
		if (jackson2Present) {
			jsonFunction = new JacksonJson();
		}else if(gsonPresent){
			jsonFunction = new GsonJson();
		}
	}

	public static String encode(Object obj) throws EncodeException {
		return jsonFunction.encode(obj);
	}

	public static String encodePrettily(Object obj) throws EncodeException {
		return jsonFunction.encodePrettily(obj);
	}

	public static <T> T decodeValue(String str, Class<T> clazz) throws DecodeException {
		return jsonFunction.decodeValue(str, clazz);
	}

}
