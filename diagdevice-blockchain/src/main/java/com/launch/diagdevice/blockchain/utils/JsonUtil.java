package com.launch.diagdevice.blockchain.utils;

import java.io.IOException;
import java.lang.reflect.Type;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.gson.Gson;

public class JsonUtil {

	public static String toJson(Object object) {
		ObjectMapper mapper = new MyObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String toJsonNoEmptyField(Object object) {
		String jsonStr=new Gson().toJson(object);
		return jsonStr;
	}
	
	public static Object fromJson(String jsonString, Class<?> type) {
		Object  object=new Gson().fromJson(jsonString, (Type)type);
		return object;
	}

	public static Object toBean(String jsonString, Class<?> type) {
		ObjectMapper mapper = new MyObjectMapper();
		try {
			return  mapper.readValue(jsonString, type);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}

}

class MyObjectMapper extends ObjectMapper {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public MyObjectMapper() {
		super();
		// 数字也加引号
		// this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
		// this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS,
		// true);
		// 空值处理为空串
		this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator jg, SerializerProvider sp)
					throws IOException, JsonProcessingException {
				jg.writeString("");
			}
		});

	}

}
