package org.vertx.java.core.json.impl;

import org.vertx.java.core.json.DecodeException;
import org.vertx.java.core.json.EncodeException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


/**
 * @author <a href="http://blog.csdn.net/east196">east196</a>
 */
public class JacksonJson implements JsonFunction {

	private final static ObjectMapper mapper = new ObjectMapper();
	private final static ObjectMapper prettyMapper = new ObjectMapper();

	static {
		// Non-standard JSON but we allow C style comments in our JSON
		mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
	}

	@Override
	public String encode(Object obj) throws EncodeException {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new EncodeException("Failed to encode as JSON: " + e.getMessage());
		}
	}

	@Override
	public String encodePrettily(Object obj) throws EncodeException {
		try {
			return prettyMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new EncodeException("Failed to encode as JSON: " + e.getMessage());
		}
	}

	@Override
	public <T> T decodeValue(String str, Class<T> clazz) throws DecodeException {
		try {
			return mapper.readValue(str, clazz);
		} catch (Exception e) {
			throw new DecodeException("Failed to decode:" + e.getMessage());
		}
	}

	static {
		prettyMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	}
}
