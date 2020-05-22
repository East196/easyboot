package org.vertx.java.core.json.impl;

import org.vertx.java.core.json.DecodeException;
import org.vertx.java.core.json.EncodeException;

import com.google.gson.GsonBuilder;

public class GsonJson implements JsonFunction {
	
	private final static GsonBuilder builder = new GsonBuilder();
	private final static GsonBuilder prettyBuilder = new GsonBuilder();

	@Override
	public String encode(Object obj) throws EncodeException {
		return builder.create().toJson(obj);
	}

	@Override
	public String encodePrettily(Object obj) throws EncodeException {
		return prettyBuilder.create().toJson(obj);
	}

	@Override
	public <T> T decodeValue(String str, Class<T> clazz) throws DecodeException {
		return builder.create().fromJson(str, clazz);
	}

	static {
		prettyBuilder.setPrettyPrinting();
	}
}
