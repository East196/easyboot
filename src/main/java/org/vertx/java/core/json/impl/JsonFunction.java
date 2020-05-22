package org.vertx.java.core.json.impl;

import org.vertx.java.core.json.DecodeException;
import org.vertx.java.core.json.EncodeException;


/**
 * @author <a href="http://blog.csdn.net/east196">east196</a>
 */
public interface JsonFunction {

	public String encode(Object obj) throws EncodeException;

	public String encodePrettily(Object obj) throws EncodeException;

	public <T> T decodeValue(String str, Class<T> clazz) throws DecodeException;

}
