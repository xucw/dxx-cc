/**
 * JsonUtil.java 11:15:23 AM Feb 15, 2012
 *
 * Copyright(c) 2000-2012 HC360.COM, All Rights Reserved.
 */
package org.cc.demo.json;

import java.io.StringWriter;
import java.text.SimpleDateFormat;

import org.cc.core.CcException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * json 工具类
 * 
 * @author dixingxing
 * @date Feb 15, 2012
 */
public final class JsonUtils {
	private static final ObjectMapper S_MAPPER ;
	private static final ObjectMapper D_MAPPER ;
	
	static {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 序列化 mapper
		S_MAPPER = new ObjectMapper();
		S_MAPPER.setSerializationConfig(S_MAPPER.getSerializationConfig().withDateFormat(dateFormat));
		
		// 反序列化 mapper
		D_MAPPER = new ObjectMapper();
		D_MAPPER.setDeserializationConfig(D_MAPPER.getDeserializationConfig().withDateFormat(dateFormat));
		
	}

	private JsonUtils() {}
	/**
	 * 使用jackson 序列化成json字符串
	 * 
	 * @param o
	 * @return
	 */
	public static String toJson(Object o) {
		StringWriter sw = new StringWriter();
		try {
			S_MAPPER.writeValue(sw, o);
			return sw.toString();
		} catch (Exception e) {
			throw new CcException("序列化对象出错：", e);
		}
	}

	/**
	 * 使用jackson 把json字符串反序列化成java对象 (单个对象)
	 * 
	 * @param <T>
	 * @param s
	 * @param clazz
	 * @return
	 */
	public static <T> T toObject(String s, Class<T> clazz) {
		try {
			return (T) D_MAPPER.readValue(s, clazz);
		} catch (Exception e) {
			throw new CcException("反序列化对象出错", e);
		}
	}
	
	/**
	 * 使用jackson 把json字符串反序列化成java对象 (list/map 集合)
	 * 
	 * @param <T>
	 * @param s
	 * @param typeReference new TypeReference<List<MyBean>>() {}
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toObject(String s, TypeReference<T> typeReference) {
		try {
			return (T)D_MAPPER.readValue(s, typeReference);
		} catch (Exception e) {
			throw new CcException("反序列化对象出错", e);
		}
	}

}
