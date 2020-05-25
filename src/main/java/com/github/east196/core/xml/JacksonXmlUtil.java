package com.github.east196.core.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.east196.core.vo.Point;

public final class JacksonXmlUtil {
	public static <T> T xmlToBean(String xml, Class<T> valueType) {
		return xmlToBean(new ByteArrayInputStream(xml.getBytes()), valueType);
	}

	public static <T> T xmlToBean(InputStream src, Class<T> valueType) {
		XmlMapper xml = new XmlMapper();
		try {
			return xml.readValue(src, valueType);
		} catch (IOException e) {
			Logger.getLogger(JacksonXmlUtil.class.getName()).log(Level.SEVERE, null, e);
			return null;
		}
	}

	public static String beanToXml(Object obj) {
		XmlMapper xml = new XmlMapper();
		try {
			return xml.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			Logger.getLogger(JacksonXmlUtil.class.getName()).log(Level.SEVERE, null, e);
			return null;
		}
	}

	public static void main(String[] args) {
		String xml = beanToXml(new Point(1.0,2.0));
		System.out.println(xml);
		Point bean = xmlToBean(xml, Point.class);
		System.out.println(bean);
	}
}