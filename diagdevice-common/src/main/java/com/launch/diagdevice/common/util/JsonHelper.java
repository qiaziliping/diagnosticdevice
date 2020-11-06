package com.launch.diagdevice.common.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * SPRINGBOOT jackjson 工具类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月28日
 */
public class JsonHelper {


     private  Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private static ObjectMapper mapper;
    private static final String DYNC_INCLUDE = "DYNC_INCLUDE";
    private static final String DYNC_FILTER = "DYNC_FILTER";
    
    @JsonFilter(DYNC_FILTER)
    interface DynamicFilter {
    }
    @JsonFilter(DYNC_INCLUDE)
    interface DynamicInclude {
    }
    
    public  JsonHelper()
    {
    	mapper = new ObjectMapper();
    	//通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化 
    	//Include.Include.ALWAYS 默认 
    	//Include.NON_DEFAULT 属性为默认值不序列化 
    	//Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化 
    	//Include.NON_NULL 属性为NULL 不序列化 
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);  
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public  JsonHelper(JsonInclude.Include include)
    {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(include);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    
    
    /**
     * 例子include： 集合List<user> list 中的user对象只显示id,name字段
     * 		      this.filter(User.class, "id,name", null);
     *            this.toJsonStr(list);
     * 例子filter ： 集合List<user> list 中的user对象排除id,name字段
     * 		      this.filter(User.class, null, "id,name");
     *            this.toJsonStr(list);
     */
    public void filter(Class<?> clazz,String include, String filter) {
    	if (clazz == null) return;
    	
    	SimpleFilterProvider filterProvider = new SimpleFilterProvider();
    	if (StringUtils.hasText(include)) {
    		
   		 	SimpleBeanPropertyFilter fieldFilter = SimpleBeanPropertyFilter.filterOutAllExcept(include.split(","));
   		 	filterProvider.addFilter(DYNC_INCLUDE, fieldFilter);
   		 	mapper.setFilterProvider(filterProvider);
   		 	mapper.addMixIn(clazz, DynamicInclude.class);
    	} else if (StringUtils.hasText(filter)) {
    		// 需要过滤的字段
    		SimpleBeanPropertyFilter fieldFilter = SimpleBeanPropertyFilter.serializeAllExcept(filter.split(","));
    		filterProvider.addFilter(DYNC_FILTER, fieldFilter);
    		mapper.setFilterProvider(filterProvider);
    		mapper.addMixIn(clazz, DynamicFilter.class);
    	}
    }
    
    /**
     * excludeFiled
     * 不需要显示的字段
     * LIPING
     */
    public void setFieldExclude(String filterName ,String [] excludeFiled) {
    	SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		
		 SimpleBeanPropertyFilter fieldFilter = SimpleBeanPropertyFilter.serializeAllExcept(excludeFiled);
		 filterProvider.addFilter(filterName, fieldFilter);
		 mapper.setFilterProvider(filterProvider);
    }
    
    /**
     * excludeFiled 只显示的字段
     * LIPING
     */
    public void setFieldInclude(String filterName ,String [] excludeFiled) {
    	SimpleFilterProvider filterProvider = new SimpleFilterProvider();
    	SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(excludeFiled);
    	filterProvider.addFilter(filterName, filter);
    	mapper.setFilterProvider(filterProvider);
    }
    
    
    /**
     * 对象转String的json格式
     * example：String str = this.toJsonStr(new user());
     * LIPING
     */
    public  String toJsonStr(Object object) {
    	String json = null;
        try {
        	json = mapper.writeValueAsString(object);
        	// json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object); 美化json格式
		} catch (JsonProcessingException e) {
			logger.error("object to json exception:",e.getMessage());
			e.printStackTrace();
		}
        return json;
    }

    /**
     * String的json格式转对象
     * example：User user = this.fromObj(strJson, User.class);
     * LIPING
     */
    public  <T> T fromObj(String json, Class<T> cls) {
        T t = null;
    	try {
			t = mapper.readValue(json, cls);
		} catch (IOException e) {
			logger.error("json to object exception:",e.getMessage());
			e.printStackTrace();
		}
    	return t;
    }

    /**
     * 复杂的对象转换：List<User> list = this.fromMultiObj(jsonObject, new TypeReference<List<User>>(){});
     * LIPING
     */
	public  <T> T fromMultiObj(String json,TypeReference<T> valueTypeRef) {
		T t = null;
		try {
			t =  mapper.readValue(json, valueTypeRef);
		} catch (IOException e) {
			logger.error("json to object exception:",e.getMessage());
			e.printStackTrace();
		}
		return t;
    }
	
	/**
     * 对象转String的json格式
     * example：String str = this.toJsonStr(new user());
     * excludeFiled 排除的字段数组，需要过滤的实体类必须添加  @JsonFilter("filterName")
     * LIPING
     */
    /*public  String toJsonStrExclude(Object object,String filterName,String [] excludeFiled) {
    	if (excludeFiled != null && excludeFiled.length > 0) {
    		setFieldExclude(filterName,excludeFiled);
    	}
    	return toJsonStr(object);
    }
    
    public  String toJsonStrInclude(Object object,String filterName,String [] includeFiled) {
    	if (includeFiled != null && includeFiled.length > 0) {
    		setFieldInclude(filterName,includeFiled);
    	}
    	return toJsonStr(object);
    }*/
}